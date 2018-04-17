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
    private static PreparedStatement prpState;
    private static Connection con = ConnectionDAO.getInstance().getConnection();

    private AddressDAO(){}

    public static AddressDAO getInstance(String prpString) throws SQLException {
        if (ADAO == null){
            ADAO = new AddressDAO();
        }
        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();
        if (prpString != null && (prpState == null || prpState.isClosed())) prpState = con.prepareStatement(prpString);

        return ADAO;
    }

    public static AddressDAO getInstance() throws  SQLException{
        getInstance(null);
        return ADAO;
    }

    @Override
    public void addAddress(Address address){
        try {
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
            closeConnections(result, state, prpState, con);
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
            closeConnections(result, state, prpState, con);
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
            closeConnections(result, state, prpState, con);
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
            closeConnections(result, state, prpState, con);
        }
    }
}
