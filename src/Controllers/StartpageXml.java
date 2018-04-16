package Controllers;

import Code.Invoice;
import Code.Item;
import DAOs.InvoiceDAO;
import DAOs.ProductDAO;
import Errors.QueryError;
import Stages.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;


public class StartpageXml {
   public void openFaktura(ActionEvent e){
        Faktura fak = new Faktura();
        Invoice faktura = new Invoice();
        InvoiceDAO IDAO = InvoiceDAO.getInstance();
        try {
            faktura = IDAO.getInvoiceById(1);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            Item aItem = ProductDAO.getInstance().getProductById(1);
            Item bItem = ProductDAO.getInstance().getProductById(2);
            faktura.addItem(aItem);
            faktura.addItem(bItem);
        } catch (SQLException | QueryError e1) {
            e1.printStackTrace();
        }


        try {
            fak.display(faktura);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void openDb(ActionEvent e) throws IOException {
       dbViewerWindow db = new dbViewerWindow();
       db.display();
    }

    public void openAddCustomer() throws IOException {
        AddCustomerWindow customerWindow = new AddCustomerWindow();
        customerWindow.display();
    }
    public void openAddCategory() throws IOException {
        AddCategoryWindow categoryWindow = new AddCategoryWindow();
        categoryWindow.display();
    }
    public void openAddAddress() throws IOException {
        AddAddressWindow addressWindow = new AddAddressWindow();
        addressWindow.display();
    }
    public void openAddInvoice() throws IOException {
        AddInvoiceWindow invoiceWindow = new AddInvoiceWindow();
        invoiceWindow.display();
    }
    public void openAddInvoiceItem() throws IOException {
        AddInvoiceItemWindow invoiceItemWindow = new AddInvoiceItemWindow();
        invoiceItemWindow.display();
    }
    public void openAddProduct() throws IOException {
        AddProductWindow productWindow = new AddProductWindow();
        productWindow.display();
    }

}
