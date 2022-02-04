import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

    private Customer customer;
    private String id, status, startDate, endDate;
    private HomeAppliance appliance;

    // Text File Storage Format: ID//CustomerID//Status//StartDate//EndDate//ApplianceID

    public Appointment(){
        this.id = "000";
        this.customer = new Customer();
        this.status = "unknown";
        this.startDate = "00/00/0000";
        this.endDate = "00/00/0000";
        this.appliance = new HomeAppliance();
    }

    public Appointment(String id, Customer customer, String status, String startDate, String endDate, HomeAppliance appliance) {
        this.id = id;
        this.customer = customer;
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

    public static List<Appointment> fetchAppointments(){
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
                        lineArgs[2],
                        lineArgs[3],
                        lineArgs[4],
                        HomeAppliance.fetchById(lineArgs[5])
                ));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }

    //Overloaded type of fetchAppointments to handle Appointment Status
    public static List<Appointment> fetchAppointments(String status){
        List<Appointment> appointmentList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appointments.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[2].equals(status.toUpperCase())){
                    appointmentList.add(new Appointment(
                            lineArgs[0],
                            Customer.fetchById(lineArgs[1]),
                            lineArgs[2],
                            lineArgs[3],
                            lineArgs[4],
                            HomeAppliance.fetchById(lineArgs[5])
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
                            lineArgs[2],
                            lineArgs[3],
                            lineArgs[4],
                            HomeAppliance.fetchById(lineArgs[5]));
                }
            }
            file.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new Appointment();
    }
}
