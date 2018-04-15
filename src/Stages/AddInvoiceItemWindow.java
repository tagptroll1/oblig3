package Stages;

import Controllers.AddInvoiceItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddInvoiceItemWindow {
    public void display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addInvoiceItem.fxml"));
        Parent root = loader.load();
        AddInvoiceItemController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add an invoice item");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
}
