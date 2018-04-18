package Interface;

import Code.InvoiceItem;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceItemDAOIF {
    /**
     * Breaks down an object into fields ands stores them in the database
     * @param iItem object to be broken down
     */
    void addInvoiceItem(InvoiceItem iItem);

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return an invoiceitem
     */
    InvoiceItem getInvoiceItemByInvoiceId(int id);

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * Gets row based on Product id
     * @param id object that holds id
     */
    InvoiceItem getInvoiceItemByProductId(int id);

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * Alternative version to #deleteInvoiceItem that gets row from Invoice id
     * @param iItem object that holds id
     */
    void deleteInvoiceItem(InvoiceItem iItem);

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
    ObservableList<InvoiceItem> getAllInvoiceItems();
}
