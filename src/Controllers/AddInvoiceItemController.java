package Controllers;

import Code.InvoiceItem;
import DAOs.InvoiceItemDAO;
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
        int invoiceItemInvoice = Integer.parseInt(invoiceItemInvoiceId.getText());
        int invoiceItemProduct = Integer.parseInt(invoiceItemProductId.getText());
        if(invoiceItemInvoice!=0 && invoiceItemProduct!=0){
            InvoiceItem invoiceItem = new InvoiceItem(
                    invoiceItemInvoice,
                    invoiceItemProduct
            );
            InvoiceItemDAO.getInstance().addInvoiceItem(invoiceItem);
            Stage stage = (Stage) invoiceItemInvoiceId.getScene().getWindow();
            if (database!=null) database.dbGoInvoiceItem();
            stage.close();
        } else {
            System.out.println("something happen!");
        }
    }
    public void setOptionalId(InvoiceItem invoiceItem, dbViewerController db, String title){
        if (invoiceItem!=null){
            invoiceItemInvoiceId.setText("Id: "+invoiceItem.getInvoiceId());
            invoiceItemProductId.setText(invoiceItem.getProductId() +"");
        }
        invoiceItemTitle.setText(title);
        database = db;
    }
}
