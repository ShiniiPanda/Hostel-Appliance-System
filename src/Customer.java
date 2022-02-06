import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person{

    // Customer Text File Format : ID//NAME//ROLE//EMAIL//DOB//ROOM

    private int roomNumber;
    private String gender;

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
            FileReader fileReader = new FileReader("./TextFiles/Customers.txt");
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
            FileReader fileReader = new FileReader("./TextFiles/Customers.txt");
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

    public static int fetchCustomerCount(){
        int count = 0;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Customers.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while (file.readLine() != null) count++;
            file.close();
            fileReader.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return count;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", DOB='" + DOB + '\'' +
                ", Gender='" + gender + '\'' +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
