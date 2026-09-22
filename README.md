# 🎵 LessonLog

음악 레슨 강사가 학생 정보와 레슨 내용, 과제, 메모를 관리하기 위한 백엔드 프로젝트

음악 레슨을 진행하며 학생별 기록을 관리할 때 느낀 불편함에서 시작했다. Java와 Spring Boot로 기능을 구현하고, 학습한 내용을 적용하며 실제 사용할 수 있는 서비스로 발전시키는 것을 목표로 한다.

**진행 상태:** 개발 중 · **문서 기준일:** 2026-09-22

Student CRUD와 학생별 LessonNote 등록·목록 조회·수정을 구현했다. 저장·조회는 Spring Data JPA + H2 기반이며, 요청/응답은 DTO로 분리되어 API에 JPA 엔티티가 직접 노출되지 않는다. 목록 조회는 페이징·정렬을 지원한다.

---

## 📋 구현 현황

| 구분 | 상태 | 내용 |
|---|---|---|
| Student 관리 | 구현 | 등록, 목록 조회, 상세 조회, 수정, 삭제 |
| LessonNote 등록 | 구현 | Student 존재 여부 확인 후 등록 |
| LessonNote 목록 조회 | 구현 | Student별 조회, `lessonDate` 기준 최신순, 페이징·정렬 지원 |
| LessonNote 수정 | 구현 | 조회 후 도메인 메서드로 상태 변경 |
| LessonNote 삭제 | 미구현 | - |
| LessonNote 단건 상세 조회 | 미구현 | - |
| JPA 기반 저장·조회 | 구현 | 인메모리 Repository 제거, `JpaRepository` 전환 완료 |
| 요청 DTO / Validation | 구현 | Request DTO + Bean Validation |
| 응답 DTO | 구현 | Request / DTO / Response 역할 분리, Entity 직접 노출 제거 |
| Entity → DTO 매핑 | 구현 | MapStruct Mapper |
| 목록 페이징 / 정렬 | 구현 | `Pageable`, `PageResponse<T>`, `PaginationValidator` |
| 전역 예외 처리 | 구현 | 도메인별 커스텀 예외 + `ErrorResponse` |
| Student–LessonNote JPA 연관관계 | 미구현 | 현재 `studentId` 값으로 연결 |
| 감사 컬럼(createdAt/updatedAt) | 미구현 | - |
| User / 인증·인가 | 미구현 | Student·LessonNote 핵심 기능 이후 진행 |
| 테스트 코드 | 미구현 | 현재 수동 요청 확인에 의존 |

---

## 🛠️ 기술 구성

| 영역 | 사용 기술 |
|---|---|
| 언어 | Java |
| 웹 / 애플리케이션 | Spring Boot, Spring MVC |
| 영속성 | Spring Data JPA, Hibernate, H2 |
| 요청 검증 | Jakarta Bean Validation |
| 객체 매핑 | MapStruct |
| 코드 작성 보조 | Lombok |
| 개발 및 리뷰 | Git, GitHub Issues / Pull Requests, CodeRabbit |

> H2는 현재 개발용으로 사용 중이며 MySQL 전환은 미완료다.

---

## 🧩 도메인

| 도메인 | 주요 필드 | 역할 |
|---|---|---|
| Student | id, name, instrument, phone, memo | 학생 정보 관리 |
| LessonNote | id, studentId, lessonDate, lessonContent, homework, memo | 학생별 레슨 기록 관리 |

LessonNote는 Student 객체 대신 `studentId` 값을 보관한다. `@ManyToOne` 등 JPA 연관관계 매핑은 아직 적용하지 않았다.

엔티티 상태 변경은 Setter 대신 `updateStudent()`, `updateLessonNote()` 도메인 메서드로만 수행한다. `@Transactional`과 변경 감지(Dirty Checking)는 아직 적용하지 않아 상태 변경 후 `save()`를 명시적으로 호출한다.

---

## 🏗️ 현재 구조

```
HTTP 요청
  → Controller     : Request DTO 수신, @Valid 검증, Response 구성
  → Service        : 존재 여부 확인, 페이징 요청값 검증, DTO 변환 위임
  → Repository     : JpaRepository (Spring Data JPA)
  → H2 Database
```

- Controller → Service → Repository로 역할을 분리한다.
- 계층별로 다루는 타입을 고정한다. Repository는 Entity, Service는 DTO, Controller는 Response.
- Entity → DTO 변환은 MapStruct Mapper가 담당한다.
- 단건 응답은 `StudentResponse` / `LessonNoteResponse`, 목록 응답은 `PageResponse<T>`를 사용한다.
- 예외는 도메인별 커스텀 예외로 발생시키고 `GlobalExceptionHandler`에서 HTTP 응답으로 변환한다.

---

## 🔌 API

| 대상 | 메서드 | 경로 | 기능 |
|---|---|---|---|
| Student | POST | `/api/students` | 학생 등록 |
| Student | GET | `/api/students` | 학생 목록 조회 (페이징·정렬) |
| Student | GET | `/api/students/{studentId}` | 학생 상세 조회 |
| Student | PUT | `/api/students/{studentId}` | 학생 수정 |
| Student | DELETE | `/api/students/{studentId}` | 학생 삭제 |
| LessonNote | POST | `/api/students/{studentId}/lessons` | 레슨 기록 등록 |
| LessonNote | GET | `/api/students/{studentId}/lessons` | 학생별 목록 조회 (페이징·정렬) |
| LessonNote | PUT | `/api/lessons/{lessonId}` | 레슨 기록 수정 ※ 실제 매핑 경로 확인 필요 |

### 목록 조회 파라미터

| 파라미터 | 설명 | 비고 |
|---|---|---|
| `pageNumber` | 페이지 번호 | 1부터 시작, 내부에서 0-based로 보정 |
| `pageSize` | 페이지 크기 | 1 이상 |
| `sortBy` | 정렬 필드 | API별 허용 필드만 사용 (화이트리스트) |
| `sortOrder` | 정렬 방향 | `asc`, `desc`만 허용 |

기본 정렬 — Student: `id ASC` / LessonNote: `lessonDate DESC`

---

## 📬 주요 응답 처리

| 상황 | 응답 |
|---|---|
| 조회·수정 성공 | 200 OK |
| 등록 성공 | 201 Created |
| 삭제 성공 | 204 No Content |
| Validation 실패 | 400 Bad Request |
| 잘못된 페이징·정렬 요청값 | 400 Bad Request (`InvalidPaginationException`) |
| Student / LessonNote 미존재 | 404 Not Found |

에러 응답은 `ErrorResponse` record로 통일하며, 오류 개수와 무관하게 항상 배열로 반환한다.

```json
{ "errors": ["학생을 찾을 수 없습니다."] }
```

시스템 내부 예외 메시지는 클라이언트에 노출하지 않고 고정 메시지로 변환한다. 직접 정의한 Validation 메시지는 그대로 응답에 포함한다.

---

## 🔍 코드 리뷰와 개선

CodeRabbit PR 리뷰를 검토하여 반영한 주요 내용:

- 잘못된 `lessonDate` 입력이 500 응답으로 이어질 가능성을 확인하고 입력 검증 및 400 처리 추가
- 인메모리 저장소의 `HashMap`을 `ConcurrentHashMap`으로 변경 (이후 JPA 전환으로 대체)
- `ConcurrentHashMap`이 null 키를 허용하지 않는 점을 고려해 Service에서 `studentId` 사전 검증
- `GlobalExceptionHandler`가 내부 예외 메시지를 그대로 반환하던 문제를 확인하고 응답 메시지 정책 분리

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

## 🗓️ 향후 작업

- [ ] 테스트 코드 작성 (현재 자동화된 검증 없음)
- [ ] LessonNote 삭제 / 단건 상세 조회 구현
- [ ] Student–LessonNote `@ManyToOne(LAZY)` 연관관계 및 삭제 정책 적용
- [ ] `@Transactional` + 변경 감지 적용
- [ ] `createdAt`, `updatedAt` 추가
- [ ] User 도메인 및 JWT 인증·인가
- [ ] MySQL 전환
- [ ] 검색·필터 기능 및 필요 시 QueryDSL 검토
- [ ] React + TypeScript 클라이언트
- [ ] 서비스 배포

---

## 📝 문서 기준

이 문서는 2026-09-22까지의 개발일지를 기준으로 작성했다. 저장소 소스 및 실행 환경을 직접 검증한 문서는 아니다. Java·Spring Boot 버전, 빌드 도구 설정, 실행 명령과 테스트 결과는 확인 후 추가한다.
