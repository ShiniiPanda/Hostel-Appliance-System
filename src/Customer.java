import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person implements TextStored {

    // Text File Storage Format: ID//NAME//ROLE//GENDER//EMAIL//DOB//ROOM-NUMBER

    private int roomNumber;
    private String gender;

    //Default Constructor
    public Customer() {
        super();
        this.roomNumber = 0;
        this.gender = "male";
    }

    public Customer(String id, String name, String state, String email, String DOB, String gender, int roomNumber){
        super(id, name, state, email, DOB);
        this.gender = gender;
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // This method returns a Customer object when given an ID.
    public static Customer fetchById(String id)  {
        try {
            FileReader fileReader = new FileReader(Constants.CUSTOMER_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            String line;
            String [] lineArgs;
            while ((line = file.readLine()) != null){
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)){
                    return new Customer(lineArgs[0],
                            lineArgs[1],
                            lineArgs[2],
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5],
                            Integer.parseInt(lineArgs[6]));
                }
            }
            file.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new Customer();
    }

    // This method returns a list of all the customers that can be found in Customers.txt
    public static List<Customer> fetchAllCustomers(){
        List<Customer> customerList = new ArrayList<Customer>();
        try {
            FileReader fileReader = new FileReader(Constants.CUSTOMER_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            String line;
            String[] lineArgs;
            while ((line = file.readLine()) != null){
                lineArgs = line.strip().split("//");
                customerList.add(new Customer(lineArgs[0],
                        lineArgs[1],
                        lineArgs[2],
                        lineArgs[3],
                        lineArgs[4],
                        lineArgs[5],
                        Integer.parseInt(lineArgs[6])));
            }
            file.close();
            fileReader.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return customerList;
    }

    //Fetches the total number of records inside Customers.txt
    public static int fetchCustomerCount(){
        int count = 0;
        try {
            FileReader fileReader = new FileReader(Constants.CUSTOMER_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while (file.readLine() != null) count++;
            file.close();
            fileReader.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return count;
    }

    //Appends a new Customer record to text file
    public static void addNewCustomer(Customer c) {
        String record = c.toTextFormat() + "\n";
        try {
            FileWriter fileWriter = new FileWriter(Constants.CUSTOMER_FILE, true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            file.write(record);
            file.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toTextFormat() {
        return this.getId() + "//" + this.getName() + "//" + this.getRole() + "//" + this.getGender() + "//" +
                this.getEmail() + "//" + this.getDOB() + "//" + this.getRoomNumber();
    }
}
