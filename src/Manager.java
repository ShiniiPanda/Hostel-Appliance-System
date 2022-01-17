public class Manager extends User {

    public Manager(){
        super();

    }
    public Manager(String id, String name, String role,  String email, String DOB, String password) {
        super(id, name, role, email, DOB, password);
    }

    public Manager(User user) {
        super(user.getId(), user.getName(), user.getRole(), user.getEmail(), user.getDOB(), user.getPassword());
    }

}
