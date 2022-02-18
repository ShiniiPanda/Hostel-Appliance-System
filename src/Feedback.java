import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Feedback implements TextStored {

    // Text File Storage Format: CUSTOMER-ID//APPLIANCE-ID//APPOINTMENT-ID//DATE//RATING//CONTENT

    private Customer customer;
    private Appointment appointment;
    private String content, date;
    private int rating;
    private HomeAppliance appliance;

    //Default Constructor
    public Feedback(){
        this.customer = new Customer();
        this.appliance = new HomeAppliance();
        this.appointment = new Appointment();
        this.date = "0000/00/00";
        this.rating = 5;
        this.content = "No Message";
    }

    public Feedback(Customer customer, HomeAppliance appliance, Appointment appointment, String date, int rating, String content ) {
        this.customer = customer;
        this.appliance = appliance;
        this.appointment = appointment;
        this.date = date;
        this.rating = rating;
        this.content = content;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HomeAppliance getAppliance() {
        return appliance;
    }

    public void setAppliance(HomeAppliance appliance) {
        this.appliance = appliance;
    }

    //Appends a new Feedback record to text file
    public static void addNewFeedback(Feedback f){
        String record = f.toTextFormat() + "\n";
        try {
            FileWriter fileWriter = new FileWriter(Constants.FEEDBACK_FILE, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(record);
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\nError writing feedback");
        }
    }

    @Override
    public String toTextFormat() {
        return this.customer.getId() + "//" + this.appliance.getId() + "//" +
                this.appointment.getId() + "//" + this.date + "//" + this.rating + "//" + this.content;
    }
}
