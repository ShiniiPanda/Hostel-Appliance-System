public class Transaction {

    private String senderID, invoiceID, senderName;
    private float amount;

    public Transaction(String invoiceID, String senderID , String senderName, float amount) {
        this.invoiceID = invoiceID;
        this.senderID = senderID;
        this.senderName = senderName;
        this.amount = amount;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
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
