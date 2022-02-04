import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User extends Person {

    private String password;

    public User(){
        super();
        this.password = "null";
    }

    public User(String id, String password, String name, String role, String email, String DOB){
        super(id, name, role, email, DOB);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // This method is used for validating the username and password of user attemtping to login.
    public static LoginResponse validateLogin(String id, String pass){
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null){
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)) {
                    if (lineArgs[1].equals(pass)){
                        return new LoginResponse(0, new User(
                                lineArgs[0],
                                lineArgs[1],
                                lineArgs[2],
                                lineArgs[3],
                                lineArgs[4],
                                lineArgs[5])); // validated
                    } else {
                        return new LoginResponse(2, null); // Password incorrect
                    }
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return new LoginResponse(1, null); // Username not found
    }
}
