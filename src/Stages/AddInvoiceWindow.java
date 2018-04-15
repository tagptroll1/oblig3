package Stages;

import Controllers.AddInvoiceController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddInvoiceWindow {
    public void display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addInvoice.fxml"));
        Parent root = loader.load();
        AddInvoiceController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Add an invoice");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
}
