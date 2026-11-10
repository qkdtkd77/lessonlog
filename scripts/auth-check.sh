#!/usr/bin/env bash
# 인증 실패 응답 확인용 curl 스크립트
# 사용법: ./scripts/auth-check.sh
#   - 서버가 먼저 떠 있어야 함
#   - 만료 토큰 케이스는 JWT_SECRET 환경변수가 있을 때만 실행
#     예) JWT_SECRET='서버와 같은 값' ./scripts/auth-check.sh

BASE_URL="${BASE_URL:-http://localhost:8080}"
FAILED=0
EMAIL="teacher1@test.com"
PASSWORD="1234"

run() {
  # $1: 케이스명, $2: 목표, 나머지: curl 인자
  local name="$1" expected="$2"; shift 2
  local out status body
  out=$(curl -s -w $'\n%{http_code}' "$@")
  status="${out##*$'\n'}"
  body="${out%$'\n'*}"
  [ ${#body} -gt 300 ] && body="${body:0:300}... (생략)"
  local want="${expected%%[!0-9]*}"   # 목표 문자열 앞의 숫자(상태 코드)만 비교
  local mark="✅ 통과"
  if [ "$status" != "$want" ]; then
    mark="❌ 실패"
    FAILED=$((FAILED + 1))
  fi
  printf '\n[%s] %s\n  목표: %s\n  결과: %s\n  본문: %s\n' "$name" "$mark" "$expected" "$status" "${body:-(없음)}"
}

b64url() { openssl base64 -A | tr '+/' '-_' | tr -d '='; }

# 0. 정상 로그인으로 토큰 발급
LOGIN=$(curl -s -X POST "$BASE_URL/api/auth/login" \
  -H 'Content-Type: application/json' \
  -d "{\"email\":\"$EMAIL\",\"password\":\"$PASSWORD\"}")
TOKEN=$(printf '%s' "$LOGIN" | sed -n 's/.*"accessToken":"\([^"]*\)".*/\1/p')
if [ -z "$TOKEN" ]; then
  echo "로그인 실패 - 서버 실행 여부와 계정을 확인해: $LOGIN"
  exit 1
fi
echo "토큰 발급 완료"

# 위조 토큰: 서명부 가운데 글자 하나 변경
HEAD_PAYLOAD="${TOKEN%.*}"
SIG="${TOKEN##*.}"
MID=$(( ${#SIG} / 2 ))
CH="${SIG:$MID:1}"; [ "$CH" = "A" ] && NEW="B" || NEW="A"
FORGED="$HEAD_PAYLOAD.${SIG:0:$MID}$NEW${SIG:$((MID+1))}"

run "정상 토큰 (기준)"              "200" \
  "$BASE_URL/api/students" -H "Authorization: Bearer $TOKEN"

run "1. 토큰 없음"                  "401 + ErrorResponse" \
  "$BASE_URL/api/students"

run "2. 위조 토큰"                  "401 + ErrorResponse" \
  "$BASE_URL/api/students" -H "Authorization: Bearer $FORGED"

if [ -n "$JWT_SECRET" ]; then
  NOW=$(date +%s)
  H=$(printf '{"alg":"HS256"}' | b64url)
  P=$(printf '{"sub":"1","email":"%s","iat":%d,"exp":%d}' "$EMAIL" $((NOW-120)) $((NOW-60)) | b64url)
  S=$(printf '%s.%s' "$H" "$P" | openssl dgst -sha256 -hmac "$JWT_SECRET" -binary | b64url)
  EXPIRED="$H.$P.$S"
  run "3. 만료 토큰"                "401 + ErrorResponse" \
    "$BASE_URL/api/students" -H "Authorization: Bearer $EXPIRED"
else
  printf '\n[3. 만료 토큰]\n  건너뜀: JWT_SECRET 환경변수 없음\n'
fi

run "4. 로그인 실패 (비밀번호 틀림)" "401 + ErrorResponse" \
  -X POST "$BASE_URL/api/auth/login" -H 'Content-Type: application/json' \
  -d "{\"email\":\"$EMAIL\",\"password\":\"wrong-password\"}"

run "5. 무효 토큰을 단 채 로그인"    "200 (permitAll 경로는 통과)" \
  -X POST "$BASE_URL/api/auth/login" -H 'Content-Type: application/json' \
  -H "Authorization: Bearer $FORGED" \
  -d "{\"email\":\"$EMAIL\",\"password\":\"$PASSWORD\"}"

run "6. 빈 Bearer 토큰"             "401 + ErrorResponse" \
  "$BASE_URL/api/students" -H "Authorization: Bearer "

run "7. 다른 사용자 학생 조회 (회귀)" "404" \
  "$BASE_URL/api/students/3" -H "Authorization: Bearer $TOKEN"

echo
if [ "$FAILED" -gt 0 ]; then
  echo "❌ ${FAILED}개 케이스 실패"
  exit 1
fi
echo "✅ 전부 통과"
