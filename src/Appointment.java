import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Appointment implements TextStored {

    private Customer customer;
    private String id, status, startDate, endDate;
    private HomeAppliance appliance;
    private Technician technician;

    // Text File Storage Format: ID//CustomerID//Technician//Status//StartDate//EndDate//ApplianceID

    public Appointment(){
        this.id = "000";
        this.customer = new Customer();
        this.technician = new Technician();
        this.status = "unknown";
        this.startDate = "00/00/0000";
        this.endDate = "00/00/0000";
        this.appliance = new HomeAppliance();
    }

    public Appointment(String id, Customer customer, Technician technician, String status, String startDate, String endDate, HomeAppliance appliance) {
        this.id = id;
        this.customer = customer;
        this.technician = technician;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appliance = appliance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public HomeAppliance getAppliance() {
        return appliance;
    }

    public void setAppliance(HomeAppliance appliance) {
        this.appliance = appliance;
    }

    public static List<Appointment> fetchAllAppointments(){
        List<Appointment> appointmentList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }

    //Overloaded type of fetchAppointments to handle Appointment Status
    public static List<Appointment> fetchAllAppointments(String status){
        List<Appointment> appointmentList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[3].equals(status.toUpperCase())){
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }

    public static List<Appointment> fetchIndividualAppointments(Technician technician){
        List<Appointment> appointmentList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[2].equals(technician.getId())) {
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

    public static List<Appointment> fetchIndividualAppointments(Technician technician, String status){
        List<Appointment> appointmentList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if ((lineArgs[2].equals(technician.getId())) &&
                        (lineArgs[3].equals(status.toUpperCase()))){
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }

    public static Appointment fetchById(String id)  {
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader file = new BufferedReader(fileReader);
            String line;
            String [] lineArgs;
            while ((line = file.readLine()) != null){
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)){
                    return new Appointment(
                            lineArgs[0],
                            Customer.fetchById(lineArgs[1]),
                            new Technician(User.fetchUserById(lineArgs[2])),
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5],
                            HomeAppliance.fetchById(lineArgs[6]));
                }
            }
            file.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new Appointment();
    }

    public static int fetchAppointmentCount(){
        int count = 0;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while (file.readLine() != null) count++;
            file.close();
            fileReader.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return count;
    }

    public static void addNewAppointment(Appointment a){
        String record = a.toTextFormat() + "\n";
        try {
            FileWriter fileWriter = new FileWriter("./TextFiles/Appointments.txt", true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(record);
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\nError writing appointment");
        }
    }

    public static void updateAppointment(Appointment appointment){
        int recordNumber = -1, lineNumber = 0;
        List<String> fileRows = new ArrayList<String>();
        String row;
        String id;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader read = new BufferedReader(fileReader);
            while ((row = read.readLine()) != null) {
                fileRows.add(row);
                id = row.substring(0, 4);
                if (id.equals(appointment.getId())){
                    recordNumber = lineNumber;
                }
                lineNumber++;
            }
            if (recordNumber != -1 ) {
                fileRows.set(recordNumber, appointment.toTextFormat());
            } else {
                return;
            }
            fileReader.close();
            read.close();
            rewriteFile(fileRows);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rewriteFile(List<String> rows) {
        try {
            FileWriter fileWriter = new FileWriter("./TextFiles/Appointments.txt");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String line : rows) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e){
            System.out.println("Error in writing");
        }
    }

    @Override
    public String toTextFormat(){
        return this.id + "//" + this.customer.getId() + "//" + this.technician.getId() + "//" + this.status + "//" +
                this.startDate + "//" + this.endDate + "//" + this.appliance.getId();
    }

}
