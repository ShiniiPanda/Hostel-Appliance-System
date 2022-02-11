import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User extends Person implements TextStored {

    private String password;

    public User(){
        super();
        this.password = "null";
    }


    public User(String id, String password, String name, String role, String email, String DOB){
        super(id, name, role, email, DOB);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // This method is used for validating the username and password of user attemtping to login.
    public static LoginResponse validateLogin(String id, String pass){
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null){
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)) {
                    if (lineArgs[1].equals(pass)){
                        return new LoginResponse(0, new User(
                                lineArgs[0],
                                lineArgs[1],
                                lineArgs[2],
                                lineArgs[3],
                                lineArgs[4],
                                lineArgs[5])); // validated
                    } else {
                        return new LoginResponse(2, null); // Password incorrect
                    }
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return new LoginResponse(1, null); // Username not found
    }

    public static User fetchUserById(String id) {
        String line;
        String[] lineArgs;
        User user = null;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)) {
                    user = new User(lineArgs[0],
                            lineArgs[1],
                            lineArgs[2],
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5]);
                }
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static List<User> fetchAllUsers() {
        String line;
        String[] lineArgs;
        List<User> userList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                    userList.add(new User(lineArgs[0],
                            lineArgs[1],
                            lineArgs[2],
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5]));
            }
            file.close();
            fileReader.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public static List<User> fetchAllUsers(String role) {
        String line;
        String[] lineArgs;
        List<User> userList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if(lineArgs[3].equals(role)) {
                    userList.add(new User(lineArgs[0],
                            lineArgs[1],
                            lineArgs[2],
                            lineArgs[3],
                            lineArgs[4],
                            lineArgs[5]));
                }
            }
            file.close();
            fileReader.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public static void addNewUser(User u) {
        String record = u.toTextFormat() + "\n";
        try {
            FileWriter fileWriter = new FileWriter("./TextFiles/Employees.txt", true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            file.write(record);
            file.close();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUser(User user){
        int recordNumber = -1, lineNumber = 0;
        List<String> fileRows = new ArrayList<String>();
        String row;
        String id;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Employees.txt");
            BufferedReader read = new BufferedReader(fileReader);
            while ((row = read.readLine()) != null) {
                fileRows.add(row);
                id = row.substring(0, row.indexOf("//"));
                if (id.equals(user.getId())){
                    recordNumber = lineNumber;
                }
                lineNumber++;
            }
            if (recordNumber != -1 ) {
                if (user.getRole().equals("Delete")) {
                    fileRows.remove(recordNumber);
                } else {
                    fileRows.set(recordNumber, user.toTextFormat());
                }
            } else {
                return;
            }
            fileReader.close();
            read.close();
            rewriteFile(fileRows);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rewriteFile(List<String> rows) {
        try {
            FileWriter fileWriter = new FileWriter("./TextFiles/Employees.txt");
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (String line : rows) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e){
            System.out.println("Error in writing");
        }
    }

    @Override
    public String toTextFormat() {
        return this.id + "//" + this.password + "//" + this.name + "//" + this.role + "//" + this.email + "//" + this.DOB;
    }
}
