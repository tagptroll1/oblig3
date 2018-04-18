package Code;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty categoryId;

    /**
     * Creates an item object to hold data from db
     * @param name product_name
     * @param description description
     * @param price price
     * @param categoryId category
     */
    public Item(String name, String description, double price, int categoryId) {
        this.id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.categoryId = new SimpleIntegerProperty(categoryId);
    }

    public Item(int id, String name, String description, double price, int categoryId) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.categoryId = new SimpleIntegerProperty(categoryId);
    }

    /*
    Setters and getters
     */
    public int getCategoryId() {
        return categoryId.get();
    }

    public void setCategoryId(int categoryId) {
        this.categoryId.set(categoryId);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

}
