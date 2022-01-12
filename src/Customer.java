import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person{

    // Customer Text File Format : ID//NAME//ROLE//EMAIL//DOB//ROOM

    private int roomNumber;

    public Customer() {
        super();
        this.roomNumber = 0;
    }

    public Customer(String id, String name, String role, String email, String DOB, int roomNumber){
        super(id, name, role, email, DOB);
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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
                            Integer.parseInt(lineArgs[5]));
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
                        Integer.parseInt(lineArgs[5])));
            }
            file.close();
            fileReader.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return customerList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", DOB='" + DOB + '\'' +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
