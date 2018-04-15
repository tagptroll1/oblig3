package Code;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    // TODO User SimpleXProperty

    private int id;
    private Customer customer;
    private String created;
    private String due;
    private double total;
    private List<Item> items;

    public Invoice(){
        this.total = 0;
        this.items = new ArrayList<>();
    }

    public Invoice(Customer customer, String due){
        this.id = -1;
        this.customer = customer;
        this.due = due;
        this.total = 0.0;
        this.items = new ArrayList<>();
    }

    public Invoice(int id, Customer customer, String due){
        this.id = id;
        this.customer = customer;
        this.due = due;
        this.total = 0.0;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public double getTotal(){return this.total;}

    public void addItem(Item item){
        this.items.add(item);
        this.total += item.getPrice();
    }

    public List<Item> getItems(){
        return this.items;
    }

    public boolean hasItems(){
        return items.size() > 0;
    }
}
