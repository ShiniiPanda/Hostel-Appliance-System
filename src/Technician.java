import javax.print.DocFlavor;

public class Technician extends User {


    public Technician(){
        super();
    }

    public Technician(String name, String id,  String email, String DOB, String password) {
        super(name, id, name, email, DOB, password);
    }

}
