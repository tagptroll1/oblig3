package Stages;

import Controllers.AddAddressController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAddressWindow {
    public void display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addAddress.fxml"));
        Parent root = loader.load();
        AddAddressController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add an address");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
}
