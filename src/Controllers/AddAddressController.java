package Controllers;

import Code.Address;
import DAOs.AddressDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddAddressController {
    @FXML private TextField addressStreetNumber;
    @FXML private TextField addressStreetName;
    @FXML private TextField addressPostalCode;
    @FXML private TextField addressPostalTown;

    public void addAddress() throws SQLException {
        String streetN = addressStreetNumber.getText();
        String streetName = addressStreetName.getText();
        String postalCode = addressPostalCode.getText();
        String postalTown = addressPostalTown.getText();

        if(streetN!=null && streetName!=null && postalCode!=null && postalTown!=null){
            Address address = new Address(
                    -1,
                    streetN,
                    streetName,
                    postalCode,
                    postalTown
            );
            AddressDAO.getInstance().addAddress(address);
            Stage stage = (Stage) addressStreetNumber.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("something happen!");
        }

    }
}
