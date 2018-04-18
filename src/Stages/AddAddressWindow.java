package Stages;

import Code.Address;
import Controllers.AddAddressController;
import Controllers.dbViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAddressWindow {
    /**
     * Opens UI for adding addresses to db
     * @param address address to be added
     * @param db dbcontroller to change table according
     * @param title Title of the UI
     * @throws IOException
     */
    public void display(Address address, dbViewerController db, String title) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addAddress.fxml"));
        Parent root = loader.load();
        AddAddressController controller = loader.getController();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add an address");
        window.setScene(new Scene(root));

        controller.setOptionalId(address, db, title);
        window.setResizable(false);
        window.showAndWait();
    }

    /**
     * Overload function for a simple insertion window
     * @param title title of the window
     * @throws IOException
     */
    public void display(String title) throws IOException {
        display(null, null, title);
    }
}
