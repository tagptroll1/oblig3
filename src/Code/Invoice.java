package Code;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Invoice {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty created;
    private SimpleStringProperty due;
    private final SimpleDoubleProperty total;
    private ArrayList<Item> items;

    /**
     * Creates an invoice object to hold data from db
     * @param customerID customer_id
     * @param due date
     */
    public Invoice(int customerID, String due){
        this.id = new SimpleIntegerProperty(-1);
        this.customerId = new SimpleIntegerProperty(customerID);
        this.due = new SimpleStringProperty(due);
        this.created = new SimpleStringProperty(getDate());
        this.total = new SimpleDoubleProperty(0);
        this.items = new ArrayList<>();
    }

    public Invoice(int id, int customerID, String due){
        this.id = new SimpleIntegerProperty(id);
        this.customerId = new SimpleIntegerProperty(customerID);
        this.due = new SimpleStringProperty(due);
        this.created = new SimpleStringProperty(getDate());
        this.total = new SimpleDoubleProperty(0);
        this.items = new ArrayList<>();
    }

    public Invoice(int id, int customerID, String due, ArrayList<Item> items){
        this.id = new SimpleIntegerProperty(id);
        this.customerId = new SimpleIntegerProperty(customerID);
        this.due = new SimpleStringProperty(due);
        this.created = new SimpleStringProperty(getDate());
        this.total = new SimpleDoubleProperty(0);
        this.items = items;
        updateTotal();
    }

    //setters and getters
    private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getCustomerId(){
        return customerId.get();
    }

    public String getCreated() {
        return created.get();
    }

    public void setCreated(String created) {
        this.created.set(created);
    }

    public String getDue() {
        return due.get();
    }

    public void setDue(String due) {
        this.due.set(due);
    }

    public void updateTotal(){
        double temp = 0;
        for (Item i : items){
            temp += i.getPrice();
        }
        this.total.set(temp);
    }

    public double getTotal(){
        return this.total.get();
    }

    /**
     * Convenience s, not db related
     */
    public void addItem(Item item){
        this.items.add(item);
        double temp = this.total.get();
        temp += item.getPrice();
        this.total.set(temp);
    }


    public void removeItem(Item item){
        this.items.remove(item);
        double temp = this.total.get();
        temp -= item.getPrice();
        this.total.set(temp);
    }

    public ArrayList<Item> getItems(){
        return this.items;
    }

    public boolean hasItems(){
        return items.size() > 0;
    }
}
