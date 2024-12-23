package classes;

public class Employee extends User{
	private String department;
    private String id;
    private String fullName;
    private String email;
    private String password;
	
    public Employee() {
        super();
    }
    
	public Employee(String id, String fullName, String email, String password) {
		super(id, fullName, email, password);
		this.department = department;
	}
	
	public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public String logMessage() {
        return "Employee{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullname() + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
    public String getId() {
        return id;
    }
}