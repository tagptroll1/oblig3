package Stages;

import Controllers.AddCategoryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCategoryWindow {
    public void display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addCategory.fxml"));
        Parent root = loader.load();
        AddCategoryController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add a category");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
}
