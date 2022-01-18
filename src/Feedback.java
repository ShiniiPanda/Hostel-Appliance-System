public class Feedback {

    private Customer customer;
    private String content, date;
    private HomeAppliance appliance;

    public Feedback(){
        this.customer = new Customer();
        this.content = "null";
        this.date = "00/00/0000";
        this.appliance = new HomeAppliance();
    }

    //This is a constructor for the feedback class
    public Feedback(Customer customer, String content, String date, HomeAppliance appliance) {
        this.customer = customer;
        this.content = content;
        this.date = date;
        this.appliance = appliance;
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
}
