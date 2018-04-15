package Stages;

import Controllers.dbViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class dbViewerWindow {
    public void display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/dbViewer.fxml"));
        Parent root = loader.load();
        dbViewerController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Database Viewer");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
}
