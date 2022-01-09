public class Manager extends User {

    private String password;

    public Manager(){
        super();

    }
    public Manager(String name, String id, String role,  String email, String DOB, String password) {
        super(name, id, role, email, DOB, password);
    }

}
