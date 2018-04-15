package Code;
/**
 * Class representing a given user
 */
public class Customer {
    // TODO User SimpleXProperty

    private int id;
    private String name;
    private int addressId;
    private String phone;
    private String billing;
    private String address;

    public Customer(String name, int address, String phone, String billing){
        this.id = -1;
        this.name = name;
        this.addressId = address;
        this.address = getAddress();
        this.phone = phone;
        this.billing = billing;
    }

    public Customer(int id, String name, int address, String phone, String billing){
        this.id = id;
        this.name = name;
        this.addressId = address;
        this.address = getAddress();
        this.phone = phone;
        this.billing = billing;
    }
    public Customer(){}
    // GETTERS AND SETTERS:


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        setAddressId(id);
    }
    public void setAddress(String adr){
       this.address = adr;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddressById(int id){

    }

}
