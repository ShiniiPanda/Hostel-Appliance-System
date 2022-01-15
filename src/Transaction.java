public class Transaction {

    private Customer sender;
    private String invoiceID;
    private Appointment appointment;
    private float amount;

    public Transaction() {
        this.invoiceID = "000";
        this.sender = new Customer();
        this.appointment = new Appointment();
        this.amount = 0;
    }

    public Transaction(String invoiceID, Customer sender, Appointment appointment, float amount) {
        this.invoiceID = invoiceID;
        this.sender = sender;
        this.appointment = appointment;
        this.amount = amount;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
