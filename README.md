# 🎵 LessonLog

음악 레슨 선생님이 학생과 레슨 기록 (내용, 숙제, 메모)을 관리하는 백엔드 서비스

직접 레슨을 하면서 손으로 일지를 쓰는 게 불편했고, 개인 레슨에서는 결국 기록을 안 하게 된 경험에서 시작했다. 기능 구현에 그치지 않고, 설계 판단의 근거를 남기고 실제 트래픽 상황을 가정해 성능을 측정·개선하는
것을 목표로 한다.

**진행 상태:** v1 개발 중 · **문서 기준일:** 2026-11-12

---

## 주요 기능

| 구분      | 기능                                                                  |
|-----------|-----------------------------------------------------------------------|
| 인증      | 회원가입, 로그인(JWT Access Token 발급), Bearer 토큰 기반 무상태 인증 |
| 학생      | 등록, 목록(페이징·정렬), 상세, 수정, 삭제                             |
| 레슨 일지 | 학생별 작성, 목록(최신순 페이징), 상세, 수정, 삭제                    |
| 권한      | `ADMIN` / `TEACHER` 역할 분리, 선생님은 자기 학생·일지만 접근         |
| 관리자    | 선생님 목록 조회, 선생님 탈퇴 처리(soft delete)                       |

## 기술 스택

| 영역        | 기술                                                          |
|-------------|---------------------------------------------------------------|
| 언어 / 빌드 | Java 26, Gradle                                               |
| 프레임워크  | Spring Boot 4.1, Spring MVC, Spring Security, Spring Data JPA |
| 인증        | JJWT 0.13 (HS256)                                             |
| DB          | H2 (개발용, MySQL 전환 예정)                                  |
| 기타        | MapStruct, Bean Validation, Lombok                            |
| 협업        | GitHub Issues / PR, CodeRabbit 리뷰                           |

---

## 설계 포인트

**인증 실패와 권한 부족을 구분한다**

- 토큰 없음·위조·만료·탈퇴 계정 토큰은 **401**, 권한 부족은 **403**으로 응답한다.
- JWT 필터는 무효 토큰이어도 예외를 던지지 않고 인증 정보만 비운다. 차단 여부는 인가 설정이, 응답은 `AuthenticationEntryPoint`가 결정한다. 그래서 만료된 토큰이 남아 있어도 로그인 같은
  공개 경로는 정상 통과한다.
- 공개 경로는 `POST /api/auth/signup`, `POST /api/auth/login`만 명시적으로 허용한다.

**소유권은 조회 조건으로 검증한다**

- 요청의 `userId`를 믿지 않고 인증 정보에서 사용자를 꺼낸다.
- `findByIdAndUserId`처럼 조회 조건에 소유자를 넣고, 다른 사용자의 리소스는 존재 여부를 숨기기 위해 404로 응답한다.

**계정은 지우지 않고 탈퇴 처리한다**

- 선생님 탈퇴는 `deletedAt`만 기록하고 학생·일지 데이터는 보존한다.
- 탈퇴 계정은 로그인 (`isEnabled`)과 이미 발급된 토큰 (필터의 사용자 조회 조건)을 모두 차단한다.
- 로그인 실패는 비밀번호 오류와 탈퇴 계정을 같은 메시지로 응답해 계정 존재 여부를 노출하지 않는다.

**에러 응답은 한 곳에서 관리한다**

- `ErrorCode`에 HTTP 상태와 메시지를 정의하고 `BusinessException`으로 던진다.
- Security의 401·403 응답도 같은 `ErrorResponse` 형식과 `ErrorCode` 메시지를 쓴다.

**필요할 때만 추상화한다**

- 구현체가 하나뿐인 서비스 인터페이스는 두지 않는다.
- 도메인 패키지 안에서는 `dto`만 하위 패키지로 나눈다.
- DTO는 불변 record로 작성한다.

---

## API

| 메서드             | 경로                                | 설명                         | 권한  |
|--------------------|-------------------------------------|------------------------------|-------|
| POST               | `/api/auth/signup`                  | 회원가입                     | 공개  |
| POST               | `/api/auth/login`                   | 로그인, 토큰 발급            | 공개  |
| GET                | `/api/students`                     | 내 학생 목록                 | 인증  |
| POST               | `/api/students`                     | 학생 등록                    | 인증  |
| GET · PUT · DELETE | `/api/students/{studentId}`         | 학생 상세 · 수정 · 삭제      | 인증  |
| GET · POST         | `/api/students/{studentId}/lessons` | 레슨 일지 목록 · 작성        | 인증  |
| GET · PUT · DELETE | `/api/lessons/{lessonId}`           | 레슨 일지 상세 · 수정 · 삭제 | 인증  |
| GET                | `/api/admin/users`                  | 선생님 목록                  | ADMIN |
| DELETE             | `/api/admin/users/{userId}`         | 선생님 탈퇴 처리             | ADMIN |

목록 조회는 `pageNumber`(1부터), `pageSize`(최대 30)를 받는다. 학생 목록은 `sortBy`(`id`, `name`)와 `sortOrder`, 레슨 일지는 `sortOrder`(기준
`lessonDate`)를 지원한다.

### 에러 응답

```json
{
  "errorMessage": "학생을 찾을 수 없습니다."
}
```

```json
{
  "errorMessage": "입력값 검증에 실패했습니다.",
  "errors": [
    "이름을 입력해주세요."
  ]
}
```

| 상태 | 상황                                                    |
|------|---------------------------------------------------------|
| 400  | 입력값 검증 실패, 잘못된 페이징·정렬 값, JSON 형식 오류 |
| 401  | 토큰 없음·무효·만료, 로그인 실패                        |
| 403  | 권한 부족                                               |
| 404  | 리소스 없음 또는 다른 사용자의 리소스                   |
| 409  | 이메일 중복                                             |

---

## 패키지 구조

```
com.project.lessonlog
├─ auth       로그인
├─ user       회원가입, 관리자 API, User, UserRole
├─ student    학생
├─ lesson     레슨 일지
├─ security   JWT 필터·발급, 401/403 응답 처리
├─ config     SecurityConfig, 공통 상수
├─ exception  ErrorCode, BusinessException, 전역 예외 처리
└─ util       PageableFactory
```

각 도메인 패키지는 Entity, Controller, Service, Repository, Mapper와 `dto/`로 구성된다.

---

## 실행 방법

JWT 서명 키를 환경변수로 넣어야 한다. (32바이트 이상)

```bash
export JWT_SECRET=$(openssl rand -base64 32)
./gradlew bootRun
```

H2 인메모리 DB를 사용하며 시작 시 `data.sql`로 테스트 데이터가 들어간다.

| 계정                | 비밀번호 | 역할                |
|---------------------|----------|---------------------|
| `teacher1@test.com` | `1234`   | TEACHER (학생 1, 2) |
| `teacher2@test.com` | `1234`   | TEACHER (학생 3, 4) |
| `admin@test.com`    | `1234`   | ADMIN               |

---

## 개발 방식

```
Issue → Branch → Commit → Pull Request → CodeRabbit 리뷰 → 리뷰 분류 및 반영 → dev 병합
```

CodeRabbit 리뷰는 그대로 수용하지 않고 "이번 PR에서 수정 / 다음 작업 / 보류"로 나눠 판단한다.

---

## 알려진 한계

- Student 삭제는 아직 물리 삭제다. (soft delete 전환 예정)
- `LessonNote.student`가 기본 EAGER 로딩이다.
- `lessonContent`, `homework`, `memo`에 길이 제한이 없다.
- 같은 날짜의 레슨 일지는 페이지 간 순서가 보장되지 않는다.
- 로그아웃은 클라이언트가 토큰을 삭제하는 방식이라, 탈취된 토큰은 만료 (1시간)까지 유효하다.
- 자동화 테스트가 없다. 현재는 Postman / curl로 검증한다.

## 다음 단계

- [ ] Student soft delete → v1 기능 완료
- [ ] 정리 작업: N+1 점검, LAZY 전환, 엔티티 검증 정리
- [ ] MySQL 전환, 배포
- [ ] LessonNote 약 20만 건 데이터 구성, Prometheus · Grafana 관측 환경
- [ ] 수업 종료 직후 요청이 몰리는 상황을 k6로 재현해 병목 분석 · 개선 · 재측정
