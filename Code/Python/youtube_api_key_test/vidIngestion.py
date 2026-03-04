"""
YouTube Healthcare Video Ingestion Pipeline
=============================================
- Searches YouTube for healthcare-related videos across a curated topic list.
- Filters for relevance (minimum view threshold).
- Fetches transcripts when available.
- Stores everything in Supabase.

Usage:
    # One-time initial dataset build (backfill)
    python vidIngestion.py --backfill

    # Weekly cron job (latest videos only)
    python vidIngestion.py

Cron example (every Sunday at 2 AM):
    0 2 * * 0  /path/to/.venv/bin/python /path/to/vidIngestion.py >> /path/to/ingestion.log 2>&1
"""

import os
import re
import argparse
import logging
from datetime import datetime, timedelta, timezone
from typing import List, Optional
from pathlib import Path

import pandas as pd
from dotenv import load_dotenv
from googleapiclient.discovery import build
from youtube_transcript_api import YouTubeTranscriptApi
from supabase import create_client, Client

# Load .env file from the same directory as this script
load_dotenv(Path(__file__).resolve().parent / ".env")

# ──────────────────────────────────────────────
# CONFIG
# ──────────────────────────────────────────────

YOUTUBE_API_KEY = "AIzaSyDmBuETaIteZZAaCXm3wnWPWD1hpLsv328"
SUPABASE_URL = os.environ.get("SUPABASE_URL", "YOUR_SUPABASE_URL_HERE")
SUPABASE_KEY = os.environ.get("SUPABASE_KEY", "YOUR_SUPABASE_KEY_HERE")

SUPABASE_TABLE_VIDEOS = "healthcare_videos"
SUPABASE_TABLE_TRANSCRIPTS = "healthcare_video_transcripts"

# Minimum views for a video to be considered "relevant"
MIN_VIEWS_WEEKLY = 1_000       # for weekly cron runs (recent videos)
MIN_VIEWS_BACKFILL = 5_000     # for initial backfill (older videos, higher bar)

# How many results per topic per API call (max 50)
RESULTS_PER_TOPIC_WEEKLY = 10
RESULTS_PER_TOPIC_BACKFILL = 50

# Weekly run: only fetch videos published in the last N days
WEEKLY_LOOKBACK_DAYS = 8  # slightly more than 7 to catch edge cases

# ──────────────────────────────────────────────
# HEALTHCARE TOPICS
# ──────────────────────────────────────────────
# Edit this list freely — add, remove, or rename topics
# to match your project needs.

HEALTHCARE_TOPICS: List[str] = [
    # ── General / Public Health ──
    "public health explained",
    "healthcare system overview",
    "health policy",
    "global health issues",
    "health equity and disparities",
    "social determinants of health",

    # ── Clinical Medicine ──
    "cardiology basics",
    "oncology treatment explained",
    "neurology and brain health",
    "orthopedic surgery explained",
    "dermatology skin conditions",
    "endocrinology hormones",
    "gastroenterology digestive health",
    "pulmonology lung health",
    "nephrology kidney health",
    "hematology blood disorders",
    "infectious disease explained",
    "rheumatology autoimmune diseases",

    # ── Mental Health ──
    "mental health awareness",
    "anxiety disorder explained",
    "depression treatment",
    "PTSD and trauma therapy",
    "addiction and substance abuse",
    "cognitive behavioral therapy",

    # ── Preventive / Lifestyle ──
    "nutrition and diet health",
    "exercise and fitness health",
    "sleep hygiene and health",
    "stress management techniques",
    "preventive health screenings",
    "vaccination and immunization",

    # ── Specialized Topics ──
    "pediatric health children",
    "geriatric care elderly",
    "women's health gynecology",
    "men's health urology",
    "maternal health pregnancy",
    "chronic disease management",
    "diabetes management",
    "hypertension blood pressure",
    "obesity and weight management",

    # ── Health Tech / Innovation ──
    "telemedicine and digital health",
    "health informatics",
    "medical AI and machine learning",
    "wearable health technology",
    "genomics and personalized medicine",

    # ── Allied Health ──
    "nursing explained",
    "physical therapy rehabilitation",
    "occupational therapy",
    "pharmacy and medications",
    "radiology and medical imaging",
    "emergency medicine",

    # ── Ethics & Research ──
    "medical ethics explained",
    "clinical trials research",
    "evidence-based medicine",
    "health misinformation",
]

# ──────────────────────────────────────────────
# LOGGING
# ──────────────────────────────────────────────

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s  %(levelname)-8s  %(message)s",
    datefmt="%Y-%m-%d %H:%M:%S",
)
log = logging.getLogger(__name__)

# ──────────────────────────────────────────────
# YOUTUBE HELPERS
# ──────────────────────────────────────────────

def get_youtube_client():
    return build("youtube", "v3", developerKey=YOUTUBE_API_KEY)


def search_videos(
    youtube,
    query: str,
    max_results: int = 25,
    published_after: Optional[str] = None,
) -> pd.DataFrame:
    """
    Search YouTube for videos matching `query`.
    `published_after` is an RFC 3339 timestamp string (e.g. '2025-01-01T00:00:00Z').
    """
    params = dict(
        part="snippet",
        q=query,
        type="video",
        maxResults=min(max_results, 50),
        order="viewCount",
        relevanceLanguage="en",
    )
    if published_after:
        params["publishedAfter"] = published_after

    resp = youtube.search().list(**params).execute()

    rows = []
    for item in resp.get("items", []):
        rows.append({
            "video_id": item["id"]["videoId"],
            "title": item["snippet"]["title"],
            "description": item["snippet"].get("description", ""),
            "channel_title": item["snippet"]["channelTitle"],
            "channel_id": item["snippet"]["channelId"],
            "published_at": item["snippet"]["publishedAt"],
            "search_topic": query,
        })
    return pd.DataFrame(rows)


def enrich_with_stats(youtube, df: pd.DataFrame) -> pd.DataFrame:
    """Attach view count, likes, comments, duration, and category from the videos().list endpoint."""
    if df.empty:
        return df

    ids = df["video_id"].tolist()
    stats_map = {}

    # YouTube allows max 50 ids per call — chunk if needed
    for i in range(0, len(ids), 50):
        chunk = ids[i : i + 50]
        resp = youtube.videos().list(
            part="snippet,statistics,contentDetails",
            id=",".join(chunk),
        ).execute()

        for v in resp.get("items", []):
            stats = v.get("statistics", {})
            details = v.get("contentDetails", {})
            snippet = v.get("snippet", {})
            stats_map[v["id"]] = {
                "views": int(stats.get("viewCount", 0)),
                "likes": int(stats.get("likeCount", 0)) if "likeCount" in stats else None,
                "comment_count": int(stats.get("commentCount", 0)) if "commentCount" in stats else None,
                "duration_iso": details.get("duration"),
                "category_id": snippet.get("categoryId"),
                "full_description": snippet.get("description", ""),
            }

    enriched = df.copy()
    for col in ["views", "likes", "comment_count", "duration_iso", "category_id", "full_description"]:
        enriched[col] = enriched["video_id"].map(lambda vid, c=col: stats_map.get(vid, {}).get(c))

    enriched["published_at"] = pd.to_datetime(enriched["published_at"], errors="coerce")
    return enriched


# ──────────────────────────────────────────────
# DURATION PARSING
# ──────────────────────────────────────────────

def parse_iso8601_duration(dur_iso: str) -> int:
    """PT#H#M#S → total seconds. Returns 0 for None/invalid."""
    if not isinstance(dur_iso, str):
        return 0
    m = re.match(r"PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?", dur_iso)
    if not m:
        return 0
    return int(m.group(1) or 0) * 3600 + int(m.group(2) or 0) * 60 + int(m.group(3) or 0)


# ──────────────────────────────────────────────
# TRANSCRIPT FETCHING
# ──────────────────────────────────────────────

def fetch_transcript(video_id: str) -> Optional[str]:
    """Return full transcript text or None if unavailable."""
    try:
        ytt = YouTubeTranscriptApi()
        transcript = ytt.fetch(video_id, languages=["en"])
        return " ".join(s.text for s in transcript.snippets)
    except Exception:
        return None


# ──────────────────────────────────────────────
# FEATURE EXTRACTION
# ──────────────────────────────────────────────

def compute_features(df: pd.DataFrame) -> pd.DataFrame:
    """Add derived columns to the enriched DataFrame."""
    now = pd.Timestamp.now(tz="UTC")

    df = df.copy()
    df["duration_seconds"] = df["duration_iso"].apply(parse_iso8601_duration)
    df["is_long"] = df["duration_seconds"] >= 20 * 60

    df["published_at"] = pd.to_datetime(df["published_at"], utc=True, errors="coerce")
    df["published_age_days"] = df["published_at"].apply(
        lambda ts: max(1, (now - ts).days) if pd.notna(ts) else None
    )

    df["views_per_day"] = df.apply(
        lambda r: r["views"] / r["published_age_days"]
        if r.get("published_age_days") and r.get("views")
        else None,
        axis=1,
    )

    safe_views = df["views"].replace(0, None)
    df["likes_ratio"] = df["likes"] / safe_views
    df["comment_ratio"] = df["comment_count"] / safe_views
    df["engagement_score"] = (df["likes"].fillna(0) + df["comment_count"].fillna(0)) / safe_views

    df["title_length"] = df["title"].str.len()
    df["title_word_count"] = df["title"].str.split().str.len()
    df["description_length"] = df["full_description"].str.len()

    return df


# ──────────────────────────────────────────────
# SUPABASE
# ──────────────────────────────────────────────

def get_supabase_client() -> Client:
    return create_client(SUPABASE_URL, SUPABASE_KEY)


def get_existing_video_ids(supabase: Client) -> set:
    """Fetch all video_ids already in the videos table to avoid duplicates."""
    rows = supabase.table(SUPABASE_TABLE_VIDEOS).select("video_id").execute()
    return {r["video_id"] for r in rows.data}


def _prepare_records(df: pd.DataFrame, columns: List[str]) -> list:
    """Convert a DataFrame subset into a list of JSON-safe dicts."""
    cols = [c for c in columns if c in df.columns]
    records = []
    for _, row in df[cols].iterrows():
        rec = {}
        for col in cols:
            val = row[col]
            if pd.isna(val):
                rec[col] = None
            elif isinstance(val, pd.Timestamp):
                rec[col] = val.isoformat()
            elif isinstance(val, (bool,)):
                rec[col] = val
            else:
                rec[col] = val
        records.append(rec)
    return records


def upsert_videos(supabase: Client, df: pd.DataFrame) -> int:
    """Upsert video metadata into the healthcare_videos table."""
    video_cols = [
        "video_id", "channel_id", "title", "description", "full_description",
        "channel_title", "published_at", "search_topic", "category_id",
        "views", "likes", "comment_count",
        "duration_iso", "duration_seconds", "is_long",
        "published_age_days", "views_per_day",
        "likes_ratio", "comment_ratio", "engagement_score",
        "title_length", "title_word_count", "description_length",
        "ingested_at",
    ]
    records = _prepare_records(df, video_cols)
    if not records:
        return 0

    for i in range(0, len(records), 1000):
        batch = records[i : i + 1000]
        supabase.table(SUPABASE_TABLE_VIDEOS).upsert(batch, on_conflict="video_id").execute()

    return len(records)


def upsert_transcripts(supabase: Client, df: pd.DataFrame) -> int:
    """Upsert transcripts into the healthcare_video_transcripts table."""
    # Only upsert rows that actually have a transcript
    has_text = df[df["transcript_text"].notna() & (df["transcript_text"] != "")]
    if has_text.empty:
        return 0

    transcript_cols = ["video_id", "transcript_text", "ingested_at"]
    records = _prepare_records(has_text, transcript_cols)

    for i in range(0, len(records), 1000):
        batch = records[i : i + 1000]
        supabase.table(SUPABASE_TABLE_TRANSCRIPTS).upsert(batch, on_conflict="video_id").execute()

    return len(records)


# ──────────────────────────────────────────────
# MAIN PIPELINE
# ──────────────────────────────────────────────

def run_ingestion(backfill: bool = False):
    """
    Core pipeline:
      1. For each topic, search YouTube.
      2. Enrich with stats.
      3. Filter by minimum views.
      4. Drop duplicates & already-ingested videos.
      5. Fetch transcripts.
      6. Compute derived features.
      7. Upsert to Supabase.
    """
    youtube = get_youtube_client()
    supabase = get_supabase_client()

    if backfill:
        min_views = MIN_VIEWS_BACKFILL
        results_per_topic = RESULTS_PER_TOPIC_BACKFILL
        published_after = None
        log.info("=== BACKFILL MODE — one-time initial dataset build ===")
    else:
        min_views = MIN_VIEWS_WEEKLY
        results_per_topic = RESULTS_PER_TOPIC_WEEKLY
        cutoff = datetime.now(timezone.utc) - timedelta(days=WEEKLY_LOOKBACK_DAYS)
        published_after = cutoff.strftime("%Y-%m-%dT%H:%M:%SZ")
        log.info(f"=== WEEKLY MODE — videos after {published_after} ===")

    # Collect existing ids to skip duplicates
    existing_ids = get_existing_video_ids(supabase)
    log.info(f"Already have {len(existing_ids)} videos in Supabase")

    all_dfs: List[pd.DataFrame] = []
    total_topics = len(HEALTHCARE_TOPICS)

    for idx, topic in enumerate(HEALTHCARE_TOPICS, 1):
        log.info(f"[{idx}/{total_topics}] Searching: '{topic}'")
        try:
            topic_df = search_videos(
                youtube,
                query=topic,
                max_results=results_per_topic,
                published_after=published_after,
            )
        except Exception as e:
            log.warning(f"  Search failed for '{topic}': {e}")
            continue

        if topic_df.empty:
            log.info(f"  No results for '{topic}'")
            continue

        # Enrich with stats
        topic_df = enrich_with_stats(youtube, topic_df)

        # Filter by view count
        before = len(topic_df)
        topic_df = topic_df[topic_df["views"] >= min_views]
        log.info(f"  {len(topic_df)}/{before} videos pass {min_views:,} view threshold")

        # Drop videos we already have
        topic_df = topic_df[~topic_df["video_id"].isin(existing_ids)]
        if topic_df.empty:
            log.info(f"  All remaining are duplicates — skipping")
            continue

        all_dfs.append(topic_df)

    if not all_dfs:
        log.info("No new videos found across all topics. Done.")
        return

    combined = pd.concat(all_dfs, ignore_index=True)

    # Global dedup (a video might match multiple topics — keep first occurrence)
    combined = combined.drop_duplicates(subset="video_id", keep="first")
    log.info(f"Total new unique videos to process: {len(combined)}")

    # Compute derived features
    combined = compute_features(combined)

    # Fetch transcripts (this is the slow part)
    log.info("Fetching transcripts …")
    combined["transcript_text"] = combined["video_id"].apply(fetch_transcript)
    has_transcript = combined["transcript_text"].notna().sum()
    log.info(f"  Transcripts found for {has_transcript}/{len(combined)} videos")

    # Timestamp the ingestion
    combined["ingested_at"] = datetime.now(timezone.utc).isoformat()

    # Upsert metadata → healthcare_videos
    video_count = upsert_videos(supabase, combined)
    log.info(f"Upserted {video_count} videos to '{SUPABASE_TABLE_VIDEOS}'")

    # Upsert transcripts → healthcare_video_transcripts
    transcript_count = upsert_transcripts(supabase, combined)
    log.info(f"Upserted {transcript_count} transcripts to '{SUPABASE_TABLE_TRANSCRIPTS}'")

    log.info("Done ✓")


# ──────────────────────────────────────────────
# ENTRY POINT
# ──────────────────────────────────────────────

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="YouTube Healthcare Video Ingestion Pipeline")
    parser.add_argument(
        "--backfill",
        action="store_true",
        help="Run one-time initial dataset build (higher volume, no date filter). "
             "Remove this flag for weekly cron runs.",
    )
    args = parser.parse_args()

    run_ingestion(backfill=args.backfill)
