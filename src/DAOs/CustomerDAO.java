package DAOs;

import Code.Customer;
import Errors.QueryError;
import Interface.CustomerDAOIF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Code.Utility.checkTable;

public class CustomerDAO implements CustomerDAOIF {
    private static CustomerDAO UDAO = null;

    private CustomerDAO(){}

    public static CustomerDAO getInstance(){
        if (UDAO == null){
            UDAO = new CustomerDAO();
        }
        return UDAO;
    }

    @Override
    public void addUser(Customer customer) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        if (!checkTable("customer")) return;

        String sql = "INSERT OR REPLACE INTO customer values(?,?,?,?,?);";
        PreparedStatement state = con.prepareStatement(sql);
        if (customer.getId() != -1) state.setInt(1, customer.getId());
        state.setString(2, customer.getName());
        state.setInt(3, customer.getAddressId());
        state.setString(4, customer.getPhone());
        state.setString(5, customer.getBilling());
        state.execute();
        System.out.println("Added: "+customer.getName());
    }

    @Override
    public Customer getUserById(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();

        String sql = "SELECT * FROM customer WHERE customer_id="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within customer table with id: "+id);

        Customer customer = new Customer();
        customer.setName(result.getString("customer_name"));
        customer.setAddressId(result.getInt("address"));
        customer.setPhone(result.getString("phone_number"));
        customer.setBilling(result.getString("billing_account"));
        return customer;
    }

    @Override
    public void deleteUser(Customer customer) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();


        String sql = "DELETE FROM customer WHERE customer_name LIKE ?;";
        PreparedStatement state = con.prepareStatement(sql);
        state.setString(1, customer.getName());
        state.executeUpdate();
    }

    @Override
    public List<Customer> getAllUsers() throws SQLException {
        //TODO return observableList

        getInstance();

        List<Customer> customers = new ArrayList<>();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM customer ORDER BY customer_id");

        while(rs.next()){
            Customer customer = new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("customer_name"),
                    rs.getInt("address"),
                    rs.getString("phone_number"),
                    rs.getString("billing_account")
            );
            customers.add(customer);
        }

        return customers;
    }
}
