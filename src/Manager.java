public class Manager extends User {

    private String password;

    public Manager(){
        super();

    }
    public Manager(String id, String name, String role,  String email, String DOB, String password) {
        super(id, name, role, email, DOB, password);
    }

}
