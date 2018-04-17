package Controllers;

import Code.*;
import DAOs.*;
import Stages.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class dbViewerController implements Initializable {
    // TODO view faktura fra db viewer

    @FXML private AnchorPane anchorPane;
    @FXML private Button dbBtnAdd, dbBtnEdit, dbBtnDel;
    private Address selectedAddress;
    private Customer selectedCustomer;
    private Category selectedCategory;
    private Invoice selectedInvoice;
    private InvoiceItem selectedInvoiceItem;
    private Item selectedProduct;
    private dbViewerController self = this;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image imageOk = new Image(getClass().getResourceAsStream("add.png"), 25, 25, true,false);
        dbBtnAdd.setGraphic(new ImageView(imageOk));

        Image imageEdit = new Image(getClass().getResourceAsStream("edit.png"), 25, 25, true,false);
        dbBtnEdit.setGraphic(new ImageView(imageEdit));

        Image imageDel = new Image(getClass().getResourceAsStream("delete.png"), 25, 25, true,false);
        dbBtnDel.setGraphic(new ImageView(imageDel));

        dbBtnDel.setDisable(true);
        dbBtnEdit.setDisable(true);

        try {
            dbGoAddress();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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

    private void removeDB(){
        if (anchorPane.getChildren().size() >= 3){
            anchorPane.getChildren().remove(2);
        }
        // Set all selected to make sure nothing gets accidently deleted

        selectedAddress = null;
        selectedCustomer = null;
        selectedCategory = null;
        selectedInvoice = null;
        selectedInvoiceItem = null;
        selectedProduct = null;

        dbBtnEdit.setDisable(true);
        dbBtnDel.setDisable(true);
    }

    public void dbGoAddress() throws SQLException {
        //TODO Add check, implement warning window
        removeDB();

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

        dbTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                selectedAddress =  dbTable.getSelectionModel().getSelectedItem();
                dbBtnDel.setDisable(false);
                dbBtnEdit.setDisable(false);
            }
        });
        dbBtnDel.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddressDAO.getInstance().deleteAddress(selectedAddress);
                    dbGoAddress();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddAddressWindow addressWindow = new AddAddressWindow();
                    addressWindow.display(selectedAddress, self, "Edit an address");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddAddressWindow addressWindow = new AddAddressWindow();
                    addressWindow.display(null, self, "Add an address");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        anchorPane.getChildren().add(2, dbTable);

    }

    public void dbGoCategory() throws SQLException {
        //TODO Add check, implement warning window
        removeDB();
        ObservableList<Category> categories = CategoryDAO.getInstance().getAllCategories();
        TableView<Category> dbTable = new TableView<>();
        dbTable.setPrefWidth(640);
        dbTable.setPrefHeight(314);

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));

        TableColumn CNColumn = new TableColumn("Category name");
        CNColumn.setMinWidth(50);
        CNColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));

        dbTable.setItems(categories);
        dbTable.getColumns().addAll(idColumn, CNColumn);

        dbTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                selectedCategory =  dbTable.getSelectionModel().getSelectedItem();
                dbBtnDel.setDisable(false);
                dbBtnEdit.setDisable(false);
            }
        });
        dbBtnDel.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    CategoryDAO.getInstance().deleteCategory(selectedCategory);
                    dbGoCategory();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddCategoryWindow categoryWindow = new AddCategoryWindow();
                    categoryWindow.display(selectedCategory, self, "Edit a category");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddCategoryWindow categoryWindow = new AddCategoryWindow();
                    categoryWindow.display(null, self, "Add a category");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        anchorPane.getChildren().add(2, dbTable);
    }

    public void dbGoCustomer() throws SQLException {
        //TODO Add check, implement warning window
        removeDB();

        ObservableList<Customer> customers = CustomerDAO.getInstance().getAllUsers();
        TableView<Customer> dbTable = new TableView<>();
        dbTable.setPrefWidth(640);
        dbTable.setPrefHeight(314);

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));

        TableColumn CNColumn = new TableColumn("Customer name");
        CNColumn.setMinWidth(50);
        CNColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));

        TableColumn adColumn = new TableColumn("Address Id");
        adColumn.setMinWidth(50);
        adColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("addressId"));

        TableColumn phColumn = new TableColumn("Phone number");
        phColumn.setMinWidth(50);
        phColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));

        TableColumn biColumn = new TableColumn("Billing Account");
        biColumn.setMinWidth(50);
        biColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("billing"));

        dbTable.setItems(customers);
        dbTable.getColumns().addAll(idColumn, CNColumn, adColumn, phColumn, biColumn);

        dbTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                selectedCustomer =  dbTable.getSelectionModel().getSelectedItem();
                dbBtnDel.setDisable(false);
                dbBtnEdit.setDisable(false);
            }
        });
        dbBtnDel.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    CustomerDAO.getInstance().deleteUser(selectedCustomer);
                    dbGoCustomer();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddCustomerWindow customerWindow = new AddCustomerWindow();
                    customerWindow.display(selectedCustomer, self, "Edit a customer");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddCustomerWindow customerWindow = new AddCustomerWindow();
                    customerWindow.display(null, self, "Add a customer");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        anchorPane.getChildren().add(2, dbTable);
    }

    public void dbGoInvoice() throws SQLException {
        //TODO Add check, implement warning window
        removeDB();

        ObservableList<Invoice> invoices = InvoiceDAO.getInstance().getAllInvoices();
        TableView<Invoice> invoiceTable = new TableView<>();
        invoiceTable.setPrefWidth(640);
        invoiceTable.setPrefHeight(314);

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Integer>("id"));

        TableColumn CIColumn = new TableColumn("Customer Id");
        CIColumn.setMinWidth(50);
        CIColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Integer>("customerId"));

        TableColumn dateColumn = new TableColumn("Date duo");
        dateColumn.setMinWidth(50);
        dateColumn.setCellValueFactory(new PropertyValueFactory<Invoice, String>("due"));

        invoiceTable.setItems(invoices);
        invoiceTable.getColumns().addAll(idColumn, CIColumn, dateColumn);
        invoiceTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                selectedInvoice =  invoiceTable.getSelectionModel().getSelectedItem();
                dbBtnDel.setDisable(false);
                dbBtnEdit.setDisable(false);
            }
        });
        dbBtnDel.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    InvoiceDAO.getInstance().deleteInvoice(selectedInvoice);
                    dbGoInvoice();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddInvoiceWindow invoiceWindow = new AddInvoiceWindow();
                    invoiceWindow.display(selectedInvoice, self, "Edit an invoice");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddInvoiceWindow invoiceWindow = new AddInvoiceWindow();
                    invoiceWindow.display(null, self, "Add an invoice");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        anchorPane.getChildren().add(2, invoiceTable);
    }

    public void dbGoInvoiceItem() throws SQLException {
        //TODO Add check, implement warning window
        //TODO Doesn't have id.. maybe add? or multiple relations
        removeDB();
        ObservableList<InvoiceItem> invoiceItems = InvoiceItemDAO.getInstance().getAllInvoiceItems();
        TableView<InvoiceItem> dbTable = new TableView<>();
        dbTable.setPrefWidth(640);
        dbTable.setPrefHeight(314);

        TableColumn idColumn = new TableColumn("Invoice Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<InvoiceItem, Integer>("invoiceId"));

        TableColumn CNColumn = new TableColumn("Product Id");
        CNColumn.setMinWidth(50);
        CNColumn.setCellValueFactory(new PropertyValueFactory<InvoiceItem, Integer>("productId"));

        dbTable.setItems(invoiceItems);
        dbTable.getColumns().addAll(idColumn, CNColumn);

        dbTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                selectedInvoiceItem =  dbTable.getSelectionModel().getSelectedItem();
                dbBtnDel.setDisable(false);
                dbBtnEdit.setDisable(false);
            }
        });
        dbBtnDel.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    InvoiceItemDAO.getInstance().deleteInvoiceItem(selectedInvoiceItem);
                    dbGoInvoiceItem();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddInvoiceItemWindow invoiceItemWindow = new AddInvoiceItemWindow();
                    invoiceItemWindow.display(selectedInvoiceItem, self, "Edit an invoice item");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddInvoiceItemWindow invoiceItemWindow = new AddInvoiceItemWindow();
                    invoiceItemWindow.display(null, self, "Add an invoice item");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        anchorPane.getChildren().add(2, dbTable);
    }

    public void dbGoProduct() throws SQLException {
        //TODO Add check, implement warning window
        removeDB();
        ObservableList<Item> products = ProductDAO.getInstance().getAllProducts();
        TableView<Item> dbTable = new TableView<>();
        dbTable.setPrefWidth(640);
        dbTable.setPrefHeight(314);

        TableColumn idColumn = new TableColumn("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));

        TableColumn CNColumn = new TableColumn("Product name");
        CNColumn.setMinWidth(50);
        CNColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));

        TableColumn adColumn = new TableColumn("Description");
        adColumn.setMinWidth(50);
        adColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        TableColumn phColumn = new TableColumn("Price");
        phColumn.setMinWidth(50);
        phColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("price"));

        TableColumn biColumn = new TableColumn("Category ID");
        biColumn.setMinWidth(50);
        biColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("categoryId"));

        dbTable.setItems(products);
        dbTable.getColumns().addAll(idColumn, CNColumn, adColumn, phColumn, biColumn);

        dbTable.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                selectedProduct =  dbTable.getSelectionModel().getSelectedItem();
                dbBtnDel.setDisable(false);
                dbBtnEdit.setDisable(false);
            }
        });
        dbBtnDel.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ProductDAO.getInstance().deleteProduct(selectedProduct);
                    dbGoProduct();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddProductWindow addProductWindow = new AddProductWindow();
                    addProductWindow.display(selectedProduct, self, "Edit a product");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dbBtnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddProductWindow addProductWindow = new AddProductWindow();
                    addProductWindow.display(null, self, "Add a product");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        anchorPane.getChildren().add(2, dbTable);
    }
}
