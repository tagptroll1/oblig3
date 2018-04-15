package Interface;

import Code.Address;

import java.sql.SQLException;
import java.util.List;

public interface AddressDAOIF {

    void addAddress(Address address) throws SQLException;
    Address getAddressById(int id) throws SQLException;
    void deleteAddress(Address address) throws SQLException;
    List<Address> getAllAddresses() throws SQLException;
}
