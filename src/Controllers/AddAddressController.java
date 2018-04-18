package Controllers;

import Code.Address;
import DAOs.AddressDAO;
import Errors.InsertionError;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddAddressController {
    @FXML private Label addressIdLabel;
    @FXML private TextField addressStreetNumber;
    @FXML private TextField addressStreetName;
    @FXML private TextField addressPostalCode;
    @FXML private TextField addressPostalTown;
    @FXML private Label addressTitle;
    private int id;
    private dbViewerController database;

    /**
     * Interface controller to create an address objects from textfield data
     * sends to AddressDAO to database storage
     * @throws SQLException
     */
    public void addAddress() throws SQLException {

        String streetN = addressStreetNumber.getText();
        String streetName = addressStreetName.getText();
        String postalCode = addressPostalCode.getText();
        String postalTown = addressPostalTown.getText();

        if(streetN!=null && streetName!=null && postalCode!=null && postalTown!=null){
            Address address = new Address(
                    id,
                    streetN,
                    streetName,
                    postalCode,
                    postalTown
            );
            AddressDAO.getInstance().addAddress(address);
            Stage stage = (Stage) addressStreetNumber.getScene().getWindow();
            if (database!=null) database.dbGoAddress();
            stage.close();
        } else {
            throw new InsertionError("One or multiple fields that are required in a new street is empty!");
        }

    }

    /**
     * Custom controller function to load insertion window with either "Auto ID" for adding inputs
     * or shows existing id of input that's being edited
     * @param address object selected from db, if any
     * @param db databaseController to select which table to show
     * @param title Title of the window
     */
    public void setOptionalId(Address address, dbViewerController db, String title){
        if (address==null){
            addressIdLabel.setText("Auto ID");
            id = -1;
        } else {
            addressIdLabel.setText("Id: "+address.getId());
            addressStreetNumber.setText(address.getStreetNumber());
            addressStreetName.setText(address.getStreetName());
            addressPostalCode.setText(address.getPostalCode());
            addressPostalTown.setText(address.getPostalTown());
            this.id = address.getId();
        }
        addressTitle.setText(title);
        database = db;
    }
}
