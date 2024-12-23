package classes;
import java.util.Map;

public class Transcript {
    private Student student;
    Map<Course, Mark> courseMarks;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Map<Course, Mark> getCourseMarks() {
        return courseMarks;
    }

    public void setCourseMarks(Map<Course, Mark> courseMarks) {
        this.courseMarks = courseMarks;
    }

    public void addCourseMarks(Course course, Mark courseMark) {
        this.courseMarks.put(course, courseMark);
    }
}