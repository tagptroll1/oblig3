package DAOs;

import Code.Customer;
import Errors.QueryError;
import Interface.CustomerDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Code.Utility.closeConnections;

public class CustomerDAO implements CustomerDAOIF {
    private static CustomerDAO UDAO = null;
    private static ResultSet result;
    private static Statement state;
    private static Connection con = ConnectionDAO.getInstance().getConnection();

    private CustomerDAO(){}

    /**
     * Get's the instance of self, makes sure connection is open and state is open
     * @return returns self
     * @throws SQLException
     */
    public static CustomerDAO getInstance() throws SQLException {
        if (UDAO == null){
            UDAO = new CustomerDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return UDAO;
    }

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param customer object to be broken down
     */
    @Override
    public void addUser(Customer customer){
        try {
            getInstance();
            String sql = "INSERT OR REPLACE INTO customer values(?,?,?,?,?);";
            PreparedStatement prpState = con.prepareStatement(sql);

            if (customer.getId() != -1) prpState.setInt(1, customer.getId());
            prpState.setString(2, customer.getName());
            prpState.setInt(3, customer.getAddressId());
            prpState.setString(4, customer.getPhone());
            prpState.setString(5, customer.getBilling());
            prpState.execute();

            System.out.println("Added: " + customer.getName());
            closeConnections(prpState);
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
    public Customer getUserById(int id){
        try {
            getInstance();
            String sql = "SELECT * FROM customer WHERE customer_id="+id;
            result = state.executeQuery(sql);

            // sjekk at den fikk noe
            result.next();
            if (result.getRow()==0) throw new QueryError("No result found within customer table with id: "+id);

            return new Customer(
                    result.getInt("customer_id"),
                    result.getString("customer_name"),
                    result.getInt("address"),
                    result.getString("phone_number"),
                    result.getString("billing_account")
            );
        } catch (SQLException e) {
            throw new QueryError("No result found within customer table with id: "+id);
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param customer object that holds id
     */
    @Override
    public void deleteUser(Customer customer){
        try {
            getInstance();
            String sql = "DELETE FROM customer WHERE customer_name LIKE ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setString(1, customer.getName());
            prpState.executeUpdate();

            closeConnections(prpState);
        } catch (SQLException e) {
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
    public ObservableList<Customer> getAllUsers(){
        ObservableList<Customer> customers =FXCollections.observableArrayList();

        try {
            getInstance();
            String sql = "SELECT * FROM customer ORDER BY customer_id";
            result = state.executeQuery(sql);

            while(result.next()){
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("customer_name"),
                        result.getInt("address"),
                        result.getString("phone_number"),
                        result.getString("billing_account")
                );
                customers.add(customer);
            }

            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
            return customers;
        } finally {
            closeConnections(result, state, con);
        }
    }
}
