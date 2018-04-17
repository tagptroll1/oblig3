package Controllers;

import Code.InvoiceItem;
import DAOs.InvoiceItemDAO;
import Errors.InsertionError;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddInvoiceItemController {
    @FXML private TextField invoiceItemInvoiceId;
    @FXML private TextField invoiceItemProductId;
    @FXML private Label invoiceItemTitle;
    private dbViewerController database;

    public void addInvoiceItem() throws SQLException {
        if ((invoiceItemProductId.getText() == null || invoiceItemProductId.getText().trim().isEmpty())
                || (invoiceItemInvoiceId.getText() == null || invoiceItemInvoiceId.getText().trim().isEmpty())){
            throw new InsertionError("Product id and/or Invoice id cannot be empty!");
        }
        int invoiceItemInvoice = Integer.parseInt(invoiceItemInvoiceId.getText());
        int invoiceItemProduct = Integer.parseInt(invoiceItemProductId.getText());

        if(invoiceItemInvoice<=0 && invoiceItemProduct<=0) {
            throw new InsertionError("Invoice id and/or Product id cannot be 0!");
        }

        InvoiceItem invoiceItem = new InvoiceItem(
                invoiceItemInvoice,
                invoiceItemProduct
        );
        InvoiceItemDAO.getInstance().addInvoiceItem(invoiceItem);
        Stage stage = (Stage) invoiceItemInvoiceId.getScene().getWindow();
        if (database!=null) database.dbGoInvoiceItem();
        stage.close();

    }
    public void setOptionalId(InvoiceItem invoiceItem, dbViewerController db, String title){
        if (invoiceItem!=null){
            invoiceItemInvoiceId.setText(invoiceItem.getInvoiceId() + "");
            invoiceItemProductId.setText(invoiceItem.getProductId() + "");
        }
        invoiceItemTitle.setText(title);
        database = db;
    }
}
