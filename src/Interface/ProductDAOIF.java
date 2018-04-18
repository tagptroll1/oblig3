package Interface;

import Code.Item;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAOIF {
    /**
     * Breaks down an object into fields ands stores them in the database
     * @param item object to be broken down
     */
    void addProduct(Item item) throws SQLException;

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return an item object
     */
    Item getProductById(int id) throws SQLException;

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param item object that holds id
     */
    void deleteProduct(Item item) throws SQLException;

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
    ObservableList<Item> getAllProducts() throws SQLException;
}
