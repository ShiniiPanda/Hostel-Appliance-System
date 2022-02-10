import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Technician extends User {

    public Technician(){
        super();
    }

    public Technician(String id, String password, String name, String role, String email, String DOB) {
        super(id, password, name, role, email, DOB);
    }

    public Technician(User user) {
        super(user.getId(), user.getPassword(), user.getName(), user.getRole(), user.getEmail(), user.getDOB());
    }

    public static List<Technician> fetchAllTechnicians() {
        List<Technician> technicianList = new ArrayList<>();
        String line;
        String[] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[3].equals("Technician")) {
                    technicianList.add(new Technician(lineArgs[0],
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
        return technicianList;
    }

}
