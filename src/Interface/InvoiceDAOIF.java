package Interface;

import Code.Invoice;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceDAOIF {
    void addInvoice(Invoice invoice) throws SQLException;
    Invoice getInvoiceById(int id) throws SQLException;
    void deleteInvoice(Invoice invoice) throws SQLException;
    ObservableList<Invoice> getAllInvoices() throws SQLException;
}
