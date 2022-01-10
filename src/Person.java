abstract public class Person {

    protected String id, name, role, email, DOB;

    public Person(){
        this.name = "null";
        this.id = "null";
        this.role = "default";
        this.email = "null";
        this.DOB = "00/00/0000";
    }
    public Person(String id, String name, String role, String email, String DOB){
        this.name = name;
        this.id = id;
        this.role = role;
        this.email = email;
        this.DOB = DOB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
}
