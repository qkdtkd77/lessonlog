# 🎵 LessonLog

음악 레슨 강사가 학생 정보와 레슨 내용, 과제, 메모를 관리하기 위한 백엔드 프로젝트

직접 레슨을 진행하며 손으로 레슨일지를 쓰는 것이 불편했고, 개인 레슨에서는 결국 기록을 하지 않게 된 경험에서 시작했다. Java와 Spring Boot로 기능을 구현하고, 학습한 내용을 적용하며 실제 사용할 수 있는 서비스로 발전시키는 것을 목표로 한다.

**진행 상태:** 개발 중 · **문서 기준일:** 2026-09-30

Student와 LessonNote의 CRUD(LessonNote 단건 조회 제외)를 구현했다. 저장·조회는 Spring Data JPA + H2 기반이며, 요청/응답은 DTO로 분리되어 API에 JPA 엔티티가 직접 노출되지 않는다. 목록 조회는 페이징을 지원하고, 예외는 도메인별 커스텀 예외와 전역 핸들러로 일관되게 응답한다.

---

## 📋 구현 현황

| 구분                            | 상태   | 내용                                                                 |
|---------------------------------|--------|----------------------------------------------------------------------|
| Student 관리                    | 구현   | 등록, 목록 조회(페이징), 상세 조회, 수정, 삭제                       |
| Student 삭제                    | 구현   | 학생 삭제 시 해당 학생의 LessonNote를 함께 삭제 (단일 트랜잭션)      |
| LessonNote 등록                 | 구현   | Student 존재 여부 확인 후 등록                                       |
| LessonNote 목록 조회            | 구현   | Student별 조회, `lessonDate` 기준 정렬(기본 최신순), 페이징          |
| LessonNote 수정 / 삭제          | 구현   | 조회 후 도메인 메서드로 상태 변경(변경 감지), 삭제                   |
| LessonNote 단건 상세 조회       | 미구현 | -                                                                    |
| JPA 기반 저장·조회              | 구현   | 인메모리 Repository 제거, `JpaRepository` 전환 완료                  |
| Student–LessonNote 연관관계     | 구현   | `LessonNote.student` `@ManyToOne` (현재 기본 EAGER, LAZY 전환 예정)  |
| 트랜잭션 / 변경 감지            | 구현   | 수정·삭제에 `@Transactional` 적용, 조회용 `readOnly`는 미적용        |
| 요청 DTO / Validation           | 구현   | Request DTO + Bean Validation                                        |
| 응답 DTO                        | 구현   | Request / Dto / Response 역할 분리, Entity 직접 노출 제거            |
| Entity ↔ DTO 매핑               | 구현   | MapStruct Mapper                                                     |
| 목록 페이징 / 정렬              | 구현   | `PageableFactory`, `PageResponse<T>` (페이지 크기는 서버 고정 10)    |
| 전역 예외 처리                  | 구현   | 도메인별 커스텀 예외 + `ErrorResponse` + `GlobalExceptionHandler`    |
| 감사 컬럼(createdAt/updatedAt)  | 미구현 | -                                                                    |
| User / 인증·인가                | 미구현 | `user` 패키지에 뼈대 클래스만 존재, 엔티티·로직 없음                 |
| 테스트 코드                     | 미구현 | `contextLoads` 1건만 존재, 수동 요청(`.http`)으로 확인               |
| CI                              | 미구현 | -                                                                    |

---

## 🛠️ 기술 구성

| 영역              | 사용 기술                                          |
|-------------------|----------------------------------------------------|
| 언어              | Java 26 (Gradle toolchain 설정 기준)               |
| 빌드              | Gradle 9.7.1 (Wrapper)                             |
| 웹 / 애플리케이션 | Spring Boot 4.1.1, Spring MVC, Actuator            |
| 영속성            | Spring Data JPA, Hibernate, H2 (인메모리)          |
| 요청 검증         | Jakarta Bean Validation                            |
| 객체 매핑         | MapStruct 1.6.3                                    |
| 코드 작성 보조    | Lombok                                             |
| 개발 및 리뷰      | Git, GitHub Issues / Pull Requests, CodeRabbit     |

> H2는 현재 개발용으로 사용 중이며 MySQL 전환은 미완료다.

---

## 🧩 도메인

| 도메인     | 주요 필드                                                | 역할                  |
|------------|----------------------------------------------------------|-----------------------|
| Student    | id, name, instrument, phone, memo                        | 학생 정보 관리        |
| LessonNote | id, student, lessonDate, lessonContent, homework, memo   | 학생별 레슨 기록 관리 |

- `LessonNote`는 `@ManyToOne`으로 `Student`를 참조한다 (`student_id`, not null). API 응답에서는 엔티티 대신 `studentId` 값만 노출한다.
- 엔티티 상태 변경은 Setter 대신 `updateStudent()`, `updateLessonNote()` 도메인 메서드로만 수행하며, 서비스의 `@Transactional` 안에서 변경 감지(Dirty Checking)로 반영한다.
- 엔티티 기본 생성자는 `protected`로 제한한다.

---

## 🏗️ 현재 구조

```
HTTP 요청
  → Controller     : Request DTO 수신, @Valid 검증, Response 구성
  → Service        : 존재 여부 확인, 페이징 요청값 검증, 트랜잭션, DTO 변환 위임
  → Repository     : JpaRepository (Spring Data JPA)
  → H2 Database
```

```
com.project.lessonlog
├─ student/    controller · service · repository · domain · dto · mapper · config
├─ lesson/     controller · service · repository · domain · dto · mapper · config
├─ user/       뼈대 클래스만 존재 (미구현)
├─ common/dto  PageResponse
├─ exception/  커스텀 예외, ErrorResponse, GlobalExceptionHandler
└─ util/       PageableFactory
```

- Controller → Service → Repository로 역할을 분리한다. Repository는 Entity, Service는 Dto, Controller는 Response로 감싸 반환한다.
- Entity ↔ Dto 변환은 MapStruct Mapper가 담당한다.
- 단건 응답은 `StudentResponse` / `LessonNoteResponse`, 목록 응답은 `PageResponse<T>`를 사용한다.
- 예외는 도메인별 커스텀 예외로 발생시키고 `GlobalExceptionHandler`에서 HTTP 응답으로 변환한다.

---

## 🔌 API

| 대상       | 메서드 | 경로                                | 기능                       |
|------------|--------|-------------------------------------|----------------------------|
| Student    | POST   | `/api/students`                     | 학생 등록                  |
| Student    | GET    | `/api/students`                     | 학생 목록 조회 (페이징)    |
| Student    | GET    | `/api/students/{studentId}`         | 학생 상세 조회             |
| Student    | PUT    | `/api/students/{studentId}`         | 학생 수정                  |
| Student    | DELETE | `/api/students/{studentId}`         | 학생 삭제 (레슨 기록 포함) |
| LessonNote | POST   | `/api/students/{studentId}/lessons` | 레슨 기록 등록             |
| LessonNote | GET    | `/api/students/{studentId}/lessons` | 학생별 목록 조회 (페이징)  |
| LessonNote | PUT    | `/api/lessons/{lessonId}`           | 레슨 기록 수정             |
| LessonNote | DELETE | `/api/lessons/{lessonId}`           | 레슨 기록 삭제             |

### 목록 조회 파라미터

| 대상       | 파라미터     | 설명        | 비고                                                   |
|------------|--------------|-------------|--------------------------------------------------------|
| 공통       | `pageNumber` | 페이지 번호 | 1부터 시작, 내부에서 0-based로 보정. 기본값 1          |
| LessonNote | `sortOrder`  | 정렬 방향   | `asc`, `desc`만 허용. 기본값 `desc` (`lessonDate` 기준) |

- 페이지 크기는 서버에서 10으로 고정한다.
- Student 목록은 `id` 오름차순으로 고정한다.
- 정렬 필드 화이트리스트와 검증 로직은 `PageableFactory`에 구현되어 있으며, 정렬 기준·페이지 크기를 클라이언트에 열 때 그대로 사용할 수 있다.

### 요청 예시

```http
POST /api/students/1/lessons
Content-Type: application/json

{
  "lessonDate": "2026-09-30",
  "lessonContent": "슬랩 옥타브 패턴",
  "homework": "옥타브 패턴 3개",
  "memo": "손목 힘 빼기"
}
```

필수 값: Student는 `name`, `instrument`, `phone`(하이픈 포함 13자, 예: `010-1234-5678`), LessonNote는 `lessonDate`, `lessonContent`, `homework`. `memo`는 선택이다.

---

## 📬 응답 형식

단건 응답은 `data`로 감싼다.

```json
{
  "data": {
    "id": 1,
    "studentId": 1,
    "lessonContent": "슬랩 옥타브 패턴",
    "homework": "옥타브 패턴 3개",
    "memo": "손목 힘 빼기",
    "lessonDate": "2026-09-30"
  }
}
```

목록 응답은 `PageResponse`를 사용한다.

```json
{
  "data": [ ... ],
  "pageNumber": 1,
  "pageSize": 10,
  "totalElements": 10,
  "totalPages": 1,
  "lastPage": true
}
```

에러 응답은 `ErrorResponse`로 통일한다. 단일 오류는 `errorMessage`만, Validation 실패는 `errors` 배열을 함께 반환한다.

```json
{ "errorMessage": "학생을 찾을 수 없습니다." }
```

```json
{
  "errorMessage": "입력값 검증에 실패했습니다.",
  "errors": ["이름을 입력해주세요.", "악기를 입력해주세요."]
}
```

| 상황                                        | 응답                                                  |
|---------------------------------------------|-------------------------------------------------------|
| 조회·수정 성공                              | 200 OK                                                |
| 등록 성공                                   | 201 Created                                           |
| 삭제 성공                                   | 204 No Content                                        |
| Validation 실패                             | 400 Bad Request (`errors`에 필드별 메시지)            |
| 요청 본문 JSON 형식·필드 타입 오류          | 400 Bad Request (`lessonDate` 형식 오류 포함)         |
| 경로·파라미터 타입 오류                     | 400 Bad Request                                       |
| 잘못된 페이징·정렬 요청값                   | 400 Bad Request (`InvalidPaginationException`)        |
| Student / LessonNote 미존재                 | 404 Not Found                                         |

내부 예외 메시지는 그대로 노출하지 않고 예외별로 정의한 고정 메시지를 반환한다.

---

## 🔍 코드 리뷰와 개선

CodeRabbit PR 리뷰를 검토하여 반영한 주요 내용:

- 잘못된 `lessonDate` 입력이 500 응답으로 이어질 가능성을 확인하고 입력 검증 및 400 처리 추가
- `GlobalExceptionHandler`가 내부 예외 메시지를 그대로 반환하던 문제를 확인하고 예외별 고정 메시지로 분리
- 인메모리 저장소 시절의 `HashMap` 동시성 문제를 `ConcurrentHashMap`으로 보완하고 `studentId` 사전 검증 추가 (이후 JPA 전환으로 대체)

이후 직접 리팩터링한 내용:

- Repository JPA 전환, 엔티티 `id` Setter 제거
- Request / Dto / Response 분리, MapStruct 적용
- `Student`–`LessonNote` 연관관계 매핑, 수정·삭제에 트랜잭션과 변경 감지 적용
- 페이징 조회 로직을 `PageableFactory`로 공통화

---

## 🔄 개발 방식

```
GitHub Issue
  → 기능 브랜치
  → 기능 단위 Commit
  → Pull Request
  → CodeRabbit 리뷰
  → 리뷰 검토 및 수정
  → dev 병합
```

날짜별로 구현 내용, 설계 판단, 트러블슈팅을 개발일지로 정리한다. 리뷰 기록은 발견한 문제와 검토 결과, 실제 반영 내용을 중심으로 남긴다.

---

## ⚠️ 알려진 한계

현재 코드 기준으로 확인된 개선 대상이다.

- `lessonContent`, `homework`, `memo`에 길이 제한이 없어 기본 컬럼 길이(255자)를 넘으면 DB 예외가 발생한다.
- 전화번호 검증이 길이(13자)만 확인하며 형식은 검증하지 않는다.
- 존재하지 않는 학생의 레슨 목록을 조회하면 404가 아닌 빈 목록(200)이 반환된다.
- `lessonDate`만으로 정렬해 같은 날짜의 기록이 여러 개면 페이지 간 순서가 보장되지 않는다.
- 정의하지 않은 예외에 대한 폴백 핸들러가 없어 `ErrorResponse` 형식이 아닌 기본 에러 응답이 나갈 수 있다.
- `@ManyToOne` 기본 fetch(EAGER) 사용, 조회 메서드에 `readOnly` 트랜잭션 미적용.
- `data.sql`이 H2 전용 문법을 사용하고, `application.yaml`이 개발용 설정(`create-drop`, `show-sql`, H2 콘솔) 단일 구성이다.

---

## 🗓️ 향후 작업

- [ ] 위 "알려진 한계" 정리
- [ ] 테스트 코드 작성 (Service 단위, Repository, Controller 검증·에러 응답) 및 GitHub Actions CI
- [ ] LessonNote 단건 상세 조회 구현
- [ ] `@ManyToOne(LAZY)` 전환 및 조회용 `@Transactional(readOnly = true)` 적용
- [ ] `createdAt`, `updatedAt` 추가
- [ ] User 도메인 및 JWT 인증·인가, 강사별 데이터 소유권 검증
- [ ] 프로필 분리(`local` / `prod`) 및 MySQL 전환
- [ ] 검색·필터 기능 및 필요 시 QueryDSL 검토
- [ ] 서비스 배포
- [ ] React + TypeScript 클라이언트 (선택)

---

## 📝 문서 기준

이 문서는 2026-09-30 기준 저장소 소스(`build.gradle`, `src/main`, `src/test`)를 직접 읽고 작성했다. 빌드·실행·테스트 결과는 별도로 검증하지 않았다.
