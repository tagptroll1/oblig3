package DAOs;

import Code.Address;
import Errors.QueryError;
import Interface.AddressDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Code.Utility.checkTable;

public class AddressDAO implements AddressDAOIF {

    private static AddressDAO ADAO = null;

    private AddressDAO(){}

    public static AddressDAO getInstance(){
        if (ADAO == null){
            ADAO = new AddressDAO();
        }
        return ADAO;
    }

    @Override
    public void addAddress(Address address) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        if (!checkTable("address")) return;

        String sql = "INSERT OR REPLACE INTO address values(?,?,?,?,?);";
        PreparedStatement state = con.prepareStatement(sql);
        if (address.getId() != -1) state.setInt(1, address.getId());
        state.setString(2, address.getStreetNumber());
        state.setString(3, address.getStreetName());
        state.setString(4, address.getPostalCode());
        state.setString(5, address.getPostalTown());
        state.execute();
        System.out.println("Added: Address to db");
    }

    @Override
    public Address getAddressById(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();

        String sql = "SELECT * FROM address WHERE address_id="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within address table with id: "+id);

        Address address = new Address(
                result.getInt("address_id"),
                result.getString("street_number"),
                result.getString("street_name"),
                result.getString("postal_code"),
                result.getString("postal_town")
        );

        return address;
    }

    @Override
    public void deleteAddress(Address address) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        String sql = "DELETE FROM address WHERE address_id = ?;";
        PreparedStatement state = con.prepareStatement(sql);
        state.setInt(1, address.getId());
        state.executeUpdate();
    }

    @Override
    public ObservableList<Address> getAllAddresses() throws SQLException {
        getInstance();

        ObservableList<Address> addresses = FXCollections.observableArrayList();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM address ORDER BY address_id");

        while(rs.next()){
            Address address = new Address(
                    rs.getInt("address_id"),
                    rs.getString("street_number"),
                    rs.getString("street_name"),
                    rs.getString("postal_code"),
                    rs.getString("postal_town")
            );
            addresses.add(address);
        }
        return addresses;
    }
}
