package Controllers;

import Code.Customer;
import Code.Invoice;
import Code.Item;
import DAOs.CustomerDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.URL;
import java.util.ResourceBundle;

import static Code.Utility.round;

public class FakturaController implements Initializable {
    @FXML private Label customerID;
    @FXML private Label invoiceID;
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
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    public void setValues(Invoice faktura) throws SQLException {

        // TODO recreate Factura og hent data basert på faktura/invoice referanser
        double totalPrice = round(faktura.getTotal(),2);
        Customer customer = CustomerDAO.getInstance().getUserById(faktura.getCustomerId());

        if (faktura.getCreated() == null){
            Date date = new Date();
            currentDate.setText(dateFormat.format(date));
        } else {
            currentDate.setText(faktura.getCreated());
        }
        customerID.setText(faktura.getCustomerId() + "");
        invoiceID.setText(faktura.getId() + "");
        invoiceDue1.setText(faktura.getDue());
        invoiceDue2.setText(faktura.getDue());
        labelBilling.setText(customer.getBilling());
        labelPaidBy.setText(customer.getName());

        if (faktura.hasItems()) {
            double totalWMVA = round(totalPrice*1.25, 2);
            String[] splitTotal = (String.valueOf(totalWMVA)).split("\\.");
            labelKroner.setText(splitTotal[0]);
            labelOre.setText(splitTotal[1]);

            labelTotal.setText(totalWMVA + "");
            gridMVA.setText(round(faktura.getTotal() * 0.25, 2) +"");
            gridTotal.setText(totalWMVA + "");
            for (Item i : faktura.getItems()) {
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

    }
}
