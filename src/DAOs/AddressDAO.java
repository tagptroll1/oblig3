package DAOs;

import Code.Address;
import Errors.QueryError;
import Interface.AddressDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Code.Utility.checkTable;

public class AddressDAO implements AddressDAOIF {
//TODO Open/close connection correctly, fix?
    private static AddressDAO ADAO = null;
    private static ResultSet result = null;
    private static Statement state = null;
    private static PreparedStatement prpState = null;
    private static Connection con = null;

    private AddressDAO(){}

    public static AddressDAO getInstance(String prpString) throws SQLException {
        if (ADAO == null){
            ADAO = new AddressDAO();
        }
        // Open and prepare resultset, statements and connection
        if (con == null) con = ConnectionDAO.getInstance().getConnection();
        if (state == null) state = con.createStatement();
        if (prpString != null && prpState == null) prpState = con.prepareStatement(prpString);

        return ADAO;
    }

    public static AddressDAO getInstance() throws  SQLException{
        getInstance(null);
        return ADAO;
    }

    private static void closeConnections(){
        if (result != null) try{result.close();} catch (SQLException e) {/*ignored*/};
        if (state != null) try{state.close();} catch (SQLException e) {/*ignored*/};
        if (prpState != null) try{prpState.close();} catch (SQLException e) {/*ignored*/};
        if (con != null) try{ConnectionDAO.getInstance().closeConnection();} catch (SQLException e) {/*ignored*/};
        result = null;
        state = null;
        prpState = null;
        con = null;
    }

    @Override
    public void addAddress(Address address){
        try {
            if (!checkTable("address")) return;
            String sql = "INSERT OR REPLACE INTO address values(?,?,?,?,?);";
            getInstance(sql);

            if (address.getId() != -1) prpState.setInt(1, address.getId());
            prpState.setString(2, address.getStreetNumber());
            prpState.setString(3, address.getStreetName());
            prpState.setString(4, address.getPostalCode());
            prpState.setString(5, address.getPostalTown());
            prpState.execute();
            System.out.println("Added: Address to db");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    @Override
    public Address getAddressById(int id){
        try{
            getInstance();

            String sql = "SELECT * FROM address WHERE address_id="+id;
            result = state.executeQuery(sql);
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
        } catch (SQLException e){
            throw new QueryError("No result found within address table with id: "+id);
        } finally {
            closeConnections();
        }
    }

    @Override
    public void deleteAddress(Address address){
        try {
            String sql = "DELETE FROM address WHERE address_id = ?;";
            getInstance(sql);

            prpState.setInt(1, address.getId());
            prpState.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

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
            closeConnections();
        }
    }
}
