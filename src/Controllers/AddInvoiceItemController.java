package Controllers;

import Code.InvoiceItem;
import DAOs.InvoiceItemDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddInvoiceItemController {
    @FXML private TextField invoiceItemInvoiceId;
    @FXML private TextField invoiceItemProductId;

    public void addInvoiceItem() throws SQLException {
        int invoiceItemInvoice = Integer.parseInt(invoiceItemInvoiceId.getText());
        int invoiceItemProduct = Integer.parseInt(invoiceItemProductId.getText());
        if(invoiceItemInvoice!=0 && invoiceItemProduct!=0){
            InvoiceItem invoiceItem = new InvoiceItem(
                    invoiceItemInvoice,
                    invoiceItemProduct
            );
            InvoiceItemDAO.getInstance().addInvoiceItem(invoiceItem);
            Stage stage = (Stage) invoiceItemInvoiceId.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("something happen!");
        }

    }
}
