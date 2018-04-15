package Controllers;

import Code.Invoice;
import DAOs.CustomerDAO;
import DAOs.InvoiceDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddInvoiceController {
    @FXML private TextField invoiceCustomer;
    @FXML private TextField invoiceDate;


    public void addInvoice() throws SQLException {
        int customerid = Integer.parseInt(invoiceCustomer.getText());
        String date = invoiceDate.getText();
        if(date!=null && customerid!=0){
            Invoice invoice = new Invoice(
                    CustomerDAO.getInstance().getUserById(customerid),
                    date
            );
            InvoiceDAO.getInstance().addInvoice(invoice);
            Stage stage = (Stage) invoiceCustomer.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("something happen!");
        }

    }
}
