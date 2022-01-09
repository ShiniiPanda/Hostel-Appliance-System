import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

abstract public class User extends Person {

    private String password;

    public User(){
        super();
        this.password = "null";
    }

    public User(String name, String id, String role, String email, String DOB, String password){
        super(name, id, role, email, DOB);
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static int validateLogin(String id, String pass){
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null){
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)) {
                    if (lineArgs[1].equals(pass)){
                        return 0; // validated
                    } else {
                        return 2; // Password incorrect
                    }
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return 1; // Username not found
    }
}
