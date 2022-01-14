public class Transaction {

    private Customer sender;
    private String invoiceID;
    private float amount;

    public Transaction() {
        this.invoiceID = "000";
        this.sender = new Customer();
        this.amount = 0;
    }

    public Transaction(String invoiceID, Customer sender, float amount) {
        this.invoiceID = invoiceID;
        this.sender = sender;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
