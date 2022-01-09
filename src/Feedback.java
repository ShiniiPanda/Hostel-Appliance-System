public class Feedback {

    private Customer customer;
    private String content, date;

    public Feedback(){
        this.customer = new Customer();
        this.content = "null";
        this.date = "00/00/0000";
    }

    public Feedback(Customer customer, String content, String date) {
        this.customer = customer;
        this.content = content;
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
