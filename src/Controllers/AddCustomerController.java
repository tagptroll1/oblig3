package Controllers;


import Code.Customer;
import DAOs.CustomerDAO;
import Errors.InsertionError;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddCustomerController {
    @FXML private TextField customerName;
    @FXML private TextField customerAddress;
    @FXML private TextField customerPhone;
    @FXML private TextField customerBilling;
    @FXML private Label customerIdLabel, customerTitle;
    private dbViewerController database;
    private int id;

    public void addCustomer() throws SQLException {
        if (customerAddress == null || customerAddress.getText().trim().isEmpty()){
            throw new InsertionError("Address field cannot be empty!");
        }

        String name = customerName.getText();
        int address = Integer.parseInt(customerAddress.getText());

        if (address <= 0){
            throw new InsertionError("Address id cannot be 0!");
        }
        String phone = customerPhone.getText();
        String billing = customerBilling.getText();
        if(name!=null && phone!=null && billing !=null){
            Customer customer = new Customer(
                    id,
                    customerName.getText(),
                    Integer.parseInt(customerAddress.getText()),
                    customerPhone.getText(),
                    customerBilling.getText()
            );
            CustomerDAO.getInstance().addUser(customer);
            Stage stage = (Stage) customerName.getScene().getWindow();
            if (database!=null) database.dbGoCustomer();
            stage.close();
        } else {
            throw new InsertionError("One or multiple fields that are required in a new customer is empty!");
        }

    }
    public void setOptionalId(Customer customer, dbViewerController db, String title){
        if (customer==null){
            customerIdLabel.setText("Auto ID");
            id = -1;
        } else {
            customerIdLabel.setText("Id: "+customer.getId());
            customerName.setText(customer.getName());
            customerAddress.setText(customer.getAddressId()+"");
            customerPhone.setText(customer.getPhone());
            customerBilling.setText(customer.getBilling());
            this.id = customer.getId();
        }
        customerTitle.setText(title);
        database = db;
    }
}
