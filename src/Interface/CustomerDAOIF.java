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
     * Inserts a given customer into db
     * @param customer object to insert
     */
    void addUser(Customer customer) throws SQLException;

    /**
     * Fetches a given user from db by id
     * @param id of user to get
     * @return Customer object of user with given id
     */
    Customer getUserById(int id) throws SQLException;

    /**
     * Receives a given customer object and deletes the customer
     * from db. (hint: du kan bruke brukerens id i delete query)
     * @param customer to delete
     */
    void deleteUser(Customer customer) throws SQLException;

    /**
     * Fetches all users in db
     * @return list of all users in db
     */
    ObservableList<Customer> getAllUsers() throws SQLException;
}
