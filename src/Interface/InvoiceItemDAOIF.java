package Interface;

import Code.InvoiceItem;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceItemDAOIF {
    void addInvoiceItem(InvoiceItem iItem) throws SQLException;
    InvoiceItem getInvoiceItemByInvoiceId(int id) throws SQLException;
    InvoiceItem getInvoiceItemByProductId(int id) throws SQLException;
    void deleteInvoiceItem(InvoiceItem iItem) throws SQLException;
    //TODO return observableList
    List<InvoiceItem> getAllInvoiceItems() throws SQLException;
}
