package Code;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Category {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;

    /**
     * Creates a Category object to hold db data
     * @param name category_name
     */
    public Category(String name){
        this.id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty(name);
    }
    public Category(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    // Setters and getters
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
}
