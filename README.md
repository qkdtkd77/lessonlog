# 🎵 LessonLog

음악 레슨 선생님이 학생과 레슨 기록 (내용, 숙제, 메모)을 관리하는 백엔드 서비스

직접 레슨을 하면서 손으로 일지를 쓰는 게 불편했고, 개인 레슨에서는 결국 기록을 안 하게 된 경험에서 시작했다. 기능 구현에 그치지 않고, 설계 판단의 근거를 남기고 실제 트래픽 상황을 가정해 성능을 측정하고
개선하는 것을 목표로 한다.

**진행 상태:** v1 완료, 성능 실험 준비 중 / **문서 기준일:** 2026-11-17

---

## 주요 기능

| 구분      | 기능                                                                  |
|-----------|-----------------------------------------------------------------------|
| 인증      | 회원가입, 로그인(JWT Access Token 발급), Bearer 토큰 기반 무상태 인증 |
| 학생      | 등록, 목록(페이징, 정렬), 상세, 수정, 삭제(soft delete)               |
| 레슨 일지 | 학생별 작성, 목록(최신순 페이징), 상세, 수정, 삭제                    |
| 권한      | `ADMIN` / `TEACHER` 역할 분리, 선생님은 자기 학생과 일지만 접근       |
| 관리자    | 선생님 목록 조회, 선생님 탈퇴 처리(soft delete)                       |

## 기술 스택

| 영역        | 기술                                                          |
|-------------|---------------------------------------------------------------|
| 언어 / 빌드 | Java 25 (LTS), Gradle                                         |
| 프레임워크  | Spring Boot 4.1, Spring MVC, Spring Security, Spring Data JPA |
| 인증        | JJWT 0.13 (HS256)                                             |
| DB          | MySQL (Docker), 테스트는 Testcontainers                       |
| 기타        | MapStruct, Bean Validation, Lombok                            |
| 협업        | GitHub Issues / PR, CodeRabbit 리뷰                           |

---

## 설계 포인트

**인증 실패와 권한 부족을 구분한다**

- 토큰 없음, 위조, 만료, 탈퇴 계정 토큰은 **401**, 권한 부족은 **403**으로 응답한다.
- JWT 필터는 무효 토큰이어도 예외를 던지지 않고 인증 정보만 비운다. 차단 여부는 인가 설정이, 응답은 `AuthenticationEntryPoint`가 결정한다. 그래서 만료된 토큰이 남아 있어도 로그인 같은
  공개 경로는 정상 통과한다.
- 공개 경로는 `POST /api/auth/signup`, `POST /api/auth/login`만 명시적으로 허용한다.

**역할마다 필요한 권한만 준다**

- 관리자는 계정 관리(선생님 탈퇴 처리)만 담당한다. 학생 연락처 같은 개인정보는 담당 선생님만 볼 수 있도록 학생, 일지 API는 `TEACHER`만 호출할 수 있다.
- 프론트에서 메뉴를 숨기는 것과 별개로 서버 인가 규칙으로 막는다.

**소유권은 조회 조건으로 검증한다**

- 요청의 `userId`를 믿지 않고 인증 정보에서 사용자를 꺼낸다.
- `findByIdAndUserId`처럼 조회 조건에 소유자를 넣고, 다른 사용자의 리소스는 존재 여부를 숨기기 위해 404로 응답한다.

**계정과 학생은 지우지 않고 탈퇴 처리한다**

- 선생님 탈퇴와 학생 삭제는 `deletedAt`만 기록하고 일지 데이터는 보존한다.
- 탈퇴 계정은 로그인 (`isEnabled`)과 이미 발급된 토큰 (필터의 사용자 조회 조건)을 모두 차단한다.
- 로그인 실패는 비밀번호 오류와 탈퇴 계정을 같은 메시지로 응답해 계정 존재 여부를 노출하지 않는다.

**입력 검증과 DB 제약을 나눈다**

- 입력 형식과 길이는 Request DTO에서 검증해 400으로 응답한다.
- 엔티티는 `@Column(nullable, length)`로 스키마 제약만 담당한다.
- 이메일 중복은 조회로 먼저 거르고, 동시 가입은 DB unique 제약에 최종 방어를 맡겨 409로 응답한다.

**에러 응답은 한 곳에서 관리한다**

- `ErrorCode`에 HTTP 상태와 메시지를 정의하고 `BusinessException`으로 던진다.
- Security의 401, 403 응답도 같은 `ErrorResponse` 형식과 `ErrorCode` 메시지를 쓴다.
- 처리되지 않은 예외는 500과 공통 메시지로 응답하고, 서버 로그에 스택트레이스를 남긴다.

**연관관계는 필요할 때만 불러온다**

- 연관 엔티티는 LAZY로 로딩하고 OSIV를 끈다. DTO 변환은 서비스 트랜잭션 안에서 끝낸다.
- 응답에 쓰지 않는 학생을 EAGER로 추가 조회하던 쿼리를 제거했다.

**필요할 때만 추상화한다**

- 구현체가 하나뿐인 서비스 인터페이스는 두지 않는다.
- 도메인 패키지 안에서는 `dto`와 Swagger 문서용 `docs`만 하위 패키지로 나눈다.
- DTO는 불변 record로 작성한다.

---

## API

| 메서드             | 경로                                | 설명                         | 권한    |
|--------------------|-------------------------------------|------------------------------|---------|
| POST               | `/api/auth/signup`                  | 회원가입                     | 공개    |
| POST               | `/api/auth/login`                   | 로그인, 토큰과 역할 반환     | 공개    |
| GET                | `/api/students`                     | 내 학생 목록                 | TEACHER |
| POST               | `/api/students`                     | 학생 등록                    | TEACHER |
| GET, PUT, DELETE   | `/api/students/{studentId}`         | 학생 상세, 수정, 삭제        | TEACHER |
| GET, POST          | `/api/students/{studentId}/lessons` | 레슨 일지 목록, 작성         | TEACHER |
| GET, PUT, DELETE   | `/api/lessons/{lessonId}`           | 레슨 일지 상세, 수정, 삭제   | TEACHER |
| GET                | `/api/admin/users`                  | 선생님 목록                  | ADMIN   |
| DELETE             | `/api/admin/users/{userId}`         | 선생님 탈퇴 처리             | ADMIN   |

목록 조회는 `pageNumber`(1부터), `pageSize`(최대 30)를 받는다. 학생 목록은 `sortBy`(`id`, `name`)와 `sortOrder`, 레슨 일지는 `sortOrder`(기준
`lessonDate`, 같은 날짜는 id 순)를 지원한다.

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

| 상태 | 상황                                                      |
|------|-----------------------------------------------------------|
| 400  | 입력값 검증 실패, 잘못된 페이징 또는 정렬 값, JSON 형식 오류 |
| 401  | 토큰 없음, 무효, 만료, 로그인 실패                        |
| 403  | 권한 부족                                                 |
| 404  | 리소스 없음 또는 다른 사용자의 리소스                     |
| 409  | 이메일 중복                                               |
| 500  | 처리되지 않은 서버 오류                                   |

---

## 패키지 구조

```
com.project.lessonlog
├─ auth       회원가입, 로그인
├─ user       관리자 API, User, UserRole, 사용자 생성 규칙
├─ student    학생
├─ lesson     레슨 일지
├─ security   JWT 필터와 발급, 401/403 응답 처리
├─ config     SecurityConfig, SwaggerConfig, 공통 상수
├─ exception  ErrorCode, BusinessException, 전역 예외 처리
└─ util       PageableFactory
```

각 도메인 패키지는 Entity, Controller, Service, Repository, Mapper와 `dto/`, `docs/`로 구성된다. Swagger 애노테이션은 `docs/`의 인터페이스에 두고 컨트롤러가 구현한다.

---

## 실행 방법

Docker가 필요하다. 로컬 DB와 테스트 DB 모두 MySQL 컨테이너를 사용한다.

**1. 환경변수 파일 만들기**

`.env.example`을 복사해 `.env`를 만들고 값을 채운다. `.env`는 Git에 올라가지 않는다.

```bash
cp .env.example .env
```

| 변수                  | 설명                                     |
|-----------------------|------------------------------------------|
| `MYSQL_ROOT_PASSWORD` | MySQL root 비밀번호                      |
| `DB_USERNAME`         | 애플리케이션 접속 계정                   |
| `DB_PASSWORD`         | 애플리케이션 접속 비밀번호               |
| `JWT_SECRET`          | JWT 서명 키 (32바이트 이상)              |

JWT 서명 키는 `openssl rand -base64 32`로 만들 수 있다.

**2. MySQL 실행**

```bash
docker compose up -d
```

`compose.yml`이 `.env`를 읽어 MySQL을 `localhost:3300`에 띄운다.

**3. 서버 실행**

```bash
./gradlew bootRun
```

기본 프로필은 `local`이다. `local` 프로필은 `.env`를 읽어 MySQL에 접속하고, 시작할 때마다 테이블을 새로 만든 뒤 `data.sql`로 테스트 데이터를 넣는다.

**4. 테스트**

```bash
./gradlew test
```

테스트는 `test` 프로필로 실행되며 Testcontainers가 별도 MySQL 컨테이너를 띄웠다가 종료 후 정리한다. 로컬 MySQL을 켜 둘 필요는 없다.

API 문서는 실행 후 `http://localhost:8080/swagger-ui/index.html`에서 볼 수 있다. 로그인 API로 받은 토큰을 우측 상단 Authorize에 넣으면 인증이 필요한 API도 호출할 수 있다.

| 계정                | 비밀번호 | 역할                |
|---------------------|----------|---------------------|
| `teacher1@test.com` | `1234`   | TEACHER (학생 11명, 2페이지 분량) |
| `teacher2@test.com` | `1234`   | TEACHER (학생 2명) |
| `teacher3@test.com` | `1234`   | TEACHER (학생 3명) |
| `teacher4@test.com` | `1234`   | 탈퇴한 TEACHER (로그인 차단 확인용) |
| `admin@test.com`    | `1234`   | ADMIN               |
| `admin2@test.com`   | `1234`   | ADMIN               |

---

## 개발 방식

```
Issue → Branch → Commit → Pull Request → CodeRabbit 리뷰 → 리뷰 분류 및 반영 → dev 병합
```

CodeRabbit 리뷰는 그대로 수용하지 않고 "이번 PR에서 수정 / 다음 작업 / 보류"로 나눠 판단한다.

---

## 알려진 한계

- 로그아웃은 클라이언트가 토큰을 삭제하는 방식이라, 탈취된 토큰은 만료 (1시간)까지 유효하다.
- 자동화 테스트는 애플리케이션 기동 확인(`contextLoads`)뿐이다. API 검증은 Postman / curl로 한다.

## 다음 단계

- [x] v1 기능 완료, 안정화 (예외 응답, 입력 제약, 권한 정리, LAZY 전환)
- [x] Swagger 문서화
- [x] 전화번호 형식 검증
- [x] MySQL 전환, Testcontainers 기반 테스트
- [ ] 배포
- [ ] LessonNote 약 20만 건 데이터 구성, Prometheus와 Grafana 관측 환경
- [ ] 수업 종료 직후 요청이 몰리는 상황을 k6로 재현해 병목 분석, 개선, 재측정
