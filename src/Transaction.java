import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Transaction> fetchTransactions(){
        List<Transaction> transactionList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Transactions.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                transactionList.add(new Transaction(
                        lineArgs[0],
                        Customer.fetchById(lineArgs[1]),
                        Appointment.fetchById(lineArgs[2]),
                        Float.parseFloat(lineArgs[3])
                ));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return transactionList;
    }

}
