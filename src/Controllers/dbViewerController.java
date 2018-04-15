package Controllers;

import Code.*;
import DAOs.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;
public class dbViewerController {
    @FXML private AnchorPane anchorPane;

    private int currentPage = 1;

    public void dbNextPage(){
    this.currentPage++;
    }

    public void dbPrevPage(){
    if (this.currentPage <= 1){
        this.currentPage = 1;
    } else {
        currentPage--;
    }
    }

    public void dbGoAddress() throws SQLException {
        ObservableList<Address> addresses = AddressDAO.getInstance().getAllAddresses();
        TableView<Address> dbTable = new TableView<>();
        dbTable.setPrefWidth(640);
        dbTable.setPrefHeight(314);

        TableColumn column = new TableColumn("Id");
        column.setMinWidth(50);
        column.setCellValueFactory(new PropertyValueFactory<Address, Integer>("id"));

        TableColumn SNColumn = new TableColumn("Street Number");
        SNColumn.setMinWidth(50);
        SNColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("streetNumber"));

        TableColumn SNaColumn = new TableColumn("Street Name");
        SNaColumn.setMinWidth(150);
        SNaColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("streetName"));

        TableColumn PCColumn = new TableColumn("Postal Code");
        PCColumn.setMinWidth(50);
        PCColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("postalCode"));

        TableColumn PTColumn = new TableColumn("Postal Town");
        PTColumn.setMinWidth(150);
        PTColumn.setCellValueFactory(new PropertyValueFactory<Address, String>("postalTown"));

        dbTable.setItems(addresses);
        dbTable.getColumns().addAll(column, SNColumn, SNaColumn, PCColumn, PTColumn);

        anchorPane.getChildren().add(2, dbTable);

    }

    public void dbGoCategory() throws SQLException {
        List<Category> categories = CategoryDAO.getInstance().getAllCategories();
    }

    public void dbGoCustomer() throws SQLException {
        List<Customer> customers = CustomerDAO.getInstance().getAllUsers();
    }

    public void dbGoInvoice() throws SQLException {
        List<Invoice> invoices = InvoiceDAO.getInstance().getAllInvoices();
    }

    public void dbGoInvoiceItem() throws SQLException {
        List<InvoiceItem> invoiceItems = InvoiceItemDAO.getInstance().getAllInvoiceItems();
    }

    public void dbGoProduct() throws SQLException {
        List<Item> products = ProductDAO.getInstance().getAllProducts();
    }
}