package Interface;

import Code.InvoiceItem;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceItemDAOIF {
    void addInvoiceItem(InvoiceItem iItem) throws SQLException;
    InvoiceItem getInvoiceItemByInvoiceId(int id) throws SQLException;
    InvoiceItem getInvoiceItemByProductId(int id) throws SQLException;
    void deleteInvoiceItem(InvoiceItem iItem) throws SQLException;
    ObservableList<InvoiceItem> getAllInvoiceItems() throws SQLException;
}
