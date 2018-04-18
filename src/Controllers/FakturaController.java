package Controllers;

import Code.*;
import DAOs.AddressDAO;
import DAOs.CustomerDAO;
import DAOs.InvoiceItemDAO;
import DAOs.ProductDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.net.URL;
import java.util.ResourceBundle;

import static Code.Utility.round;

public class FakturaController implements Initializable {
    @FXML private Label customerId;
    @FXML private Label invoiceId;
    @FXML private Label currentDate;
    @FXML private Label invoiceDue1;
    @FXML private Label invoiceDue2;
    @FXML private Label labelTotal;
    @FXML private Label labelBilling;
    @FXML private Label labelKroner;
    @FXML private Label labelOre;
    @FXML private VBox itemsBox;
    @FXML private VBox itemsMVA;
    @FXML private VBox itemsPrice;
    @FXML private Label gridTotal;
    @FXML private Label gridMVA;
    @FXML private Label digit1;
    @FXML private Label digit2;
    @FXML private Label digit3;
    @FXML private Label digit4;
    @FXML private Label digit5;
    @FXML private Label digit6;
    @FXML private Label digit7;
    @FXML private Label digit8;
    @FXML private Label digit9;
    @FXML private Label digit10;
    @FXML private Label digit11;
    @FXML private Label labelPaidBy;
    @FXML private Label labelPaidByAddress;
    @FXML private Label labelPaidByPostal;

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Makes sure the item lists are cleared
     */
    private void clearLists(){
        itemsBox.getChildren().clear();
        itemsMVA.getChildren().clear();
        itemsPrice.getChildren().clear();
    }

    /**
     * custom controller function that takes a faktura/invoice, fetches required information from it
     * Uses relations and other DAOs to fill in missing information and prints out a fully
     * written invoice
     * @param faktura invoice to be broke down
     * @throws SQLException
     */
    @FXML
    public void setValues(Invoice faktura) throws SQLException {

        double totalPrice = 0;
        clearLists();
        Customer customer = CustomerDAO.getInstance().getUserById(faktura.getCustomerId());
        Address address = AddressDAO.getInstance().getAddressById(customer.getAddressId());
        ObservableList<InvoiceItem> fetchedInvoiceItems = InvoiceItemDAO.getInstance().getAllInvoiceItems();
        ObservableList<Item> fetchedItems = ProductDAO.getInstance().getAllProducts();
        ArrayList<Item> items = new ArrayList<>();

        for (InvoiceItem i : fetchedInvoiceItems){
            for (Item j : fetchedItems){
                if (i.getProductId() == j.getId() && i.getInvoiceId() == faktura.getId()){
                    items.add(j);
                    totalPrice += j.getPrice();
                }
            }
        }
        totalPrice =  round(totalPrice,2);

        if (faktura.getCreated() == null){
            Date date = new Date();
            currentDate.setText(dateFormat.format(date));
        } else {
            currentDate.setText(faktura.getCreated());
        }
        customerId.setText(faktura.getCustomerId() + "");
        invoiceId.setText(faktura.getId() + "");
        invoiceDue1.setText(faktura.getDue());
        invoiceDue2.setText(faktura.getDue());
        labelBilling.setText(customer.getBilling());
        labelPaidBy.setText(customer.getName());

        if (items.size() > 0) {
            System.out.println("size > 0");
            double totalWMVA = round(totalPrice*1.25, 2);
            String[] splitTotal = (String.valueOf(totalWMVA)).split("\\.");
            String ore = splitTotal[1];
            if (ore.length() == 1){
                ore += "0";
            }
            String WMVAString = splitTotal[0] +"."+ore;
            labelKroner.setText(splitTotal[0]);
            labelOre.setText(ore);

            labelTotal.setText(WMVAString);
            gridMVA.setText(round(totalPrice * 0.25, 2) +"");
            gridTotal.setText(WMVAString);
            for (Item i : items) {
                Label name = new Label();
                Label mva = new Label();
                Label price = new Label();
                name.setText(i.getName());
                mva.setText("25%");
                price.setText(i.getPrice() + "");

                itemsBox.getChildren().add(name);
                itemsMVA.getChildren().add(mva);
                itemsPrice.getChildren().add(price);

            }
        } else {
            gridMVA.setText("0.00");
            gridTotal.setText("0.00");
            labelTotal.setText("0.00");
            labelKroner.setText("00");
            labelOre.setText("00");
        }


        char[] bill = customer.getBilling().toCharArray();
        if (bill.length >= 11) {
            digit1.setText(Character.toString(bill[0]));
            digit2.setText(Character.toString(bill[1]));
            digit3.setText(Character.toString(bill[2]));
            digit4.setText(Character.toString(bill[3]));
            digit5.setText(Character.toString(bill[4]));
            digit6.setText(Character.toString(bill[5]));
            digit7.setText(Character.toString(bill[6]));
            digit8.setText(Character.toString(bill[7]));
            digit9.setText(Character.toString(bill[8]));
            digit10.setText(Character.toString(bill[9]));
            digit11.setText(Character.toString(bill[10]));
        } else {
            System.out.println(bill.length);
        }

        labelPaidByAddress.setText(address.getStreetName() + " " + address.getStreetNumber());
        labelPaidByPostal.setText(address.getPostalCode() + " " + address.getPostalTown());

    }
}
