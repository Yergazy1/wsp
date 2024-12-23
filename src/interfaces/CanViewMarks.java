package interfaces;
import java.util.HashMap;
import java.util.List;
import classes.Mark;
import classes.Student;

public interface CanViewMarks {
	void viewMarks(HashMap<Student, Mark> marks);
}