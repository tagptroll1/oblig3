package Code;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 * Class representing a given user
 */
public class Customer {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty addressId;
    private SimpleStringProperty phone;
    private SimpleStringProperty billing;

    /**
     * Creates a Customer object to hold data from db
     * @param name
     * @param address
     * @param phone
     * @param billing
     */
    public Customer(String name, int address, String phone, String billing){
        this.id = new SimpleIntegerProperty(-1);
        this.name = new SimpleStringProperty(name);
        this.addressId = new SimpleIntegerProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.billing = new SimpleStringProperty(billing);
    }

    public Customer(int id, String name, int address, String phone, String billing){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.addressId = new SimpleIntegerProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.billing = new SimpleStringProperty(billing);
    }

    // GETTERS AND SETTERS:

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAddressId() {
        return addressId.get();
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getBilling() {
        return billing.get();
    }

    public void setBilling(String billing) {
        this.billing.set(billing);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }



}
