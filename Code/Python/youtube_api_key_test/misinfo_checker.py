"""
Healthcare Video Misinformation Checker
========================================
Extracts claims from YouTube video transcripts, then checks them using:
  1. Google Fact Check Tools API (free) — searches real fact-check articles
  2. Local NLI model (BART-large-MNLI) — classifies claims as supported/refuted/neutral
  3. Regex patterns for known healthcare misinformation rhetoric

Usage:
    python misinfo_checker.py                          → test video
    python misinfo_checker.py 1 video_ids.txt          → from file
    python misinfo_checker.py 2                        → from supabase
    python misinfo_checker.py --video <VIDEO_ID>       → single video by ID
"""

import os
import sys
import re
import json
import logging
from pathlib import Path
from dataclasses import dataclass, field, asdict

import requests
from youtube_transcript_api import YouTubeTranscriptApi
from dotenv import load_dotenv

# Suppress noisy model output
os.environ["TRANSFORMERS_NO_ADVISORY_WARNINGS"] = "1"
os.environ["TOKENIZERS_PARALLELISM"] = "false"
logging.getLogger("transformers").setLevel(logging.ERROR)
logging.getLogger("torch").setLevel(logging.ERROR)

from transformers import pipeline

# ──────────────────────────────────────────────
# CONFIG
# ──────────────────────────────────────────────

load_dotenv(Path(__file__).resolve().parent / ".env.example")

GOOGLE_API_KEY = os.environ.get("YOUTUBE_API_KEY", "")
SUPABASE_URL = os.environ.get("SUPABASE_URL", "")
SUPABASE_KEY = os.environ.get("SUPABASE_KEY", "")
SUPABASE_TABLE_VIDEOS = "healthcare_videos"

FACT_CHECK_API_URL = "https://factchecktools.googleapis.com/v1alpha1/claims:search"

# ──────────────────────────────────────────────
# MODELS (loaded once at import time)
# ──────────────────────────────────────────────

# Zero-shot NLI — used for claim extraction, stance, and entailment
nli_model = pipeline(
    "zero-shot-classification",
    model="facebook/bart-large-mnli",
)

# Transcript client
ytt_api = YouTubeTranscriptApi()

# ──────────────────────────────────────────────
# KNOWN MISINFO PATTERNS (healthcare-specific)
# ──────────────────────────────────────────────
# Each entry: (compiled regex, human-readable description, severity)
# Severity: "high" = well-established misinformation, "medium" = suspicious rhetoric

MISINFO_PATTERNS = [
    # Vaccines
    (r"vaccines?\s+caus(e|es|ed|ing)\s+autism", "Vaccines cause autism", "high"),
    (r"vaccines?\s+(are|is)\s+(poison|toxic|dangerous)", "Vaccines are toxic/dangerous", "high"),
    (r"vaccines?\s+(contain|have)\s+microchips?", "Vaccines contain microchips", "high"),
    (r"vaccines?\s+(alter|change|modify)\s+(your\s+)?dna", "Vaccines alter DNA", "high"),
    (r"(don.?t|never|stop)\s+vaccinate?\s+(your\s+)?(kids?|children|babies?)", "Anti-vaccination rhetoric", "high"),
    (r"natural\s+immunity\s+is\s+(all|enough|better|superior)", "Natural immunity superiority claims", "medium"),

    # COVID-specific
    (r"covid.{0,10}(hoax|fake|planned|plandemic)", "COVID is a hoax/planned", "high"),
    (r"5g\s+(caus|spread|transmit).{0,20}(covid|virus|disease)", "5G causes COVID", "high"),
    (r"ivermectin\s+(cure|treat|heal).{0,20}covid", "Ivermectin cures COVID", "high"),
    (r"hydroxychloroquine\s+(cure|treat|heal).{0,20}covid", "Hydroxychloroquine cures COVID", "high"),
    (r"masks?\s+(don.?t|do\s+not|never)\s+work", "Masks don't work", "medium"),

    # Big Pharma / conspiracy
    (r"big\s+pharma\s+(is\s+)?(hiding|suppressing|covering)", "Big Pharma suppression conspiracy", "medium"),
    (r"cure\s+for\s+cancer\s+(is\s+)?(being\s+)?(hidden|suppressed|covered)", "Cancer cure is hidden", "high"),
    (r"doctors?\s+(are|is)\s+(all\s+)?(lying|paid\s+off|corrupt|bribed)", "Doctors are corrupt/lying", "medium"),
    (r"(pharma|drug)\s+companies?\s+(don.?t|do\s+not)\s+want\s+you\s+to\s+know", "Pharma hiding information", "medium"),
    (r"(government|fda|cdc|who)\s+(is\s+)?(lying|corrupt|hiding|cover)", "Government health conspiracy", "medium"),

    # Alternative medicine overclaims
    (r"(essential\s+oils?|herbs?|crystals?|homeopathy)\s+(cure|heal|treat).{0,20}(cancer|diabetes|hiv|aids)", "Alt medicine cures serious disease", "high"),
    (r"(alkaline|detox|cleanse)\s+(cure|heal|prevent).{0,20}(cancer|disease)", "Alkaline/detox cures disease", "high"),
    (r"(coffee\s+enema|turpentine|bleach|mms)\s+(cure|heal|treat)", "Dangerous substance as medicine", "high"),

    # Anti-science
    (r"(germ\s+theory|evolution)\s+(is\s+)?(a\s+)?(lie|myth|fraud|hoax|wrong)", "Germ theory denial", "high"),
    (r"(fluoride|water)\s+(is\s+)?(poison|mind\s+control|toxic)", "Fluoride conspiracy", "medium"),
    (r"(chemtrails?)\s+(are|is)\s+(spraying|poison|real)", "Chemtrails conspiracy", "medium"),
]

# Compile patterns once
COMPILED_PATTERNS = [(re.compile(p, re.IGNORECASE), desc, sev) for p, desc, sev in MISINFO_PATTERNS]

# ──────────────────────────────────────────────
# CLAIM CATEGORIES (for zero-shot extraction)
# ──────────────────────────────────────────────

HEALTH_CLAIM_TYPES = [
    "a specific health treatment or cure",
    "a claim about vaccine safety or efficacy",
    "a claim about a drug or medication",
    "a dietary or nutritional health claim",
    "a claim about a disease cause or mechanism",
    "a conspiracy theory about healthcare",
    "a claim about government or institutional health policy",
    "general health education or information",
]

# ──────────────────────────────────────────────
# DATA CLASSES
# ──────────────────────────────────────────────

@dataclass
class PatternMatch:
    pattern_description: str
    severity: str
    match_count: int

@dataclass
class FactCheckResult:
    claim_text: str
    claimant: str | None
    rating: str
    publisher: str
    url: str

@dataclass
class ClaimAnalysis:
    claim_text: str
    claim_type: str
    claim_type_confidence: float
    entailment_label: str       # "supported", "refuted", "neutral"
    entailment_confidence: float
    fact_checks: list[FactCheckResult] = field(default_factory=list)

@dataclass
class VideoMisinfoReport:
    video_id: str
    error: str | None = None
    transcript_length_words: int = 0
    # Pattern-based detection
    pattern_matches: list[PatternMatch] = field(default_factory=list)
    high_severity_count: int = 0
    medium_severity_count: int = 0
    # Claim-level analysis
    claims_analyzed: list[ClaimAnalysis] = field(default_factory=list)
    # Overall assessment
    risk_level: str = "low"   # low / medium / high
    risk_reasons: list[str] = field(default_factory=list)


# ──────────────────────────────────────────────
# TRANSCRIPT HELPERS
# ──────────────────────────────────────────────

def fetch_transcript(video_id: str) -> str | None:
    """Fetch and clean a video transcript. Returns None if unavailable."""
    try:
        try:
            transcript = ytt_api.fetch(video_id, languages=["en"])
        except Exception:
            transcript = ytt_api.fetch(video_id)

        text = " ".join(s.text for s in transcript.snippets)
        # Clean
        text = re.sub(r'\[[^\]]*\]', '', text, flags=re.IGNORECASE)
        text = re.sub(r'\([^)]*\)', '', text)
        text = re.sub(r'\b(?:um|uh|ugh|hmm)\b', '', text, flags=re.IGNORECASE)
        text = re.sub(r'\s+', ' ', text).strip()
        return text
    except Exception as e:
        return None


def extract_sentences(text: str) -> list[str]:
    """Split text into sentence-like chunks for claim analysis."""
    # Split on sentence-ending punctuation
    raw = re.split(r'(?<=[.!?])\s+', text)
    sentences = []
    for s in raw:
        s = s.strip()
        # Only keep sentences of reasonable length (5-60 words)
        word_count = len(s.split())
        if 5 <= word_count <= 60:
            sentences.append(s)
    return sentences


# ──────────────────────────────────────────────
# 1. PATTERN-BASED DETECTION
# ──────────────────────────────────────────────

def scan_patterns(text: str) -> list[PatternMatch]:
    """Scan text for known misinformation patterns."""
    matches = []
    for pattern, description, severity in COMPILED_PATTERNS:
        found = pattern.findall(text)
        if found:
            matches.append(PatternMatch(
                pattern_description=description,
                severity=severity,
                match_count=len(found),
            ))
    return matches


# ──────────────────────────────────────────────
# 2. GOOGLE FACT CHECK TOOLS API
# ──────────────────────────────────────────────

def search_fact_checks(query: str, max_results: int = 3) -> list[FactCheckResult]:
    """
    Search Google's Fact Check Tools API for existing fact-checks on a claim.
    Free, no OAuth — just needs an API key.
    Docs: https://developers.google.com/fact-check/tools/api/reference/rest
    """
    if not GOOGLE_API_KEY:
        return []

    try:
        resp = requests.get(
            FACT_CHECK_API_URL,
            params={
                "key": GOOGLE_API_KEY,
                "query": query[:200],  # API has length limits
                "languageCode": "en",
            },
            timeout=10,
        )
        if resp.status_code != 200:
            return []

        data = resp.json()
        results = []
        for claim in data.get("claims", [])[:max_results]:
            for review in claim.get("claimReview", []):
                results.append(FactCheckResult(
                    claim_text=claim.get("text", ""),
                    claimant=claim.get("claimant"),
                    rating=review.get("textualRating", "Unknown"),
                    publisher=review.get("publisher", {}).get("name", "Unknown"),
                    url=review.get("url", ""),
                ))
        return results
    except Exception:
        return []


# ──────────────────────────────────────────────
# 3. NLI-BASED CLAIM ANALYSIS
# ──────────────────────────────────────────────

def classify_claim_type(sentence: str) -> tuple[str, float]:
    """Classify what type of health claim a sentence is."""
    result = nli_model(sentence, candidate_labels=HEALTH_CLAIM_TYPES)
    return result["labels"][0], round(result["scores"][0], 4)


def check_entailment(claim: str, evidence: str = None) -> tuple[str, float]:
    """
    Use NLI to check if a claim is supported, refuted, or neutral
    relative to established medical consensus framing.
    """
    # We frame consensus positions as hypotheses and test the claim against them
    consensus_hypotheses = [
        "This claim is consistent with established medical science.",
        "This claim contradicts established medical science.",
        "This claim is an opinion or cannot be verified.",
    ]
    label_map = {
        consensus_hypotheses[0]: "supported",
        consensus_hypotheses[1]: "refuted",
        consensus_hypotheses[2]: "neutral",
    }
    result = nli_model(claim, candidate_labels=consensus_hypotheses)
    top_label = result["labels"][0]
    return label_map[top_label], round(result["scores"][0], 4)


def is_health_claim(sentence: str) -> bool:
    """Quick filter: is this sentence making a health-related claim (vs. just narration)?"""
    result = nli_model(
        sentence,
        candidate_labels=[
            "a factual health or medical claim",
            "general narration or filler",
        ],
    )
    return result["labels"][0] == "a factual health or medical claim" and result["scores"][0] > 0.6


def analyze_claims(text: str, max_claims: int = 10) -> list[ClaimAnalysis]:
    """
    Extract sentences that look like health claims, classify them,
    check entailment, and search for existing fact-checks.
    """
    sentences = extract_sentences(text)

    # Filter to only health claim sentences (batch for speed)
    claim_sentences = []
    for s in sentences:
        if len(claim_sentences) >= max_claims * 3:  # check more than we need, then trim
            break
        if is_health_claim(s):
            claim_sentences.append(s)

    analyses = []
    for sentence in claim_sentences[:max_claims]:
        claim_type, type_conf = classify_claim_type(sentence)
        entailment, ent_conf = check_entailment(sentence)

        # Skip boring "general education" claims that are clearly fine
        if claim_type == "general health education or information" and entailment == "supported":
            continue

        # Search Google Fact Check API for this claim
        fact_checks = search_fact_checks(sentence)

        analyses.append(ClaimAnalysis(
            claim_text=sentence,
            claim_type=claim_type,
            claim_type_confidence=type_conf,
            entailment_label=entailment,
            entailment_confidence=ent_conf,
            fact_checks=fact_checks,
        ))

    return analyses


# ──────────────────────────────────────────────
# FULL PIPELINE
# ──────────────────────────────────────────────

def check_video(video_id: str) -> VideoMisinfoReport:
    """Full misinformation check pipeline for a single video."""
    report = VideoMisinfoReport(video_id=video_id)

    # 1. Fetch transcript
    text = fetch_transcript(video_id)
    if not text:
        report.error = "Could not fetch transcript"
        return report

    report.transcript_length_words = len(text.split())

    # 2. Pattern-based scan
    report.pattern_matches = scan_patterns(text)
    report.high_severity_count = sum(1 for m in report.pattern_matches if m.severity == "high")
    report.medium_severity_count = sum(1 for m in report.pattern_matches if m.severity == "medium")

    # 3. Claim-level analysis (NLI + fact-check API)
    report.claims_analyzed = analyze_claims(text)

    # 4. Compute overall risk level
    refuted_claims = sum(1 for c in report.claims_analyzed if c.entailment_label == "refuted")
    fact_checked_false = sum(
        1 for c in report.claims_analyzed
        for fc in c.fact_checks
        if any(word in fc.rating.lower() for word in ["false", "pants on fire", "incorrect", "misleading", "wrong"])
    )

    reasons = []
    if report.high_severity_count > 0:
        reasons.append(f"{report.high_severity_count} high-severity misinfo pattern(s)")
    if refuted_claims > 0:
        reasons.append(f"{refuted_claims} claim(s) flagged as contradicting medical consensus")
    if fact_checked_false > 0:
        reasons.append(f"{fact_checked_false} claim(s) rated false by fact-checkers")
    if report.medium_severity_count >= 3:
        reasons.append(f"{report.medium_severity_count} medium-severity suspicious patterns")

    report.risk_reasons = reasons

    if report.high_severity_count > 0 or fact_checked_false > 0 or refuted_claims >= 2:
        report.risk_level = "high"
    elif report.medium_severity_count > 0 or refuted_claims >= 1:
        report.risk_level = "medium"
    else:
        report.risk_level = "low"

    return report


# ──────────────────────────────────────────────
# VIDEO ID SOURCES
# ──────────────────────────────────────────────

def ids_from_file(filepath: str) -> list[str]:
    path = Path(filepath)
    if not path.exists():
        print(f"Error: File '{filepath}' not found.")
        sys.exit(1)
    ids = []
    for line in path.read_text().splitlines():
        line = line.split("#")[0].strip()
        if line:
            ids.append(line)
    return ids


def ids_from_supabase() -> list[str]:
    from supabase import create_client
    if not SUPABASE_URL or not SUPABASE_KEY:
        print("Error: SUPABASE_URL / SUPABASE_KEY not set.")
        sys.exit(1)
    client = create_client(SUPABASE_URL, SUPABASE_KEY)
    rows = client.table(SUPABASE_TABLE_VIDEOS).select("video_id").execute()
    return [r["video_id"] for r in rows.data]


# ──────────────────────────────────────────────
# PRETTY PRINTING
# ──────────────────────────────────────────────

RISK_ICONS = {"low": "🟢", "medium": "🟡", "high": "🔴"}

def print_report(report: VideoMisinfoReport):
    icon = RISK_ICONS.get(report.risk_level, "⚪")

    if report.error:
        print(f"  ⚠  {report.error}\n")
        return

    print(f"  Risk: {icon} {report.risk_level.upper()}  ({report.transcript_length_words} words)")

    # Pattern matches
    if report.pattern_matches:
        print(f"  Pattern flags:")
        for m in report.pattern_matches:
            sev_icon = "🔴" if m.severity == "high" else "🟡"
            print(f"    {sev_icon} {m.pattern_description} (×{m.match_count})")
    else:
        print(f"  Pattern flags: None")

    # Claims
    if report.claims_analyzed:
        print(f"  Claims analyzed: {len(report.claims_analyzed)}")
        for i, c in enumerate(report.claims_analyzed, 1):
            ent_icon = {"supported": "✅", "refuted": "❌", "neutral": "➖"}.get(c.entailment_label, "?")
            print(f"    {i}. {ent_icon} [{c.entailment_label}] ({c.entailment_confidence:.0%}) {c.claim_text[:120]}")
            if c.fact_checks:
                for fc in c.fact_checks:
                    print(f"       📰 {fc.publisher}: \"{fc.rating}\"")
                    print(f"          {fc.url}")
    else:
        print(f"  Claims: No notable health claims detected")

    # Risk reasons
    if report.risk_reasons:
        print(f"  Reasons:")
        for r in report.risk_reasons:
            print(f"    → {r}")

    print()


# ──────────────────────────────────────────────
# CLI
# ──────────────────────────────────────────────
# Usage:
#   python misinfo_checker.py                          → test video
#   python misinfo_checker.py 1 video_ids.txt          → from file
#   python misinfo_checker.py 2                        → from supabase
#   python misinfo_checker.py --video <VIDEO_ID>       → single video

if __name__ == "__main__":
    # Parse --video flag
    if "--video" in sys.argv:
        idx = sys.argv.index("--video")
        if idx + 1 >= len(sys.argv):
            print("Usage: python misinfo_checker.py --video <VIDEO_ID>")
            sys.exit(1)
        video_ids = [sys.argv[idx + 1]]
    else:
        mode = sys.argv[1] if len(sys.argv) > 1 else None

        if mode is None:
            video_ids = ["dQw4w9WgXcQ"]
        elif mode == "1":
            if len(sys.argv) < 3:
                print("Usage: python misinfo_checker.py 1 <path_to_ids.txt>")
                sys.exit(1)
            video_ids = ids_from_file(sys.argv[2])
        elif mode == "2":
            video_ids = ids_from_supabase()
        else:
            print(f"Unknown mode '{mode}'. Use: no args | 1 <file> | 2 | --video <ID>")
            sys.exit(1)

    print(f"Checking {len(video_ids)} video(s) for misinformation...\n")

    all_reports = []
    for i, vid in enumerate(video_ids, 1):
        print(f"[{i}/{len(video_ids)}] {vid}")
        report = check_video(vid)
        print_report(report)
        all_reports.append(report)

    # Summary
    checked = [r for r in all_reports if r.error is None]
    failed = len(all_reports) - len(checked)
    high = sum(1 for r in checked if r.risk_level == "high")
    medium = sum(1 for r in checked if r.risk_level == "medium")
    low = sum(1 for r in checked if r.risk_level == "low")
    total_patterns = sum(len(r.pattern_matches) for r in checked)
    total_claims = sum(len(r.claims_analyzed) for r in checked)

    print(f"{'═' * 60}")
    print(f"MISINFORMATION CHECK SUMMARY")
    print(f"{'─' * 60}")
    print(f"  Videos checked:     {len(checked)}/{len(all_reports)}  (failed: {failed})")
    print(f"  Risk breakdown:     🔴 {high} high  |  🟡 {medium} medium  |  🟢 {low} low")
    print(f"  Pattern flags:      {total_patterns} total across all videos")
    print(f"  Claims analyzed:    {total_claims} total")
    if high > 0:
        print(f"\n  ⚠  {high} video(s) flagged HIGH RISK — review recommended")
    print(f"{'═' * 60}")

    # Optionally dump full JSON
    if "--json" in sys.argv:
        output = [asdict(r) for r in all_reports]
        json_path = Path("misinfo_report.json")
        json_path.write_text(json.dumps(output, indent=2, default=str))
        print(f"\nFull report saved to {json_path}")
