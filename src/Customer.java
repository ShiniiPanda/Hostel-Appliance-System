import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static Customer fetchById(String id) throws IOException {
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
        return new Customer();
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
