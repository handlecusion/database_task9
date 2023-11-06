### main java code
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentCourseInfo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "사용자명";
        String password = "비밀번호";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            if (connection != null) {
                System.out.println("데이터베이스에 연결되었습니다.");

                // 사용자로부터 ID 입력 받기
                Scanner scanner = new Scanner(System.in);
                System.out.print("학생의 ID를 입력하세요: ");
                int studentId = scanner.nextInt();

                // SQL SELECT 쿼리 준비 (takes와 course 테이블을 조인)
                String sql = "SELECT takes.course_id, takes.sec_id, takes.semester, takes.grade, takes.year, course.title " +
                             "FROM takes " +
                             "JOIN course ON takes.course_id = course.id " +
                             "WHERE takes.student_id = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, studentId);

                // 쿼리 실행
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int courseId = resultSet.getInt("course_id");
                    int secId = resultSet.getInt("sec_id");
                    String semester = resultSet.getString("semester");
                    String grade = resultSet.getString("grade");
                    int year = resultSet.getInt("year");
                    String courseTitle = resultSet.getString("title");

                    System.out.println("강의 ID: " + courseId);
                    System.out.println("섹션 ID: " + secId);
                    System.out.println("학기: " + semester);
                    System.out.println("성적: " + grade);
                    System.out.println("년도: " + year);
                    System.out.println("강의 제목: " + courseTitle);
                    System.out.println("------------");
                }

                if (!resultSet.next()) {
                    System.out.println("해당 학생이 수강한 강의가 없습니다.");
                }

                // 리소스 닫기
                resultSet.close();
                preparedStatement.close();
                scanner.close();
            } else {
                System.out.println("데이터베이스에 연결할 수 없습니다.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("데이터베이스에 연결 중 오류가 발생했습니다.");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

### and other sql in java
**use subquery**
```sql
SELECT course_id, sec_id, semester, grade, year, (SELECT title FROM course WHERE course.id = takes.course_id) as title
FROM takes
WHERE student_id = ?;
```

### create table code
**student**
```sql
CREATE TABLE student (
    ID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    tot_cred INT NOT NULL,
    PRIMARY KEY (ID)
);
```

**takes**
```sql
CREATE TABLE takes (
    ID INT NOT NULL,
    course_id INT NOT NULL,
    sec_id INT NOT NULL,
    semester VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    grade VARCHAR(2),
    PRIMARY KEY (ID, course_id, sec_id, semester, year)
);
```

**course**
```sql
CREATE TABLE course (
    course_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    dept_name VARCHAR(255) NOT NULL,
    credits INT NOT NULL,
    PRIMARY KEY (course_id)
);
```

### insert in table code
**student**
```sql
INSERT INTO student (ID, name, dept_name, tot_cred)
VALUES
(1, 'John Doe', 'Computer Science', 120),
(2, 'Jane Smith', 'Mathematics', 95),
(3, 'Bob Johnson', 'Physics', 110);
```

**takes**
```sql
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
```

**course**
```sql
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
```
