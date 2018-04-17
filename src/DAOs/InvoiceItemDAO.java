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
    private static PreparedStatement prpState;
    private static Connection con = ConnectionDAO.getInstance().getConnection();

    private InvoiceItemDAO(){};

    public static InvoiceItemDAO getInstance(String prpString) throws SQLException {
        if (IIDAO == null){
            IIDAO = new InvoiceItemDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();
        if (prpString != null && (prpState == null || prpState.isClosed())) prpState = con.prepareStatement(prpString);

        return IIDAO;
    }

    public static InvoiceItemDAO getInstance() throws SQLException {
        getInstance(null);
        return IIDAO;
    }

    @Override
    public void addInvoiceItem(InvoiceItem iItem){
        try {
            String sql = "INSERT OR REPLACE INTO invoice_items values(?,?);";
            getInstance(sql);

            if (!(iItem.getInvoiceId() != 0 && iItem.getProductId() != 0)){
                throw new InsertionError("Invoice item insertion error: one or both ids in invoice item are invalid");
            }
            prpState.setInt(1, iItem.getInvoiceId());
            prpState.setInt(2, iItem.getProductId());
            prpState.execute();
            System.out.println("Added: invoice item to db");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, prpState, con);
        }
    }

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
            closeConnections(result, state, prpState, con);
        }
    }

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
            closeConnections(result, state, prpState, con);
        }

    }

    @Override
    public void deleteInvoiceItem(InvoiceItem iItem){
        try {
            String sql = "DELETE FROM invoice_items WHERE invoice = ?;";
            getInstance(sql);

            prpState.setInt(1, iItem.getInvoiceId());
            prpState.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, prpState, con);
        }
    }

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
            closeConnections(result, state, prpState, con);
        }
    }
}
