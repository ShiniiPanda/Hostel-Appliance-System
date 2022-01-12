import javax.print.DocFlavor;

public class Technician extends User {

    public Technician(){
        super();
    }

    public Technician(String id, String name, String role,  String email, String DOB, String password) {
        super(id, name, role, email, DOB, password);
    }

}
