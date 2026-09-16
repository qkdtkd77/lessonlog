# 🎵 LessonLog

> 음악 레슨 강사가 학생 정보와 레슨 내용, 과제, 메모를 관리하기 위한 백엔드 프로젝트

음악 레슨을 진행하며 학생별 기록을 관리할 때 느낀 불편함에서 시작했다. Java와 Spring Boot로 기능을 구현하고, 학습한 내용을 적용하며 실제 사용할 수 있는 서비스로 발전시키는 것을 목표로 한다.

**진행 상태: 개발 중 · 문서 기준일: 2026-09-16**

현재 Student CRUD와 학생별 LessonNote 등록·목록 조회를 구현했다. Student와 LessonNote의 JPA 엔티티 매핑은 적용했지만, **API의 실제 저장·조회는 아직 인메모리
Repository를 사용한다.**

## 📋 구현 현황

| 구분                            | 상태   | 내용                                                               |
|---------------------------------|--------|--------------------------------------------------------------------|
| Student 관리                    | 구현   | 등록, 목록 조회, 상세 조회, 수정, 삭제                             |
| LessonNote 등록                 | 구현   | Student 존재 여부 확인 후 등록                                     |
| LessonNote 목록 조회            | 구현   | Student별 조회, 레슨 날짜 기준 최신순 정렬                         |
| 등록 입력 검증                  | 구현   | lessonDate 누락·공백·형식 오류 처리, Service의 studentId null 검증 |
| 인메모리 저장소                 | 적용   | ConcurrentHashMap + AtomicLong                                     |
| JPA 엔티티 매핑                 | 적용   | Student, LessonNote에 엔티티 및 기본키 매핑                        |
| H2 테이블 생성                  | 확인   | Hibernate를 통한 엔티티 테이블 생성                                |
| JPA 기반 저장·조회              | 미구현 | 기존 인메모리 Repository 전환 필요                                 |
| Student–LessonNote JPA 연관관계 | 미구현 | 현재 studentId 값으로 연결                                         |

## 🛠️ 기술 구성

| 영역              | 사용 기술 / 현재 용도                          |
|-------------------|------------------------------------------------|
| 언어              | Java                                           |
| 웹 / 애플리케이션 | Spring Boot, Spring MVC                        |
| 코드 작성 보조    | Lombok                                         |
| 현재 데이터 저장  | ConcurrentHashMap, AtomicLong                  |
| 영속성 전환 작업  | Jakarta Persistence(JPA), Hibernate, H2        |
| 개발 및 리뷰      | Git, GitHub Issues / Pull Requests, CodeRabbit |

JPA와 H2는 엔티티 매핑 및 테이블 생성 단계에 적용되어 있다. 데이터베이스를 통한 API 저장·조회 전환을 완료한 상태는 아니다.

## 🧩 도메인

| 도메인     | 주요 필드                                                | 역할                  |
|------------|----------------------------------------------------------|-----------------------|
| Student    | id, name, instrument, phone, memo                        | 학생 정보 관리        |
| LessonNote | id, studentId, lessonDate, lessonContent, homework, memo | 학생별 레슨 기록 관리 |

현재 LessonNote는 Student 객체 대신 `studentId` 값을 보관한다. `@ManyToOne` 등의 JPA 연관관계 매핑은 아직 적용하지 않았다.

## 🏗️ 현재 구조

```text
HTTP 요청
  → Controller: 요청 파싱 및 HTTP 응답 처리
  → Service: Student 존재 여부 확인 등 처리
  → Repository 인터페이스
  → 인메모리 구현체: ConcurrentHashMap + AtomicLong
```

- Controller → Service → Repository로 역할을 분리한다.
- Repository 인터페이스와 구현체를 분리한다.
- Student 상세 조회·수정 대상이 없으면 Service에서 예외를 발생시키고 Controller에서 404로 처리한다.
- LessonNote 등록 전에 Student 존재 여부를 확인한다.
- 목록 조회 결과가 없으면 빈 목록을 반환한다.
- Student 수정 및 LessonNote 등록 요청은 별도 DTO 없이 `Map<String, String>`으로 처리한다.

### 🗄️ JPA 전환 범위

완료한 작업:

- Student, LessonNote에 `@Entity` 적용
- 기본키에 `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)` 적용
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)` 적용
- 엔티티 필드의 기존 `final` 제거
- Hibernate를 통한 H2 테이블 생성 확인

남은 작업:

- Repository의 저장·조회 구현을 JPA 기반으로 전환
- Student와 LessonNote의 JPA 연관관계 매핑 적용

현재 API 데이터는 인메모리 저장소에 있으므로 애플리케이션을 재시작하면 유지되지 않는다. 엔티티에 설정한 IDENTITY 전략도 기존 인메모리 Repository의 `AtomicLong` 채번을 자동으로 대체하지
않는다.

## 🔌 API

| 대상       | 메서드 | 경로                              | 기능                       |
|------------|--------|-----------------------------------|----------------------------|
| Student    | POST   | /api/students                     | 학생 등록                  |
| Student    | GET    | /api/students                     | 학생 목록 조회             |
| Student    | GET    | /api/students/{studentId}         | 학생 상세 조회             |
| Student    | PUT    | /api/students/{studentId}         | 학생 수정                  |
| Student    | DELETE | /api/students/{studentId}         | 학생 삭제                  |
| LessonNote | POST   | /api/students/{studentId}/lessons | 레슨 기록 등록             |
| LessonNote | GET    | /api/students/{studentId}/lessons | 학생별 레슨 기록 목록 조회 |

### 📬 주요 응답 처리

| 상황                                                   | 응답                                                                  |
|--------------------------------------------------------|-----------------------------------------------------------------------|
| Student 상세 조회·수정 성공                            | 200 OK                                                                |
| Student 상세 조회·수정 대상 미존재                     | 404 Not Found                                                         |
| LessonNote 등록 성공                                   | 201 Created                                                           |
| LessonNote 등록 대상 Student 미존재                    | 404 Not Found                                                         |
| LessonNote 등록 시 lessonDate 누락·공백·날짜 형식 오류 | 400 Bad Request                                                       |
| LessonNote 등록 Service에 null studentId 전달          | IllegalArgumentException 발생, Controller에서 처리 시 400 Bad Request |

## 🔍 코드 리뷰와 개선

CodeRabbit PR 리뷰를 검토하여 다음 내용을 반영했다.

- 잘못된 `lessonDate` 입력이 처리되지 않은 예외로 인해 500 응답으로 이어질 가능성을 확인하고, 입력 검증 및 400 응답 처리를 추가했다.
- 여러 요청이 접근하는 인메모리 저장소의 `HashMap`을 `ConcurrentHashMap`으로 변경했다.
- `ConcurrentHashMap`이 null 키를 허용하지 않는 점을 고려하여 Service에서 `studentId`를 사전 검증했다.

`ConcurrentHashMap` 적용은 Map 개별 연산의 동시 접근을 보완한다. 여러 단계의 서비스 처리 전체에 대한 원자성이나 트랜잭션을 보장하는 것은 아니다.

## 🔄 개발 방식

```text
GitHub Issue
  → 기능 브랜치
  → 기능 단위 Commit
  → Pull Request
  → CodeRabbit 리뷰
  → 리뷰 검토 및 수정
  → dev 병합
```

커밋을 기반으로 개발일지 초안을 자동 작성하고, 날짜별로 구현 내용과 설계 판단, 트러블슈팅을 정리한다. 리뷰 기록은 발견한 문제와 검토 결과, 실제 반영 내용을 중심으로 남긴다.

## 🗓️ 향후 작업 / 검토 항목

아래 항목은 현재 구현 완료 범위에 포함하지 않는다.

- JPA Repository 전환 및 Student–LessonNote 연관관계 매핑
- 요청·응답 DTO 도입
- Bean Validation 적용 및 전역 예외 처리
- 사용자 도메인과 인증·인가
- MySQL 전환
- 검색·필터 기능 및 필요에 따른 QueryDSL 적용 검토
- React + TypeScript 클라이언트
- 서비스 배포

## 📝 문서 기준

이 문서는 2026-09-16까지 공유된 개발일지와 프로젝트 대화를 기준으로 작성했다. 저장소 소스 및 실행 환경을 직접 검증한 문서는 아니다. Java·Spring Boot 버전, 빌드 도구 설정, 실행 명령과
테스트 결과는 확인 후 추가한다.
