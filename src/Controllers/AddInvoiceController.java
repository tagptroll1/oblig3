package Controllers;

import Code.Invoice;
import DAOs.InvoiceDAO;
import Errors.InsertionError;
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

    /**
     * Fetches data from UI to store in db
     * @throws SQLException
     */
    public void addInvoice() throws SQLException {
        String date = invoiceDate.getText();

        if (invoiceCustomer.getText() == null || invoiceCustomer.getText().trim().isEmpty() ){
            throw new InsertionError("Customer id cannot be empty!");
        }
        int customerid = Integer.parseInt(invoiceCustomer.getText());
        if (customerid >= 0){
            throw new InsertionError("Customer Id cannot be 0!");
        }
        if(date!=null && !date.trim().isEmpty()){
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
            throw new InsertionError("Date field cannot be empty!");
        }
    }

    /**
     * Custom controller function to load insertion window with either "Auto ID" for adding inputs
     * or shows existing id of input that's being edited
     * @param invoice object selected from db, if any
     * @param db databaseController to select which table to show
     * @param title Title of the window
     */
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
