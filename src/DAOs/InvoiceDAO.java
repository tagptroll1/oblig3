package DAOs;

import Code.Invoice;
import Code.Item;
import Errors.QueryError;
import Interface.InvoiceDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

import static Code.Utility.closeConnections;

public class InvoiceDAO implements InvoiceDAOIF {
    private static InvoiceDAO IDAO = null;
    private static ResultSet result;
    private static Statement state;
    private static Connection con = ConnectionDAO.getInstance().getConnection();


    private InvoiceDAO(){}

    /**
     * Get's the instance of self, makes sure connection is open and state is open
     * @return returns self
     * @throws SQLException
     */
    public static InvoiceDAO getInstance() throws SQLException {
        if (IDAO == null){
            IDAO = new InvoiceDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return IDAO;
    }

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param invoice object to be broken down
     */
    @Override
    public void addInvoice(Invoice invoice){
        try {
            getInstance();
            String sql = "INSERT OR REPLACE INTO invoice values(?,?,?);";
            PreparedStatement prpState = con.prepareStatement(sql);

            if (invoice.getId() != -1) prpState.setInt(1, invoice.getId());
            prpState.setInt(2, invoice.getCustomerId());
            prpState.setString(3, invoice.getDue());
            prpState.execute();

            System.out.println("Added: Invoice to db");
            closeConnections(prpState);
        } catch (SQLException e) {
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
    public Invoice getInvoiceById(int id){
        ArrayList<Item> items = new ArrayList<>();

        try {
            getInstance();
            String sql = "SELECT * FROM invoice WHERE invoice_id="+id;
            result = state.executeQuery(sql);

            // sjekk at den fikk noe
            result.next();
            if (result.getRow()==0) throw new QueryError("No result found within invoice table with id: "+id);

            Item item = new Item(
                    1,
                    "Test",
                    "desc",
                    55.99,
                    2
            );
            items.add(item);
            return new Invoice(
                    result.getInt("invoice_id"),
                    result.getInt("customer"),
                    result.getString("dato"),
                    items
            );
        } catch (SQLException e) {
            throw new QueryError(e.toString());
        } finally {
            closeConnections(result, state, con);
        }

    }

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param invoice object that holds id
     */
    @Override
    public void deleteInvoice(Invoice invoice){
        try {
            getInstance();
            String sql = "DELETE FROM invoice WHERE invoice_id = ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setInt(1, invoice.getId());
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
    public ObservableList<Invoice> getAllInvoices(){
        ObservableList<Invoice> voices = FXCollections.observableArrayList();

        try {
            getInstance();
            result = state.executeQuery("SELECT * FROM invoice ORDER BY invoice_id");

            while(result.next()){
                Invoice in = new Invoice(
                        result.getInt("invoice_id"),
                        result.getInt("customer"),
                        result.getString("dato")
                );
                voices.add(in);
            }
            return voices;
        } catch (SQLException e) {
            return voices;
        } finally {
            closeConnections(result, state, con);
        }
    }
}
