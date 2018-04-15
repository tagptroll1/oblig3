package Stages;

import Controllers.AddCustomerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerWindow {
    public void display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addCustomer.fxml"));
        Parent root = loader.load();
        AddCustomerController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add a customer");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
}
