package Stages;

import Controllers.AddProductController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddProductWindow {
    public void display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addProduct.fxml"));
        Parent root = loader.load();
        AddProductController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add a product");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
}
