package classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import enums.CourseType;
import main.Message;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:1111/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2005";

    private static Database instance;
    private Connection connection;

    private Database() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Метод для регистрации пользователя
    public static boolean registerUser(String fullname, String email, String password, String userType) throws SQLException {
        String query = "INSERT INTO users (fullname, email, password, role) VALUES (?, ?, ?, ?)";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, fullname);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, userType);




        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }

    // Метод для проверки занятости email
    public static boolean isEmailTaken(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
    public static boolean loginUser(String email, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Если пользователь найден, возвращаем true
        }
        return false; // Если пользователь не найден
    }
    public static String getUserRole(String email) throws SQLException {
        String query = "SELECT role FROM users WHERE email = ?";
        Connection conn = getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role"); // Получение значения колонки "role"
            }
        }
        return null; // Если роль не найдена
    }
    public static List<Message> getMessages(String toWhom) throws SQLException {
        String query = "SELECT from_whom, content FROM messages WHERE to_whom = ?";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, toWhom);

        ResultSet rs = stmt.executeQuery();List<Message> messages = new ArrayList<>();
        while (rs.next()) {
            String fromWhom = rs.getString("from_whom");
            String content = rs.getString("content");
            messages.add(new Message(fromWhom, content));
        }
        return messages;
    }
    public static boolean sendMessage(String fromWhom, String toWhom, String content) throws SQLException {
        String query = "INSERT INTO messages (from_whom, to_whom, content) VALUES (?, ?, ?)";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, fromWhom);
        stmt.setString(2, toWhom);
        stmt.setString(3, content);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }
    public static boolean addCourse(String courseName, int credits, String courseType) throws SQLException {
        String query = "INSERT INTO courses (course_name, credits, course_type) VALUES (?, ?, ?)";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, courseName);
        stmt.setInt(2, credits);
        stmt.setString(3, courseType);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }
    public static boolean addInstructorToCourse(int teacherId, int courseId) throws SQLException {
        String query = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, teacherId);
        stmt.setInt(2, courseId);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }
    public static boolean addStudentToCourse(int studentId, int courseId) throws SQLException {
        String query = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, studentId);
        stmt.setInt(2, courseId);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }
    public static List<Course> getCoursesForTeacher(String teacherId) throws SQLException {
        String query = "SELECT c.course_id, c.course_name, c.credits, c.course_type " +
                "FROM courses c " +
                "JOIN teachers_courses tc ON c.course_id = tc.course_id " +
                "WHERE tc.teacher_id = ?";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, teacherId); // Using the String ID directly

        ResultSet rs = stmt.executeQuery();
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            Course course = new Course();
            course.setCourseId(rs.getString("course_id"));
            course.setCourseName(rs.getString("course_name"));
            course.setCredits(rs.getInt("credits"));
            course.setCourseType(CourseType.valueOf(rs.getString("course_type")));
            courses.add(course);
        }
        return courses;
    }

    public static List<Student> getStudentsInCourse(int courseId) throws SQLException {
        String query = "SELECT u.id, u.fullname, u.email FROM users u " +
                "JOIN students_courses sc ON u.id = sc.student_id " +
                "WHERE sc.course_id = ?";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, courseId);

        ResultSet rs = stmt.executeQuery();
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(String.valueOf(rs.getInt("id")));
            student.setFullname(rs.getString("fullname"));
            student.setEmail(rs.getString("email"));
            students.add(student);
        }
        return students;
    }
    public static Object getUserByEmail(String email) throws SQLException {
        // Запрос для извлечения пользователя по email
        String query = "SELECT * FROM users WHERE email = ?";
        Connection conn = getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();

        // Проверка, найден ли пользователь
        if (rs.next()) {
            // Получаем роль пользователя
            String role = rs.getString("role");

            // В зависимости от роли создаем объект нужного типа
            if ("teacher".equalsIgnoreCase(role)) {
                Teacher teacher = new Teacher();
                teacher.setId(String.valueOf(rs.getInt("id")));
                teacher.setFullname(rs.getString("fullname"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPassword(rs.getString("password"));

                return teacher; // Возвращаем объект преподавателя
            } else if ("student".equalsIgnoreCase(role)) {
                Student student = new Student();
                student.setId(String.valueOf(rs.getInt("id")));
                student.setFullname(rs.getString("fullname"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                return student; // Возвращаем объект студента
            }
        }
        return null; // Если пользователь не найден
    }







}