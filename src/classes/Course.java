package classes;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

import enums.CourseType;
import interfaces.Observer;

public class Course {
    private static final Set<Course> courseRegistry = new HashSet<>();

    private String courseId;
    private String courseName;
    private int credits;
    private List<Teacher> instructors;
    private List<Student> students;
    private HashMap<Student, Mark> marks = new HashMap<>();
    private CourseType courseType;
    private List<Observer> observers = new ArrayList<>();

    public Course() {
        this.instructors = new ArrayList<>();
        this.students = new ArrayList<>();
        this.marks = new HashMap<>();
    }

    public Course(String courseId, String courseName, int credits, CourseType courseType) {
        this();
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.courseType = courseType;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseName() {
    	return courseName;
    }
    
    public void setCourseName(String CourseName) {
    	this.courseName = courseName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    
    public List<Teacher> getInstructors() {
		return instructors;
	}

    public void addInstructor(Teacher instructor) {
        if (!instructors.contains(instructor)) {
            instructors.add(instructor);
        }
    }

    public boolean isFreeElectiveForSITE() {
        return this.courseType == CourseType.FREE_ELECTIVE;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", courseType=" + courseType +
                ", instructors=" + instructors +
                ", students=" + students +
                '}';
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}