package DAOs;

import Code.Address;
import Errors.QueryError;
import Interface.AddressDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Code.Utility.closeConnections;

public class AddressDAO implements AddressDAOIF {
    private static AddressDAO ADAO = null;
    private static ResultSet result;
    private static Statement state;
    private static Connection con = ConnectionDAO.getInstance().getConnection();

    private AddressDAO(){}

    /**
     * Get's the instance of self, makes sure connection is open and state is open
     * @return returns self
     * @throws SQLException
     */
    public static AddressDAO getInstance() throws SQLException {
        if (ADAO == null){
            ADAO = new AddressDAO();
        }
        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return ADAO;
    }

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param address object to be broken down
     */
    @Override
    public void addAddress(Address address){
        try {
            getInstance();
            String sql = "INSERT OR REPLACE INTO address values(?,?,?,?,?);";
            PreparedStatement prpState = con.prepareStatement(sql);

            if (address.getId() != -1) prpState.setInt(1, address.getId());
            prpState.setString(2, address.getStreetNumber());
            prpState.setString(3, address.getStreetName());
            prpState.setString(4, address.getPostalCode());
            prpState.setString(5, address.getPostalTown());
            prpState.execute();

            closeConnections(prpState);
            System.out.println("Added: Address to db");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return
     */
    @Override
    public Address getAddressById(int id){
        try{
            getInstance();
            String sql = "SELECT * FROM address WHERE address_id="+id;
            result = state.executeQuery(sql);

            // sjekk at den fikk noe
            result.next();
            if (result.getRow()==0) throw new QueryError("No result found within address table with id: "+id);

            return new Address(
                    result.getInt("address_id"),
                    result.getString("street_number"),
                    result.getString("street_name"),
                    result.getString("postal_code"),
                    result.getString("postal_town")
            );
        } catch (SQLException e){
            throw new QueryError("No result found within address table with id: "+id);
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param address object that holds id
     */
    @Override
    public void deleteAddress(Address address){
        try {
            getInstance();
            String sql = "DELETE FROM address WHERE address_id = ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setInt(1, address.getId());
            prpState.executeUpdate();

            closeConnections(prpState);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
    @Override
    public ObservableList<Address> getAllAddresses(){
        ObservableList<Address> addresses = FXCollections.observableArrayList();
        try {
            getInstance();
            result = state.executeQuery("SELECT * FROM address ORDER BY address_id");

            while (result.next()) {
                Address address = new Address(
                        result.getInt("address_id"),
                        result.getString("street_number"),
                        result.getString("street_name"),
                        result.getString("postal_code"),
                        result.getString("postal_town")
                );
                addresses.add(address);
            }
            return addresses;
        } catch (SQLException e){
            return  addresses;
        } finally {
            closeConnections(result, state, con);
        }
    }
}
