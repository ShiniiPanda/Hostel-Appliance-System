import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Technician extends User {

    /* IMPORTANT : TEST DATA */
    /* ID: technician0 || PASS: adminPass123 */
    /* ID: technician1 || PASS: passAdmin321 */

    public Technician(){
        super();
    }

    public Technician(String id, String password, String name, String role, String email, String DOB) {
        super(id, password, name, role, email, DOB);
    }

    //Cast Constructor, used to convert user to Technician
    public Technician(User user) {
        super(user.getId(), user.getPassword(), user.getName(), user.getRole(), user.getEmail(), user.getDOB());
    }

    public static List<Technician> fetchAllTechnicians() {
        List<Technician> technicianList = new ArrayList<>();
        String line;
        String[] lineArgs;
        try {
            FileReader fileReader = new FileReader(Constants.EMPLOYEE_FILE);
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

    // Fetching individual appointments for each technician
    public List<Appointment> fetchIndividualAppointments(){
        List<Appointment> appointmentList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader(Constants.APPOINTMENT_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[2].equals(this.getId())) { // check if technician matches
                    appointmentList.add(new Appointment(
                            lineArgs[0],
                            Customer.fetchById(lineArgs[1]),
                            new Technician(User.fetchUserById(lineArgs[2])),
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5],
                            HomeAppliance.fetchById(lineArgs[6])
                    ));
                }
            }
            file.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }

    //Overloaded version of above method, allows to specify status of appointment.
    public List<Appointment> fetchIndividualAppointments(String status){
        List<Appointment> appointmentList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader(Constants.APPOINTMENT_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if ((lineArgs[2].equals(this.getId())) && // ID Check
                        (lineArgs[3].equals(status.toUpperCase()))){ // STATUS Check
                    appointmentList.add(new Appointment(
                            lineArgs[0],
                            Customer.fetchById(lineArgs[1]),
                            new Technician(User.fetchUserById(lineArgs[2])),
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5],
                            HomeAppliance.fetchById(lineArgs[6])
                    ));
                }
            }
            file.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }

}
