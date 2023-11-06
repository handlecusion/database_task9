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

