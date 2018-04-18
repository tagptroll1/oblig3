package Interface;

import Code.Invoice;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface InvoiceDAOIF {
    /**
     * Breaks down an object into fields ands stores them in the database
     * @param invoice object to be broken down
     */
    void addInvoice(Invoice invoice);

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return a invoice object
     */
    Invoice getInvoiceById(int id);

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param invoice object that holds id
     */
    void deleteInvoice(Invoice invoice);

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
    ObservableList<Invoice> getAllInvoices();
}
