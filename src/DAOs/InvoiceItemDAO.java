package DAOs;

import Code.InvoiceItem;
import Errors.InsertionError;
import Errors.QueryError;
import Interface.InvoiceItemDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Code.Utility.closeConnections;

public class InvoiceItemDAO implements InvoiceItemDAOIF {
    private static InvoiceItemDAO IIDAO = null;
    private static ResultSet result;
    private static Statement state;
    private static Connection con = ConnectionDAO.getInstance().getConnection();

    private InvoiceItemDAO(){}

    /**
     * Get's the instance of self, makes sure connection is open and state is open
     * @return returns self
     * @throws SQLException
     */
    public static InvoiceItemDAO getInstance() throws SQLException {
        if (IIDAO == null){
            IIDAO = new InvoiceItemDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return IIDAO;
    }

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param iItem object to be broken down
     */
    @Override
    public void addInvoiceItem(InvoiceItem iItem){
        try {
            getInstance();
            String sql = "INSERT OR REPLACE INTO invoice_items values(?,?);";
            PreparedStatement prpState = con.prepareStatement(sql);

            if (!(iItem.getInvoiceId() != 0 && iItem.getProductId() != 0)){
                throw new InsertionError("Invoice item insertion error: one or both ids in invoice item are invalid");
            }
            prpState.setInt(1, iItem.getInvoiceId());
            prpState.setInt(2, iItem.getProductId());
            prpState.execute();

            System.out.println("Added: invoice item to db");
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
     * @return an invoice object
     */
    @Override
    public InvoiceItem getInvoiceItemByInvoiceId(int id){
        try {
            getInstance();
            String sql = "SELECT * FROM invoice_items WHERE invoice="+id;
            result = state.executeQuery(sql);

            // sjekk at den fikk noe
            result.next();
            if (result.getRow()==0) throw new QueryError("No result found within category table with id: "+id);

            InvoiceItem iItem = new InvoiceItem(
                    result.getInt("invoice"),
                    result.getInt("product")
            );

            return iItem;
        } catch (SQLException e) {
            throw new QueryError(e.toString());
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * Gets row based on Product id
     * @param id object that holds id
     */
    @Override
    public InvoiceItem getInvoiceItemByProductId(int id){
        try {
            getInstance();
            String sql = "SELECT * FROM invoice_items WHERE product="+id;
            result = state.executeQuery(sql);

            // sjekk at den fikk noe
            result.next();
            if (result.getRow()==0) throw new QueryError("No result found within category table with id: "+id);

            return new InvoiceItem(
                    result.getInt("invoice"),
                    result.getInt("product")
            );
        } catch (SQLException e) {
            throw new QueryError(e.toString());
        } finally {
            closeConnections(result, state, con);
        }

    }

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * Alternative version to #deleteInvoiceItem that gets row from Invoice id
     * @param iItem object that holds id
     */
    @Override
    public void deleteInvoiceItem(InvoiceItem iItem){
        try {
            getInstance();
            String sql = "DELETE FROM invoice_items WHERE invoice = ? AND product = ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setInt(1, iItem.getInvoiceId());
            prpState.setInt(2, iItem.getProductId());
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
    public ObservableList<InvoiceItem> getAllInvoiceItems(){
        ObservableList<InvoiceItem> iItems = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM invoice_items ORDER BY invoice";
            getInstance();
            result = state.executeQuery(sql);

            while(result.next()){
                InvoiceItem iItem = new InvoiceItem(
                        result.getInt("invoice"),
                        result.getInt("product")
                );
                iItems.add(iItem);
            }
            return iItems;
        } catch (SQLException e) {
            return iItems;
        } finally {
            closeConnections(result, state, con);
        }
    }
}
