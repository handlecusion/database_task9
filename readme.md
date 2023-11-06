### and other sql in java
**use subquery**
'''
SELECT course_id, sec_id, semester, grade, year, (SELECT title FROM course WHERE course.id = takes.course_id) as title
FROM takes
WHERE student_id = ?;
'''

### create table code
**student**
'''
CREATE TABLE student (
    ID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    tot_cred INT NOT NULL,
    PRIMARY KEY (ID)
);
'''

**takes**
'''
CREATE TABLE takes (
    ID INT NOT NULL,
    course_id INT NOT NULL,
    sec_id INT NOT NULL,
    semester VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    grade VARCHAR(2),
    PRIMARY KEY (ID, course_id, sec_id, semester, year)
);
'''

**course**
'''
CREATE TABLE course (
    course_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    credits INT NOT NULL,
    PRIMARY KEY (course_id)
);
'''

### insert in table code
**student**
'''
INSERT INTO student (ID, name, dept_name, tot_cred)
VALUES
(1, 'John Doe', 'Computer Science', 120),
(2, 'Jane Smith', 'Mathematics', 95),
(3, 'Bob Johnson', 'Physics', 110);
'''

**takes**
'''
-- 학생 1이 3개의 강의 수강
INSERT INTO takes (ID, course_id, sec_id, semester, year, grade)
VALUES
(1, 101, 1, 'Fall', 2023, 'A'),
(1, 102, 1, 'Spring', 2023, 'B'),
(1, 103, 1, 'Spring', 2023, 'C');

-- 학생 2가 3개의 강의 수강
INSERT INTO takes (ID, course_id, sec_id, semester, year, grade)
VALUES
(2, 101, 1, 'Fall', 2023, 'B'),
(2, 104, 1, 'Spring', 2023, 'A'),
(2, 105, 1, 'Spring', 2023, 'A');

-- 학생 3이 3개의 강의 수강
INSERT INTO takes (ID, course_id, sec_id, semester, year, grade)
VALUES
(3, 102, 1, 'Fall', 2023, 'A'),
(3, 105, 1, 'Spring', 2023, 'B'),
(3, 107, 1, 'Spring', 2023, 'C');
'''

**course**
'''
-- 강의 정보를 course 테이블에 추가
INSERT INTO course (course_id, title, dept_name, credits)
VALUES
(101, 'Introduction to Computer Science', 'Computer Science', 3),
(102, 'Calculus I', 'Mathematics', 4),
(103, 'Physics for Scientists and Engineers', 'Physics', 4),
(104, 'Data Structures and Algorithms', 'Computer Science', 3),
(105, 'Introduction to Statistics', 'Mathematics', 3),
(106, 'Classical Mechanics', 'Physics', 3),
(107, 'Web Development', 'Computer Science', 3),
(108, 'Linear Algebra', 'Mathematics', 4),
(109, 'Modern Physics', 'Physics', 3);
'''
