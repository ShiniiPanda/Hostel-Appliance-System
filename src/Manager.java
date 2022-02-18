import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manager extends User {

    /* IMPORTANT : TEST DATA */
    /* ID: manager0 || PASS: adminPass123 */
    /* ID: manager1 || PASS: passAdmin321 */

    //Default Constructor, inherited from User.class
    public Manager(){
        super();
    }

    public Manager(String id, String password, String name,  String role, String email, String DOB) {
        super(id, password, name, role, email, DOB);
    }

    //Cast Constructor, used to convert user to Manager
    public Manager(User user) {
        super(user.getId(), user.getPassword(), user.getName(), user.getRole(), user.getEmail(), user.getDOB());
    }

    public static List<Manager> fetchAllManagers() {
        List<Manager> managerList = new ArrayList<>();
        String line;
        String[] lineArgs;
        try {
            FileReader fileReader = new FileReader(Constants.EMPLOYEE_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[3].equals("Manager")) {
                    managerList.add(new Manager(lineArgs[0],
                            lineArgs[1],
                            lineArgs[2],
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5]));
                }
            }
            file.close();
            fileReader.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return managerList;
    }


}
