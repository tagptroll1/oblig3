package Controllers;

import Code.Invoice;
import DAOs.CustomerDAO;
import DAOs.InvoiceDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddInvoiceController {
    @FXML private Label invoiceIdLabel, invoiceTitle;
    @FXML private TextField invoiceCustomer;
    @FXML private TextField invoiceDate;
    private dbViewerController datebase;
    private int id;

    public void addInvoice() throws SQLException {
        int customerid = Integer.parseInt(invoiceCustomer.getText());
        String date = invoiceDate.getText();
        if(date!=null && customerid!=0){
            Invoice invoice = new Invoice(
                    id,
                    customerid,
                    date
            );
            InvoiceDAO.getInstance().addInvoice(invoice);
            Stage stage = (Stage) invoiceCustomer.getScene().getWindow();
            if (datebase!=null) datebase.dbGoInvoice();
            stage.close();
        } else {
            System.out.println("something happen!");
        }
    }

    public void setOptionalId(Invoice invoice, dbViewerController db, String title){
        if (invoice==null){
            invoiceIdLabel.setText("Auto ID");
            id = -1;
        } else {
            invoiceIdLabel.setText("Id: "+invoice.getId());
            invoiceCustomer.setText(invoice.getCustomerId()+"");
            invoiceDate.setText(invoice.getDue());
            this.id = invoice.getId();
        }
        invoiceTitle.setText(title);
        datebase = db;
    }
}
