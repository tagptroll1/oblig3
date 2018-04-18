package Stages;

import Code.Customer;
import Controllers.AddCustomerController;
import Controllers.dbViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerWindow {
    /**
     * Opens UI for adding customers to db
     * @param customer customer to be added
     * @param db dbcontroller to change table according
     * @param title Title of the UI
     * @throws IOException
     */
    public void display(Customer customer, dbViewerController db, String title) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addCustomer.fxml"));
        Parent root = loader.load();
        AddCustomerController controller = loader.getController();
        window.initModality(Modality.APPLICATION_MODAL);

        controller.setOptionalId(customer, db, title);
        window.setTitle("Add a customer");
        window.setScene(new Scene(root));
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
