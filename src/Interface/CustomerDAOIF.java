package Interface;

import Code.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 * Encapsulates user-related functionality for communicating with db
 */
public interface CustomerDAOIF {
    /**
     * Breaks down an object into fields ands stores them in the database
     * @param customer object to be broken down
     */
    void addUser(Customer customer) throws SQLException;

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return a customer object
     */
    Customer getUserById(int id);

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param customer object that holds id
     */
    void deleteUser(Customer customer);

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
    ObservableList<Customer> getAllUsers();
}
