-- ========================================
-- LessonLog 부하 테스트 데이터
-- 기준: LessonLog_부하_테스트_계획 3장
-- 실행: root 계정으로 실행한다 (DB 생성, 권한 부여 포함)
-- 결과: 선생님 1,000명, 학생 약 10,000명, 일지 약 200,000건
-- 난수 대신 CRC32 해시를 써서 실행할 때마다 같은 데이터가 나온다
-- ========================================

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS lessonlog_perf;
CREATE DATABASE lessonlog_perf DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
GRANT ALL PRIVILEGES ON lessonlog_perf.* TO 'lessonlog'@'%';

USE lessonlog_perf;

SET SESSION cte_max_recursion_depth = 1000;

-- ========================================
-- 테이블 (Hibernate가 생성한 DDL 기준)
-- ========================================

CREATE TABLE users (
    created_at datetime(6) NOT NULL,
    deleted_at datetime(6),
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    role enum ('ADMIN','TEACHER') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UK6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE student (
    deleted_at datetime(6),
    id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    phone varchar(13) NOT NULL,
    name varchar(20) NOT NULL,
    instrument varchar(30) NOT NULL,
    memo varchar(500),
    PRIMARY KEY (id),
    CONSTRAINT FKk0thg920a3xk3v59yjbsatw1l FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE lesson_note (
    lesson_date date NOT NULL,
    id bigint NOT NULL AUTO_INCREMENT,
    student_id bigint NOT NULL,
    homework varchar(500) NOT NULL,
    memo varchar(500),
    lesson_content varchar(1000) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FKon9d5j0pm5erjehxjtirs6duy FOREIGN KEY (student_id) REFERENCES student (id)
) ENGINE=InnoDB;

-- ========================================
-- 작업용 테이블 (마지막에 삭제)
-- ========================================

CREATE TABLE tmp_seq (n int PRIMARY KEY);
INSERT INTO tmp_seq (n)
WITH RECURSIVE seq (n) AS (SELECT 1 UNION ALL SELECT n + 1 FROM seq WHERE n < 1000)
SELECT n FROM seq;

-- ========================================
-- Users
-- teacher0001 ~ teacher1000 (id 1 ~ 1000), 약 5%는 탈퇴
-- admin (id 1001)
-- 비밀번호는 모두 1234
-- ========================================

INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
SELECT n,
       CONCAT('teacher', LPAD(n, 4, '0'), '@perf.test'),
       '$2y$10$01PQ9Mb4X5XHDdhRFK4SEee.EETdJMeKuq70ffGOGIs6uWNF3eR/G',
       CONCAT('선생님', n),
       'TEACHER',
       '2023-01-02 09:00:00',
       IF(CRC32(CONCAT('teacher-deleted-', n)) % 100 < 5, '2026-10-01 09:00:00', NULL)
FROM tmp_seq;

INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
VALUES (1001, 'admin@perf.test', '$2y$10$01PQ9Mb4X5XHDdhRFK4SEee.EETdJMeKuq70ffGOGIs6uWNF3eR/G', '관리자', 'ADMIN', '2023-01-02 09:00:00', NULL);

-- ========================================
-- Student
-- 선생님당 학생 수: 60%는 3~7명, 30%는 8~15명, 10%는 20~50명
-- 약 10%는 삭제
-- ========================================

CREATE TABLE tmp_teacher_plan AS
SELECT n AS teacher_id,
       CASE
           WHEN CRC32(CONCAT('teacher-bucket-', n)) % 100 < 60 THEN 3 + CRC32(CONCAT('teacher-size-', n)) % 5
           WHEN CRC32(CONCAT('teacher-bucket-', n)) % 100 < 90 THEN 8 + CRC32(CONCAT('teacher-size-', n)) % 8
           ELSE 20 + CRC32(CONCAT('teacher-size-', n)) % 31
       END AS student_count
FROM tmp_seq;

INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
SELECT ROW_NUMBER() OVER (ORDER BY p.teacher_id, s.n),
       p.teacher_id,
       '',
       '',
       '',
       '',
       NULL
FROM tmp_teacher_plan p
JOIN tmp_seq s ON s.n <= p.student_count;

UPDATE student
SET name       = CONCAT(
        ELT(1 + CRC32(CONCAT('last-', id)) % 10, '김', '이', '박', '최', '정', '강', '조', '윤', '장', '임'),
        ELT(1 + CRC32(CONCAT('first-', id)) % 10, '민준', '서연', '도윤', '하은', '지호', '수아', '예준', '지우', '시우', '하린')),
    instrument = ELT(1 + CRC32(CONCAT('instrument-', id)) % 6, 'Bass', 'Piano', 'Guitar', 'Drum', 'Vocal', 'Violin'),
    phone      = CONCAT('010-', LPAD((id * 37) % 10000, 4, '0'), '-', LPAD(id % 10000, 4, '0')),
    memo       = ELT(1 + CRC32(CONCAT('student-memo-', id)) % 5, '', '취미반', '입시 준비', '주 2회 수업', '밴드 활동 중'),
    deleted_at = IF(CRC32(CONCAT('student-deleted-', id)) % 100 < 10, '2026-09-01 18:00:00', NULL);

-- ========================================
-- LessonNote
-- 학생당 일지 수: 55%는 2~10건, 30%는 11~30건, 12%는 31~80건, 3%는 81~200건
-- 주 1회 간격, 학생마다 마지막 수업일을 0~59일 앞당겨 분산
-- 약 2%는 직전 수업과 같은 날짜 (보강)
-- ========================================

CREATE TABLE tmp_student_plan AS
SELECT id AS student_id,
       CASE
           WHEN CRC32(CONCAT('student-bucket-', id)) % 100 < 55 THEN 2 + CRC32(CONCAT('student-size-', id)) % 9
           WHEN CRC32(CONCAT('student-bucket-', id)) % 100 < 85 THEN 11 + CRC32(CONCAT('student-size-', id)) % 20
           WHEN CRC32(CONCAT('student-bucket-', id)) % 100 < 97 THEN 31 + CRC32(CONCAT('student-size-', id)) % 50
           ELSE 81 + CRC32(CONCAT('student-size-', id)) % 120
       END AS note_count,
       CRC32(CONCAT('student-end-', id)) % 60 AS end_offset
FROM student;

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
SELECT ROW_NUMBER() OVER (ORDER BY p.student_id, s.n),
       p.student_id,
       CONCAT(
           ELT(1 + CRC32(CONCAT('content-', p.student_id, '-', s.n)) % 8,
               '스케일 연습', '리듬 트레이닝', '곡 카피 진행', '코드 진행 이해', '테크닉 교정', '합주 대비 곡 연습', '청음 훈련', '입시곡 점검'),
           '. ',
           REPEAT('구간별로 나눠 반복하고 템포를 단계적으로 올렸다. ', CRC32(CONCAT('content-len-', p.student_id, '-', s.n)) % 11)),
       CONCAT(
           ELT(1 + CRC32(CONCAT('homework-', p.student_id, '-', s.n)) % 6,
               '메트로놈 연습', '오늘 배운 구간 복습', '스케일 3회 반복', '곡 1절 외우기', '녹음해서 들어보기', '손 풀기 루틴'),
           REPEAT(' 매일 10분.', CRC32(CONCAT('homework-len-', p.student_id, '-', s.n)) % 8)),
       IF(CRC32(CONCAT('memo-empty-', p.student_id, '-', s.n)) % 100 < 30,
          '',
          CONCAT(
              ELT(1 + CRC32(CONCAT('memo-', p.student_id, '-', s.n)) % 5,
                  '집중도 좋음', '박자 흔들림', '손목 힘 빼기', '자세 교정 필요', '다음 시간 진도 확인'),
              REPEAT(' 다음 수업에서 다시 확인.', CRC32(CONCAT('memo-len-', p.student_id, '-', s.n)) % 7))),
       DATE_SUB('2026-11-18',
                INTERVAL p.end_offset
                    + 7 * (p.note_count
                    - IF(s.n > 1 AND CRC32(CONCAT('makeup-', p.student_id, '-', s.n)) % 100 < 2, s.n - 1, s.n)) DAY)
FROM tmp_student_plan p
JOIN tmp_seq s ON s.n <= p.note_count;

DROP TABLE tmp_student_plan;
DROP TABLE tmp_teacher_plan;
DROP TABLE tmp_seq;

-- ========================================
-- 건수와 분포 확인
-- ========================================

SELECT role, COUNT(*) AS total, SUM(deleted_at IS NOT NULL) AS deleted
FROM users
GROUP BY role;

SELECT COUNT(*) AS students, SUM(deleted_at IS NOT NULL) AS deleted
FROM student;

SELECT COUNT(*) AS lesson_notes, MIN(lesson_date) AS first_date, MAX(lesson_date) AS last_date
FROM lesson_note;

SELECT CASE
           WHEN cnt <= 7 THEN '1) 3~7명'
           WHEN cnt <= 15 THEN '2) 8~15명'
           ELSE '3) 20~50명'
       END AS students_per_teacher,
       COUNT(*) AS teachers
FROM (SELECT user_id, COUNT(*) AS cnt FROM student GROUP BY user_id) t
GROUP BY students_per_teacher
ORDER BY students_per_teacher;

SELECT CASE
           WHEN cnt <= 10 THEN '1) 2~10건'
           WHEN cnt <= 30 THEN '2) 11~30건'
           WHEN cnt <= 80 THEN '3) 31~80건'
           ELSE '4) 81~200건'
       END AS notes_per_student,
       COUNT(*) AS students
FROM (SELECT student_id, COUNT(*) AS cnt FROM lesson_note GROUP BY student_id) t
GROUP BY notes_per_student
ORDER BY notes_per_student;
