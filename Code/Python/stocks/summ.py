# app.py
import time
from datetime import datetime, timedelta
import pandas as pd
import yfinance as yf
import plotly.graph_objects as go
import streamlit as st

st.set_page_config(page_title="S&P 500 Live (Near-Real-Time)", layout="wide")

st.title("📈 S&P 500 Live (Near-Real-Time)")
st.caption(
    "Free data sources are typically delayed ~15 minutes for indices. "
    "For true tick-by-tick realtime, use a paid market data feed."
)

# --- Sidebar controls
with st.sidebar:
    st.header("Settings")
    symbol = st.selectbox(
        "Ticker",
        options=["^GSPC", "SPY", "ES=F"],
        index=0,
        help="^GSPC = S&P 500 index (delayed). SPY = ETF (delayed). ES=F = S&P 500 E-mini Futures (more hours).",
    )
    interval = st.selectbox(
        "Interval",
        options=["1m", "2m", "5m", "15m"],
        index=0,
        help="Shorter intervals are more live but have less history."
    )
    lookback_minutes = st.slider(
        "Lookback (minutes)",
        min_value=30, max_value=6*60, value=240, step=30,
        help="How far back to pull intraday candles."
    )
    refresh_sec = st.slider("Auto-refresh seconds", 5, 60, 15, 5)
    show_ema = st.checkbox("Show EMA (20, 50)", value=True)
    show_vol = st.checkbox("Show Volume", value=True)

# Map interval to period supported by yfinance
period_map = {"1m": "1d", "2m": "5d", "5m": "5d", "15m": "1mo"}
period = period_map.get(interval, "5d")

end_utc = datetime.utcnow()
start_utc = end_utc - timedelta(minutes=lookback_minutes)

@st.cache_data(ttl=60)
def load_data(sym: str, per: str, itv: str) -> pd.DataFrame:
    df = yf.download(sym, period=per, interval=itv, progress=False)
    if df is None or df.empty:
        return pd.DataFrame()
    # Make timezone explicit and convert to America/Chicago
    try:
        df = df.tz_localize("UTC").tz_convert("America/Chicago")
    except Exception:
        df = df.tz_convert("America/Chicago")
    df.index.name = "Datetime"
    return df

df = load_data(symbol, period, interval)
if df.empty:
    st.warning("No data returned (market closed or unsupported interval/period). Try SPY or ES=F, or a longer interval.")
    st.stop()

# Trim to lookback window
start_cst = pd.Timestamp(start_utc, tz="UTC").tz_convert("America/Chicago")
df = df[df.index >= start_cst]
if df.empty:
    st.warning("No data in the selected lookback window.")
    st.stop()

# Indicators
if show_ema:
    df["EMA20"] = df["Close"].ewm(span=20, adjust=False).mean()
    df["EMA50"] = df["Close"].ewm(span=50, adjust=False).mean()

# --- Safely extract scalars for the metric
last_price_val = df["Close"].iloc[-1]
if isinstance(last_price_val, pd.Series):
    last_price_val = last_price_val.iloc[0]
last_price = float(last_price_val)

last_time_val = df.index[-1]
if isinstance(last_time_val, pd.Series):
    last_time_val = last_time_val.iloc[0]
last_time = last_time_val

left, right = st.columns([3, 1], gap="large")
with right:
    st.metric("Last Price", f"{last_price:,.2f}", help=f"As of {last_time.strftime('%Y-%m-%d %H:%M:%S %Z')}")
    st.write(f"**Symbol:** `{symbol}`")
    st.write(f"**Interval:** `{interval}`")
    st.write(f"**Bars:** `{len(df)}`")
    st.write(f"**Updated:** {datetime.now().strftime('%Y-%m-%d %H:%M:%S')} (local)")

with left:
    fig = go.Figure()
    fig.add_trace(go.Candlestick(
        x=df.index, open=df["Open"], high=df["High"], low=df["Low"], close=df["Close"],
        name="Price"
    ))
    if show_ema:
        fig.add_trace(go.Scatter(x=df.index, y=df["EMA20"], mode="lines", name="EMA 20"))
        fig.add_trace(go.Scatter(x=df.index, y=df["EMA50"], mode="lines", name="EMA 50"))
    fig.update_layout(
        height=520, margin=dict(l=10, r=10, t=40, b=10),
        xaxis_rangeslider_visible=False,
        legend=dict(orientation="h", yanchor="bottom", y=1.02, xanchor="right", x=1),
        title=f"{symbol} Intraday ({interval})"
    )
    st.plotly_chart(fig, use_container_width=True)

    if show_vol and "Volume" in df.columns:
        vol_fig = go.Figure()
        vol_fig.add_trace(go.Bar(x=df.index, y=df["Volume"], name="Volume"))
        vol_fig.update_layout(
            height=220, margin=dict(l=10, r=10, t=30, b=10),
            title="Volume"
        )
        st.plotly_chart(vol_fig, use_container_width=True)

st.caption("Data: Yahoo Finance via yfinance. Timezone: America/Chicago.")
time.sleep(refresh_sec)
st.rerun()
