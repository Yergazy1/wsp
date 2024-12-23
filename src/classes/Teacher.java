package classes;
import enums.Faculty;
import enums.TeacherType;
import enums.UrgencyLevel;
import interfaces.CanViewCourses;
import interfaces.CanViewMarks;
import interfaces.CanViewStudents;
import interfaces.Observer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Teacher extends Employee implements CanViewStudents, CanViewMarks, CanViewCourses, Observer {
	private Faculty faculty;
	private TeacherType teacherType;
	private double rating;
	private List<Course> courses;
	private List<ResearchProject> researchProjects;
	private List<ResearchPaper> researchPapers;

	public Teacher() {
		super();
	}

	public Teacher(String id, String fullName, String email, String password, Faculty faculty, TeacherType teacherType) {
		super(id, fullName, email, password);
		this.faculty = faculty;
		this.teacherType = teacherType;
		this.rating = rating;
	}

	public Teacher(String id, String fullName, String email, String password, Faculty faculty, TeacherType teacherType, double rating) {
		super(id, fullName, email, password);
		this.faculty = faculty;
		this.teacherType = teacherType;
		this.rating = rating;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public TeacherType getTeacherType() {
		return teacherType;
	}

	public void setTeacherType(TeacherType teacherType) {
		this.teacherType = teacherType;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public void viewStudents(List<Student> students) {
		for (Student student : students) {
			System.out.println(student);
		}
	}


	@Override
	public void viewCourses(List<Course> courses) {
		try {
			List<Course> dbCourses = Database.getCoursesForTeacher(getId());
			for (Course course : dbCourses) {
				System.out.println(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void viewMarks(HashMap<Student, Mark> marks) {
		for (HashMap.Entry<Student, Mark> entry : marks.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

	public void putMarks(Student student, Mark mark) {
		System.out.println("Mark " + mark + " assigned to " + student.getName());
	}

	public void sendComplaint(Student student, UrgencyLevel level) {
		System.out.println("Complaint sent to dean about " + student.getName() + " with urgency " + level);
	}

	@Override
	public void update(String message) {
		System.out.println("Teacher " + getFullname() + " received notification: " + message);
	}

	@Override
	public String logMessage() {
		return "Teacher{" +
				"id='" + getId() + '\'' +
				", fullName='" + getFullname() + '\'' +
				", department='" + getDepartment() + '\'' +
				", faculty='" + faculty + '\'' +
				", teacherType='" + teacherType + '\'' +
				", rating=" + rating +
				'}';
	}
}
