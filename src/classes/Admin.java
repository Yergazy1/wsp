package classes;
import classes.User;

public class Admin extends Employee{
    public Admin() {
        super();
    }
    
	public Admin(String id, String fullName, String email, String password) {
		super(id, fullName, email, password);
	}
	
	public void addUsers(User u) {
		((Admin) users).addUsers(u);
        System.out.println("User " + u.getFullname() + " added.");
	}

    public void removeUsers(User u) {
        ((Admin) users).removeUsers(u);
        System.out.println("User " + u.getFullname() + " removed.");
    }

    public void updateUser(User u, String newEmail, String newPassword) {
        u.setEmail(newEmail);
        u.setPassword(newPassword);
        System.out.println("User " + u.getFullname() + " updated.");
    }
	
	@Override
    public String logMessage() {
        return "Admin{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullname() + '\'' +
                ", department='" + getDepartment() + '\'' +
                '}';
    }
}