package Interface;

import Code.Address;

import java.sql.SQLException;
import java.util.List;

public interface AddressDAOIF {

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param address object to be broken down
     */
    void addAddress(Address address);

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return an address object
     */
    Address getAddressById(int id);

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param address object that holds id
     */
    void deleteAddress(Address address);

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
    List<Address> getAllAddresses();
}
