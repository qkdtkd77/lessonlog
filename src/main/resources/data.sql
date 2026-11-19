-- ========================================
-- Users
-- 비밀번호는 모두 1234
-- ========================================
INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
VALUES (1, 'teacher1@test.com', '$2y$10$01PQ9Mb4X5XHDdhRFK4SEee.EETdJMeKuq70ffGOGIs6uWNF3eR/G', '김선생', 'TEACHER', '2026-10-14 09:00:00', NULL);
INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
VALUES (2, 'teacher2@test.com', '$2y$10$uwshMoYXCxCFpFLVlEl22O08VnEM1kKhBXSibkfloTLXrL.oKw0PG', '이선생', 'TEACHER', '2026-10-14 09:00:00', NULL);
INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
VALUES (3, 'admin@test.com', '$2y$10$01PQ9Mb4X5XHDdhRFK4SEee.EETdJMeKuq70ffGOGIs6uWNF3eR/G', '관리자', 'ADMIN', '2026-10-14 09:00:00', NULL);
INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
VALUES (4, 'admin2@test.com', '$2y$10$01PQ9Mb4X5XHDdhRFK4SEee.EETdJMeKuq70ffGOGIs6uWNF3eR/G', '관리자2', 'ADMIN', '2026-10-14 09:00:00', NULL);
INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
VALUES (5, 'teacher3@test.com', '$2y$10$01PQ9Mb4X5XHDdhRFK4SEee.EETdJMeKuq70ffGOGIs6uWNF3eR/G', '박선생', 'TEACHER', '2026-10-14 09:00:00', NULL);
INSERT INTO users (id, email, password, name, role, created_at, deleted_at)
VALUES (6, 'teacher4@test.com', '$2y$10$01PQ9Mb4X5XHDdhRFK4SEee.EETdJMeKuq70ffGOGIs6uWNF3eR/G', '최선생', 'TEACHER', '2026-10-14 09:00:00', '2026-11-01 10:00:00');

-- ========================================
-- Student
-- ========================================

-- 김선생 (teacher1)

INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (1, 1, '김민수', 'Bass', '010-1111-1111', '주 1회 수업', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (2, 1, '이서연', 'Piano', '010-2222-2222', '입시 준비', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (5, 1, '정하은', 'Vocal', '010-5005-0005', '입시 준비', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (6, 1, '윤도현', 'Guitar', '010-5006-0006', '취미반', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (7, 1, '강서준', 'Drum', '010-5007-0007', '밴드 활동 중', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (8, 1, '임지아', 'Piano', '010-5008-0008', '초등학생, 학부모 연락', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (9, 1, '한예린', 'Violin', '010-5009-0009', '주 2회 수업', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (10, 1, '오태양', 'Bass', '010-5010-0010', '재즈 관심', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (11, 1, '서지우', 'Vocal', '010-5011-0011', '', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (12, 1, '신동하', 'Guitar', '010-5012-0012', '직장인 저녁반', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (13, 1, '배수아', 'Piano', '010-5013-0013', '콩쿠르 준비', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (14, 1, '조민재', 'Drum', '010-5014-0014', '수강 종료', '2026-10-31 18:00:00');

-- 이선생 (teacher2)

INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (3, 2, '박준호', 'Guitar', '010-3333-3333', '취미반', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (4, 2, '최유진', 'Drum', '010-4444-4444', '밴드 활동 중', NULL);

-- 박선생 (teacher3)

INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (15, 5, '김나연', 'Piano', '010-6015-0015', '성인 취미반', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (16, 5, '이준서', 'Bass', '010-6016-0016', '밴드 준비', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (17, 5, '박소율', 'Violin', '010-6017-0017', '', NULL);

-- 최선생 (teacher4) - 탈퇴한 선생님

INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (18, 6, '최하람', 'Guitar', '010-7018-0018', '', NULL);
INSERT INTO student (id, user_id, name, instrument, phone, memo, deleted_at)
VALUES (19, 6, '정우진', 'Drum', '010-7019-0019', '', NULL);

-- ========================================
-- LessonNote
-- 학생별로 수업 날짜 순서
-- ========================================

-- Student 1 김민수

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
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (11, 1, '워킹 베이스 기초', '4분음표 워킹 연습', '루트 위주로', '2026-09-09');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (12, 1, '셔플 리듬', '셔플 그루브 반복', '스윙감 체크', '2026-09-16');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (13, 1, '펑크 그루브', '16비트 고스트 노트', '', '2026-09-23');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (14, 1, '코드 톤 연습', 'ii-V-I 코드 톤', '3도 7도 강조', '2026-09-30');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (15, 1, '합주 대비 곡 연습', '곡 전체 반복', '드럼과 박자 맞추기', '2026-10-07');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (16, 1, '워킹 베이스 기초', '4분음표 워킹 연습', '루트 위주로', '2026-10-14');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (17, 1, '셔플 리듬', '셔플 그루브 반복', '스윙감 체크', '2026-10-21');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (18, 1, '펑크 그루브', '16비트 고스트 노트', '', '2026-10-28');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (19, 1, '코드 톤 연습', 'ii-V-I 코드 톤', '3도 7도 강조', '2026-11-04');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (20, 1, '합주 대비 곡 연습', '곡 전체 반복', '드럼과 박자 맞추기', '2026-11-11');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (21, 1, '보강 수업', '지난주 곡 복습', '같은 날 두 번째 수업', '2026-11-11');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (22, 1, '워킹 베이스 기초', '4분음표 워킹 연습', '루트 위주로', '2026-11-18');

-- Student 2 이서연

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (23, 2, '스케일 점검', '하농 1번', '', '2026-07-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (24, 2, '코드 진행 연습', 'I-IV-V 진행', '왼손 이동 주의', '2026-07-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (25, 2, '초견 연습', '악보 2페이지', '', '2026-07-17');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (26, 2, '페달링 연습', '페달 교체 구간 연습', '', '2026-07-24');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (27, 2, '곡 전반부 레슨', '전반부 암보', '템포 유지', '2026-07-31');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (28, 2, '곡 후반부 레슨', '후반부 암보', '', '2026-08-07');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (29, 2, '다이내믹 표현', '강약 표현 연습', '', '2026-08-14');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (30, 2, '전체 곡 연결', '처음부터 끝까지 연주', '중간 템포 흔들림', '2026-08-21');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (31, 2, '실전 연주 연습', '녹음 후 들어보기', '', '2026-08-28');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (32, 2, '최종 피드백', '약한 구간 반복', '완성도 높아짐', '2026-09-04');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (33, 2, '하농 1번', '하농 1~5번', '손가락 독립', '2026-11-04');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (34, 2, '체르니 30번', '30번 1곡 연습', '', '2026-11-11');

-- Student 3 박준호

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (35, 3, '오픈 코드 복습', 'C G Am F 전환', '', '2026-07-02');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (36, 3, '스트로크 패턴', '8비트 스트로크', '', '2026-07-09');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (37, 3, '바레 코드 연습', 'F 코드 반복', '손가락 힘 조절', '2026-07-16');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (38, 3, '아르페지오 기본', '4/4 아르페지오', '', '2026-07-23');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (39, 3, '곡 코드 분석', '코드 진행 외우기', '', '2026-07-30');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (40, 3, '곡 전반부 연주', '1절까지 연주', '', '2026-08-06');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (41, 3, '리듬 교정', '메트로놈 70 BPM', '급해지는 부분 주의', '2026-08-13');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (42, 3, '곡 후반부 연주', '후렴 반복', '', '2026-08-20');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (43, 3, '전체 곡 연결', '원곡과 함께 연주', '', '2026-08-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (44, 3, '전체 피드백', '부족한 코드 전환 반복', '안정감 향상', '2026-09-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (45, 3, '개방 코드 전환', 'G C D 전환 연습', '손목 각도', '2026-11-04');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (46, 3, '바레 코드', 'F 코드 소리 내기', '검지 위치', '2026-11-11');

-- Student 4 최유진

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (47, 4, '기본 그립 점검', '싱글 스트로크', '', '2026-07-04');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (48, 4, '8비트 기본 비트', '8비트 80 BPM', '', '2026-07-11');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (49, 4, '필인 연습', '1마디 필인 3개', '', '2026-07-18');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (50, 4, '하이햇 컨트롤', '하이햇 오픈/클로즈', '', '2026-07-25');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (51, 4, '16비트 기본', '16비트 60 BPM', '손목 힘 빼기', '2026-08-01');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (52, 4, '킥 패턴 연습', '킥 변형 패턴', '', '2026-08-08');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (53, 4, '곡 구조 분석', '곡 섹션 구분', '', '2026-08-15');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (54, 4, '곡 전반부 합주', '전반부 반복', '', '2026-08-22');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (55, 4, '곡 후반부 합주', '후반부 반복', '필인 타이밍 주의', '2026-08-29');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (56, 4, '전체 곡 연주', '원곡과 전체 연주', '', '2026-09-05');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (57, 4, '8비트 기본', '8비트 메트로놈 90', '하이햇 균일하게', '2026-11-04');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (58, 4, '필인 연습', '4마디 필인', '', '2026-11-11');

-- Student 5 정하은

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (59, 5, '호흡 훈련', '복식 호흡 5분', '어깨 힘 빼기', '2026-10-13');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (60, 5, '발성 연습', '허밍 스케일', '', '2026-10-20');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (61, 5, '고음 연습', '립트릴 상행', '목에 힘 금지', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (62, 5, '곡 해석', '가사 낭독', '감정선 체크', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (63, 5, '녹음 피드백', '녹음본 모니터링', '', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (64, 5, '호흡 훈련', '복식 호흡 5분', '어깨 힘 빼기', '2026-11-17');

-- Student 6 윤도현

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (65, 6, '개방 코드 전환', 'G C D 전환 연습', '손목 각도', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (66, 6, '바레 코드', 'F 코드 소리 내기', '검지 위치', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (67, 6, '스트로크 패턴', '8비트 스트로크', '', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (68, 6, '펜타토닉 스케일', 'A 마이너 펜타', '운지 순서', '2026-11-17');

-- Student 7 강서준

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (69, 7, '8비트 기본', '8비트 메트로놈 90', '하이햇 균일하게', '2026-10-20');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (70, 7, '필인 연습', '4마디 필인', '', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (71, 7, '16비트 하이햇', '16비트 80 BPM', '오른손 힘 빼기', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (72, 7, '셔플 그루브', '셔플 반복', '킥 위치', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (73, 7, '곡 카피', '곡 1절 카피', '', '2026-11-17');

-- Student 8 임지아

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (74, 8, '하농 1번', '하농 1~5번', '손가락 독립', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (75, 8, '체르니 30번', '30번 1곡 연습', '', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (76, 8, '코드 반주 패턴', '왼손 반주 연습', '페달 타이밍', '2026-11-17');

-- Student 9 한예린

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (77, 9, '활 긋기 기초', '개방현 롱톤', '활 속도 일정하게', '2026-10-05');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (78, 9, '1포지션 스케일', 'G 메이저 스케일', '', '2026-10-09');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (79, 9, '스즈키 1권', '작은 별 변주', '음정 확인', '2026-10-13');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (80, 9, '비브라토 준비', '손목 흔들기 연습', '', '2026-10-17');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (81, 9, '2포지션 이동', '포지션 이동 연습', '', '2026-10-21');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (82, 9, '활 긋기 기초', '개방현 롱톤', '활 속도 일정하게', '2026-10-25');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (83, 9, '1포지션 스케일', 'G 메이저 스케일', '', '2026-10-29');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (84, 9, '스즈키 1권', '작은 별 변주', '음정 확인', '2026-11-02');

-- Student 10 오태양

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (85, 10, '워킹 베이스 기초', '4분음표 워킹 연습', '루트 위주로', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (86, 10, '셔플 리듬', '셔플 그루브 반복', '스윙감 체크', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (87, 10, '펑크 그루브', '16비트 고스트 노트', '', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (88, 10, '코드 톤 연습', 'ii-V-I 코드 톤', '3도 7도 강조', '2026-11-17');

-- Student 11 서지우

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (89, 11, '호흡 훈련', '복식 호흡 5분', '어깨 힘 빼기', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (90, 11, '발성 연습', '허밍 스케일', '', '2026-11-17');

-- Student 12 신동하

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (91, 12, '개방 코드 전환', 'G C D 전환 연습', '손목 각도', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (92, 12, '바레 코드', 'F 코드 소리 내기', '검지 위치', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (93, 12, '스트로크 패턴', '8비트 스트로크', '', '2026-11-17');

-- Student 13 배수아

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (94, 13, '하농 1번', '하농 1~5번', '손가락 독립', '2026-10-20');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (95, 13, '체르니 30번', '30번 1곡 연습', '', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (96, 13, '코드 반주 패턴', '왼손 반주 연습', '페달 타이밍', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (97, 13, '입시곡 1악장', '1악장 암보', '셈여림 표현', '2026-11-10');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (98, 13, '스케일과 아르페지오', '24조 스케일', '', '2026-11-17');

-- Student 14 조민재 - 삭제된 학생

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (99, 14, '8비트 기본', '8비트 메트로놈 90', '하이햇 균일하게', '2026-09-05');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (100, 14, '필인 연습', '4마디 필인', '', '2026-09-12');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (101, 14, '16비트 하이햇', '16비트 80 BPM', '오른손 힘 빼기', '2026-09-19');

-- Student 15 김나연

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (102, 15, '하농 1번', '하농 1~5번', '손가락 독립', '2026-10-20');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (103, 15, '체르니 30번', '30번 1곡 연습', '', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (104, 15, '코드 반주 패턴', '왼손 반주 연습', '페달 타이밍', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (105, 15, '입시곡 1악장', '1악장 암보', '셈여림 표현', '2026-11-10');

-- Student 16 이준서

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (106, 16, '워킹 베이스 기초', '4분음표 워킹 연습', '루트 위주로', '2026-10-20');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (107, 16, '셔플 리듬', '셔플 그루브 반복', '스윙감 체크', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (108, 16, '펑크 그루브', '16비트 고스트 노트', '', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (109, 16, '코드 톤 연습', 'ii-V-I 코드 톤', '3도 7도 강조', '2026-11-10');

-- Student 17 박소율

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (110, 17, '활 긋기 기초', '개방현 롱톤', '활 속도 일정하게', '2026-10-20');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (111, 17, '1포지션 스케일', 'G 메이저 스케일', '', '2026-10-27');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (112, 17, '스즈키 1권', '작은 별 변주', '음정 확인', '2026-11-03');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (113, 17, '비브라토 준비', '손목 흔들기 연습', '', '2026-11-10');

-- Student 18 최하람

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (114, 18, '개방 코드 전환', 'G C D 전환 연습', '손목 각도', '2026-09-15');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (115, 18, '바레 코드', 'F 코드 소리 내기', '검지 위치', '2026-09-22');

-- Student 19 정우진

INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (116, 19, '8비트 기본', '8비트 메트로놈 90', '하이햇 균일하게', '2026-09-15');
INSERT INTO lesson_note (id, student_id, lesson_content, homework, memo, lesson_date)
VALUES (117, 19, '필인 연습', '4마디 필인', '', '2026-09-22');
