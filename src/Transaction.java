import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Transaction implements TextStored {

    //Text File Storage Format: ID//APPOINTMENT-ID//AMOUNT

    private String invoiceID;
    private Appointment appointment;
    private float amount;

    //Default Constructor
    public Transaction() {
        this.invoiceID = "000";
        this.appointment = new Appointment();
        this.amount = 0;
    }

    public Transaction(String invoiceID, Appointment appointment, float amount) {
        this.invoiceID = invoiceID;
        this.appointment = appointment;
        this.amount = amount;
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

    //Method returns a list of all transactions.
    public static List<Transaction> fetchAllTransactions(){
        List<Transaction> transactionList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader(Constants.TRANSACTION_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                transactionList.add(new Transaction(
                        lineArgs[0],
                        Appointment.fetchById(lineArgs[1]),
                        Float.parseFloat(lineArgs[2])
                ));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return transactionList;
    }

    //Fetches the number of transaction records
    public static int fetchTransactionCount(){
        int count = 0;
        try {
            FileReader fileReader = new FileReader(Constants.TRANSACTION_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while (file.readLine() != null) count++;
            file.close();
            fileReader.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return count;
    }

    //Appends a new record to Transaction.txt
    public static void addNewTransaction(Transaction t){
        String record = t.toTextFormat() + "\n";
        try {
            FileWriter fileWriter = new FileWriter(Constants.TRANSACTION_FILE, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(record);
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\nError writing transaction");
        }
    }

    @Override
    public String toTextFormat() {
        return this.invoiceID + "//" + this.appointment.getId() + "//" + this.amount;
    }
}
