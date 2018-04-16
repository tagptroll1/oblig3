package Controllers;


import Code.Customer;
import DAOs.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddCustomerController {
    @FXML private TextField customerName;
    @FXML private TextField customerAddress;
    @FXML private TextField customerPhone;
    @FXML private TextField customerBilling;

    public void addCustomer() throws SQLException {
        String name = customerName.getText();
        int address = Integer.parseInt(customerAddress.getText());
        String phone = customerPhone.getText();
        String billing = customerBilling.getText();
        if(name!=null && address != 0 && phone!=null && billing !=null){
            Customer customer = new Customer(
                    -1,
                    customerName.getText(),
                    Integer.parseInt(customerAddress.getText()),
                    customerPhone.getText(),
                    customerBilling.getText()
            );
            CustomerDAO.getInstance().addUser(customer);
            Stage stage = (Stage) customerName.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("something happen!");
        }

    }
}
