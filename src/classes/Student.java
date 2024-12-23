package classes;
import enums.Faculty;
import enums.UrgencyLevel;
import interfaces.CanViewCourses;
import interfaces.CanViewMarks;
import interfaces.CanViewTeachers;
import interfaces.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Student extends User implements CanViewCourses, CanViewTeachers, Observer {
    private String studentId;
    private String department;
    private List<Course> enrolledCourses;
    private Map<Teacher, UrgencyLevel> complaints;

    {
        this.enrolledCourses = new ArrayList<>();
        this.complaints = new HashMap<>();
    }

    public Student() {
    }


    public Student(String id, String fullName, String email, String password) {
        super(id, fullName, email, password);
    }

    public Student(String id, String fullName, String email, String password, List<Course> enrolledCourses) {
        super(id, fullName, email, password);
    }


    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void enrollCourse(Course course) throws Exception {
        if (enrolledCourses.size() >= 7) {
            throw new Exception("Student cannot register for more than 21 credits.");
        }
        enrolledCourses.add(course);
    }

    public Transcript getTranscript() {
        return null;
    }

    public Map<Teacher, UrgencyLevel> getComplaints() {
        return complaints;
    }

    public void addComplaints(Teacher teacher, UrgencyLevel urgencyLevel) {
        this.complaints.put(teacher, urgencyLevel);
    }

    public double getGPA() {
        return 0;
    }
    public String getDepartment() {
        return "";
    }

    @Override
	public void viewTeachers(List<Teacher> teachers) {
    	for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
	}

	@Override
	public void viewCourses(List<Course> courses) {
		for (Course course : courses) {
            System.out.println(course);
        }
	}

    @Override
    public void update(String message) {
        System.out.println("Student " + getFullname() + " received notification: " + message);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", department='" + department + '\'' +
                //", enrolledCourses=" + enrolledCourses +
                ", complaints=" + complaints +
                "} " + super.toString();
    }
}