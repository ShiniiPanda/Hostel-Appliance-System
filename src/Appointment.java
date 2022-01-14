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


}
