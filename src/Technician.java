import javax.print.DocFlavor;

public class Technician extends User {

    public Technician(){
        super();
    }

    public Technician(String id, String password, String name, String role, String email, String DOB) {
        super(id, password, name, role, email, DOB);
    }

    public Technician(User user) {
        super(user.getId(), user.getPassword(), user.getName(), user.getRole(), user.getEmail(), user.getDOB());
    }

}
