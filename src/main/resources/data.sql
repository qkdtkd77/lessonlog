-- ========================================
-- Student
-- ========================================

INSERT INTO student (id, name, instrument, phone, memo)
VALUES (1, '김민수', 'Bass', '010-1111-1111', '주 1회 수업');

INSERT INTO student (id, name, instrument, phone, memo)
VALUES (2, '이서연', 'Piano', '010-2222-2222', '입시 준비');

INSERT INTO student (id, name, instrument, phone, memo)
VALUES (3, '박준호', 'Guitar', '010-3333-3333', '취미반');

INSERT INTO student (id, name, instrument, phone, memo)
VALUES (4, '최유진', 'Drum', '010-4444-4444', '밴드 활동 중');


-- ========================================
-- LessonNote - Student 1
-- ========================================

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (1, 1, '기본 핑거링 연습', '크로매틱 연습 10분', '왼손 힘 빼기', '2026-07-01');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (2, 1, '메이저 스케일 연습', 'C Major 스케일', '', '2026-07-08');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (3, 1, '8비트 리듬 연습', '메트로놈 80 BPM', '박자 안정화 필요', '2026-07-15');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (4, 1, '뮤트 연습', '오른손 뮤트 반복', '', '2026-07-22');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (5, 1, '슬랩 기본 자세', '엄지 슬랩 연습', '손목 힘 빼기', '2026-07-29');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (6, 1, '슬랩 옥타브 패턴', '옥타브 패턴 3개', '', '2026-08-05');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (7, 1, '16비트 리듬', '16비트 메트로놈 연습', '템포 천천히 시작', '2026-08-12');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (8, 1, '곡 카피 진행', '1절 베이스 라인 카피', '', '2026-08-19');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (9, 1, '곡 카피 피드백', '후렴까지 연결', '리듬 정확도 향상', '2026-08-26');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (10, 1, '전체 곡 연주', '원곡에 맞춰 반복 연주', '', '2026-09-02');


-- ========================================
-- LessonNote - Student 2
-- ========================================

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (11, 2, '스케일 점검', '하농 1번', '', '2026-07-03');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (12, 2, '코드 진행 연습', 'I-IV-V 진행', '왼손 이동 주의', '2026-07-10');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (13, 2, '초견 연습', '악보 2페이지', '', '2026-07-17');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (14, 2, '페달링 연습', '페달 교체 구간 연습', '', '2026-07-24');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (15, 2, '곡 전반부 레슨', '전반부 암보', '템포 유지', '2026-07-31');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (16, 2, '곡 후반부 레슨', '후반부 암보', '', '2026-08-07');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (17, 2, '다이내믹 표현', '강약 표현 연습', '', '2026-08-14');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (18, 2, '전체 곡 연결', '처음부터 끝까지 연주', '중간 템포 흔들림', '2026-08-21');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (19, 2, '실전 연주 연습', '녹음 후 들어보기', '', '2026-08-28');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (20, 2, '최종 피드백', '약한 구간 반복', '완성도 높아짐', '2026-09-04');


-- ========================================
-- LessonNote - Student 3
-- ========================================

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (21, 3, '오픈 코드 복습', 'C G Am F 전환', '', '2026-07-02');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (22, 3, '스트로크 패턴', '8비트 스트로크', '', '2026-07-09');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (23, 3, '바레 코드 연습', 'F 코드 반복', '손가락 힘 조절', '2026-07-16');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (24, 3, '아르페지오 기본', '4/4 아르페지오', '', '2026-07-23');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (25, 3, '곡 코드 분석', '코드 진행 외우기', '', '2026-07-30');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (26, 3, '곡 전반부 연주', '1절까지 연주', '', '2026-08-06');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (27, 3, '리듬 교정', '메트로놈 70 BPM', '급해지는 부분 주의', '2026-08-13');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (28, 3, '곡 후반부 연주', '후렴 반복', '', '2026-08-20');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (29, 3, '전체 곡 연결', '원곡과 함께 연주', '', '2026-08-27');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (30, 3, '전체 피드백', '부족한 코드 전환 반복', '안정감 향상', '2026-09-03');


-- ========================================
-- LessonNote - Student 4
-- ========================================

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (31, 4, '기본 그립 점검', '싱글 스트로크', '', '2026-07-04');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (32, 4, '8비트 기본 비트', '8비트 80 BPM', '', '2026-07-11');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (33, 4, '필인 연습', '1마디 필인 3개', '', '2026-07-18');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (34, 4, '하이햇 컨트롤', '하이햇 오픈/클로즈', '', '2026-07-25');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (35, 4, '16비트 기본', '16비트 60 BPM', '손목 힘 빼기', '2026-08-01');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (36, 4, '킥 패턴 연습', '킥 변형 패턴', '', '2026-08-08');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (37, 4, '곡 구조 분석', '곡 섹션 구분', '', '2026-08-15');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (38, 4, '곡 전반부 합주', '전반부 반복', '', '2026-08-22');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (39, 4, '곡 후반부 합주', '후반부 반복', '필인 타이밍 주의', '2026-08-29');

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (40, 4, '전체 곡 연주', '원곡과 전체 연주', '', '2026-09-05');


-- H2 IDENTITY 다음 생성값 조정
ALTER TABLE student
    ALTER COLUMN id RESTART WITH 5;
ALTER TABLE lesson_note
    ALTER COLUMN id RESTART WITH 41;