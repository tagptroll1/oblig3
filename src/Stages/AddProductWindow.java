package Stages;

import Code.Item;
import Controllers.AddProductController;
import Controllers.dbViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class AddProductWindow {
    public void display(Item product, dbViewerController db, String title) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addProduct.fxml"));
        Parent root = loader.load();
        AddProductController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        controller.setOptionalId(product, db, title);
        window.setTitle("Add a product");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.showAndWait();
    }

    public void display(String title) throws IOException {
        display(null, null, title);
    }
}
