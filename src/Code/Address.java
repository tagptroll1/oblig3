package Code;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Address {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty streetNumber;
    private final SimpleStringProperty streetName;
    private final SimpleStringProperty postalCode;
    private final SimpleStringProperty postalTown;

    /**
     * Creates an Address object to hold data from DB
     * @param streetNumber street_number
     * @param streetName street_name
     * @param postalCode postal_code
     * @param postalTown postal_town
     */
    public Address(String streetNumber, String streetName, String postalCode, String postalTown) {
        this.id = new SimpleIntegerProperty(-1);
        this.streetNumber = new SimpleStringProperty(streetNumber);
        this.streetName = new SimpleStringProperty(streetName);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.postalTown = new SimpleStringProperty(postalTown);
    }

    public Address(int id, String streetNumber, String streetName, String postalCode, String postalTown) {
        this.id = new SimpleIntegerProperty(id);
        this.streetNumber = new SimpleStringProperty(streetNumber);
        this.streetName = new SimpleStringProperty(streetName);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.postalTown = new SimpleStringProperty(postalTown);
    }

    // Getters and setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getStreetNumber() {
        return streetNumber.get();
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber.set(streetNumber);
    }

    public String getStreetName() {
        return streetName.get();
    }

    public void setStreetName(String streetName) {
        this.streetName.set(streetName);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getPostalTown() {
        return postalTown.get();
    }

    public void setPostalTown(String postalTown) {
        this.postalTown.set(postalTown);
    }
}
