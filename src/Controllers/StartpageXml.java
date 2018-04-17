package Controllers;

import Code.Invoice;
import DAOs.InvoiceDAO;
import Stages.*;
import java.io.IOException;
import java.sql.SQLException;


public class StartpageXml {
   public void openFaktura(){
        Faktura fak = new Faktura();

        try {
            Invoice faktura = InvoiceDAO.getInstance().getInvoiceById(1);
            fak.display(faktura);
        } catch (SQLException | IOException e1) {
            e1.printStackTrace();
        }
    }

    public void openDb() throws IOException {
       dbViewerWindow db = new dbViewerWindow();
       db.display();
    }

    public void openAddCustomer() throws IOException {
        AddCustomerWindow customerWindow = new AddCustomerWindow();
        customerWindow.display("Add a customer");
    }
    public void openAddCategory() throws IOException {
        AddCategoryWindow categoryWindow = new AddCategoryWindow();
        categoryWindow.display( "Add a category");
    }
    public void openAddAddress() throws IOException {
        AddAddressWindow addressWindow = new AddAddressWindow();
        addressWindow.display("Add an address");
    }
    public void openAddInvoice() throws IOException {
        AddInvoiceWindow invoiceWindow = new AddInvoiceWindow();
        invoiceWindow.display("Add an invoice");
    }
    public void openAddInvoiceItem() throws IOException {
        AddInvoiceItemWindow invoiceItemWindow = new AddInvoiceItemWindow();
        invoiceItemWindow.display("Add an invoice item");
    }
    public void openAddProduct() throws IOException {
        AddProductWindow productWindow = new AddProductWindow();
        productWindow.display("Add a product");
    }

}
