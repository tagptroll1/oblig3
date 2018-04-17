package Stages;

import Code.InvoiceItem;
import Controllers.AddInvoiceItemController;
import Controllers.dbViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddInvoiceItemWindow {
    public void display(InvoiceItem invoiceItem, dbViewerController db, String title) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addInvoiceItem.fxml"));
        Parent root = loader.load();
        AddInvoiceItemController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        controller.setOptionalId(invoiceItem, db, title);
        window.setTitle("Add an invoice item");
        window.setScene(new Scene(root));

        window.showAndWait();
    }
    public void display(String title) throws IOException {
        display(null, null, title);
    }
}
