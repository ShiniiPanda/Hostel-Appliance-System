public class Manager extends User {

    public Manager(){
        super();

    }
    public Manager(String id, String password, String name,  String role, String email, String DOB) {
        super(id, password, name, role, email, DOB);
    }

    public Manager(User user) {
        super(user.getId(), user.getPassword(), user.getName(), user.getRole(), user.getEmail(), user.getDOB());
    }

}
