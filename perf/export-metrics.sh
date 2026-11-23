#!/usr/bin/env bash
set -euo pipefail

MINUTES="${1:-10}"
PROMETHEUS_URL="${PROMETHEUS_URL:-http://localhost:9090}"
OUT="perf/results/$(date +%Y%m%d-%H%M%S)"
END=$(date +%s)
START=$((END - MINUTES * 60))
STEP=5
F='application="lessonlog", uri!="/actuator/prometheus"'

mkdir -p "$OUT"

query() {
  curl -sG "$PROMETHEUS_URL/api/v1/query_range" \
    --data-urlencode "query=$2" \
    --data-urlencode "start=$START" \
    --data-urlencode "end=$END" \
    --data-urlencode "step=$STEP" > "$OUT/$1.json"
}

query rps_total "sum(rate(http_server_requests_seconds_count{$F}[30s]))"
query rps_by_api "sum by (method, uri) (rate(http_server_requests_seconds_count{$F}[30s]))"
query p95_total "histogram_quantile(0.95, sum by (le) (rate(http_server_requests_seconds_bucket{$F}[30s])))"
query p95_by_api "histogram_quantile(0.95, sum by (le, method, uri) (rate(http_server_requests_seconds_bucket{$F}[30s])))"
query p99_total "histogram_quantile(0.99, sum by (le) (rate(http_server_requests_seconds_bucket{$F}[30s])))"
query status_rate "sum by (status) (rate(http_server_requests_seconds_count{$F}[30s]))"
query hikari "{__name__=~\"hikaricp_connections_(active|idle|pending|max)\", application=\"lessonlog\"}"
query hikari_acquire_avg "rate(hikaricp_connections_acquire_seconds_sum{application=\"lessonlog\"}[30s]) / rate(hikaricp_connections_acquire_seconds_count{application=\"lessonlog\"}[30s])"
query tomcat_threads "{__name__=~\"tomcat_threads_(busy_threads|current_threads|config_max_threads)\", application=\"lessonlog\"}"
query cpu "{__name__=~\"(process|system)_cpu_usage\", application=\"lessonlog\"}"
query heap_used "sum(jvm_memory_used_bytes{application=\"lessonlog\", area=\"heap\"})"
query gc_pause "sum(rate(jvm_gc_pause_seconds_sum{application=\"lessonlog\"}[30s]))"

echo "$OUT"
