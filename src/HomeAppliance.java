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
}
