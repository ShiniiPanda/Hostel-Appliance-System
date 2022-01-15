import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HomeAppliance {

    private String id, name;
    private float pricePerDay;

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

    public static HomeAppliance fetchById(String id){
        String line;
        String [] lineArgs;
        try {
            FileReader fileReader = new FileReader("./TextFiles/Appliances.txt");
            BufferedReader file = new BufferedReader(fileReader);
            while ((line = file.readLine()) != null) {
                lineArgs = line.strip().split("//");
                if (lineArgs[0].equals(id)) {
                    return new HomeAppliance(
                            lineArgs[0],
                            lineArgs[1],
                            Float.parseFloat(lineArgs[2])
                    );
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new HomeAppliance();
    }
}
