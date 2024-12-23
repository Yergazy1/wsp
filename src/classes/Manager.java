package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import enums.LessonType;
import interfaces.CanViewCourses;
import interfaces.CanViewStudents;
import interfaces.CanViewTeachers;

public class Manager extends Employee implements CanViewTeachers, CanViewStudents, CanViewCourses {
	private List<Course> courses;
	private List<Lesson> schedule;
	private List<Teacher> teachers;
	private List<Student> students;
	private List<News> news;

	public Manager() {
		super();
		this.courses = new ArrayList<>();
		this.schedule = new ArrayList<>();
		this.teachers = new ArrayList<>();
		this.students = new ArrayList<>();
		this.news = new ArrayList<>();
	}

	public Manager(String id, String fullName, String email, String password) {
		super(id, fullName, email, password);
		this.courses = new ArrayList<>();
		this.schedule = new ArrayList<>();
		this.teachers = new ArrayList<>();
		this.students = new ArrayList<>();
		this.news = new ArrayList<>();
	}

	public void addCourse(Course course) {
		if (course == null) {
			throw new IllegalArgumentException("Course cannot be null.");
		}

		if (!courses.contains(course)) {
			courses.add(course);
			course.notifyObservers("New course added: " + course.getCourseName());
			System.out.println("Course " + course.getCourseName() + " added.");
		} else {
			System.out.println("Course " + course.getCourseName() + " is already in the list.");
		}
	}

	public void deleteCourse(Course course) {
		if (courses.remove(course)) {
			System.out.println("Course " + course.getCourseName() + " deleted.");
		} else {
			System.out.println("Course " + course.getCourseName() + " not found.");
		}
	}

	public void manageCourses(Course course) {
		System.out.println("Managing course: " + course.getCourseName());
	}

	@Override
	public void viewStudents(List<Student> students) {
		for (Student student : students) {
			System.out.println(student);
		}
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

	public void makeSchedule(Course course, List<LessonType> lessonTypes, List<String> timeSlots) {
		if (course.getInstructors().size() != lessonTypes.size() || course.getInstructors().size() != timeSlots.size()) {
			throw new IllegalArgumentException("Each instructor must have a corresponding LessonType and time slot.");
		}

		int[] availableRooms = {101, 102, 103, 201, 202};
		if (course.getInstructors().size() > availableRooms.length) {
			throw new IllegalArgumentException("Not enough rooms for all instructors.");
		}

		for (int i = 0; i < course.getInstructors().size(); i++) {
			Teacher teacher = course.getInstructors().get(i);
			LessonType type = lessonTypes.get(i);
			String time = timeSlots.get(i);
			int room = availableRooms[i];

			Lesson lesson = new Lesson(course, type, room);
			lesson.setTime(time);
			schedule.add(lesson);

			System.out.println("Lesson created: " + lesson + " at " + time + " with " + teacher.getFullname());
		}
	}

	public void assignTeachersToCourse(Course course, List<Teacher> instructors) {
		for (Teacher instructor : instructors) {
			course.addInstructor(instructor);
			System.out.println("Assigned teacher " + instructor.getFullname() + " to course " + course.getCourseName());
		}
	}

	@Override
	public String logMessage() {
		return "Manager{" +
				"id='" + getId() + '\'' +
				", fullName='" + getFullname() + '\'' +
				", department='" + getDepartment() + '\'' +
				'}';
	}
}