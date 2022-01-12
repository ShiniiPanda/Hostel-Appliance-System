public class Transaction {

    private Customer sender;
    private String invoiceID, senderName;
    private float amount;

    public Transaction(String invoiceID, Customer sender , String senderName, float amount) {
        this.invoiceID = invoiceID;
        this.sender = sender;
        this.senderName = senderName;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
