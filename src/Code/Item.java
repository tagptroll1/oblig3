package Code;

public class Item {
    // TODO User SimpleXProperty

    private int id;
    private String name;
    private String description;
    private double price;
    private Category category;

    public Item(){}

    public Item(String name, String description, double price, Category category) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Item(int id, String name, String description, double price, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
