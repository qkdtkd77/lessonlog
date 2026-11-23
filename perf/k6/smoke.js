import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';
const PASSWORD = '1234';

export const options = {
  stages: [
    { duration: '30s', target: 10 },
    { duration: '1m', target: 30 },
    { duration: '30s', target: 0 },
  ],
};

export function setup() {
  const tokens = [];
  for (let i = 1; i <= 30; i++) {
    const email = `teacher${String(i).padStart(4, '0')}@perf.test`;
    const res = http.post(
      `${BASE_URL}/api/auth/login`,
      JSON.stringify({ email, password: PASSWORD }),
      { headers: { 'Content-Type': 'application/json' } },
    );
    if (res.status === 200) {
      tokens.push(res.json('accessToken'));
    } else if (i === 1) {
      console.warn(`로그인 실패 예시: ${email} → ${res.status} ${res.body}`);
    }
  }
  if (tokens.length === 0) {
    throw new Error('로그인에 성공한 계정이 없습니다. perf 프로필로 서버가 실행 중인지 확인하세요.');
  }
  return { tokens };
}

export default function (data) {
  const token = data.tokens[(__VU - 1) % data.tokens.length];
  const headers = { Authorization: `Bearer ${token}` };

  const students = http.get(`${BASE_URL}/api/students?pageNumber=1&pageSize=10`, { headers });
  check(students, { '학생 목록 200': (r) => r.status === 200 });

  const list = students.json('students') || [];
  if (list.length > 0) {
    const studentId = list[Math.floor(Math.random() * list.length)].id;
    const lessons = http.get(`${BASE_URL}/api/students/${studentId}/lessons?pageNumber=1&pageSize=10`, { headers });
    check(lessons, { '일지 목록 200': (r) => r.status === 200 });
  }

  sleep(0.5);
}
