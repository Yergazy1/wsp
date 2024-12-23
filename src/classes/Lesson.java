package classes;
import java.util.Date;
import enums.LessonType;

public class Lesson {
    private Course course;
    private LessonType type;
    private int room;
    private String time;

    public Lesson() {}

    public Lesson(Course course, LessonType type, int room) {
        this.course = course;
        this.type = type;
        this.room = room;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LessonType getType() {
        return this.type;
    }

    public void setLessonType(LessonType type) {
        this.type = type;
    }

    public int getRoom() {
        return this.room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "course=" + course +
                ", type=" + type +
                ", room=" + room +
                ", time='" + time + '\'' +
                '}';
    }
}