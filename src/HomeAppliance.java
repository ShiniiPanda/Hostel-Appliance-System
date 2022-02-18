import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeAppliance {

    // Text File Storage Format : ID//NAME//PRICE-PER-DAY

    private String id, name;
    private float pricePerDay;

    //Default Constructor
    public HomeAppliance(){
        this.id = "000";
        this.name = "null";
        this.pricePerDay = 0;
    }

    public HomeAppliance(String id, String name, float pricePerDay) {
        this.id = id;
        this.name = name;
        this.pricePerDay = pricePerDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    //Fetches an appliance using its unique identification number
    public static HomeAppliance fetchById(String id){
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader(Constants.APPLIANCE_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)) { // ID CHECK
                    return new HomeAppliance(
                            lineArgs[0],
                            lineArgs[1],
                            Float.parseFloat(lineArgs[2])
                    );
                }
            }
            file.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new HomeAppliance();
    }

    //Fetches a list of all appliances found in HomeAppliance.txt
    public static List<HomeAppliance> fetchAllAppliance(){
        List<HomeAppliance> applianceList = new ArrayList<>();
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader(Constants.APPLIANCE_FILE);
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                    applianceList.add(new HomeAppliance(
                            lineArgs[0],
                            lineArgs[1],
                            Float.parseFloat(lineArgs[2])
                    ));
            }
            file.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return applianceList;
    }
}
