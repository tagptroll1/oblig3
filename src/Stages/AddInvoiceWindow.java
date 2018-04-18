package Stages;

import Code.Invoice;
import Controllers.AddInvoiceController;
import Controllers.dbViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AddInvoiceWindow {
    /**
     * Opens UI for adding invoices to db
     * @param invoice invoice to be added
     * @param db dbcontroller to change table according
     * @param title Title of the UI
     * @throws IOException
     */
    public void display(Invoice invoice, dbViewerController db, String title) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addInvoice.fxml"));
        Parent root = loader.load();
        AddInvoiceController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        controller.setOptionalId(invoice, db, title);
        window.setTitle("Add an invoice");
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
