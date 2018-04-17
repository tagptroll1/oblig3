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

    public static InvoiceItemDAO getInstance() throws SQLException {
        if (IIDAO == null){
            IIDAO = new InvoiceItemDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return IIDAO;
    }


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
            //closeConnections(prpState);
            closeConnections(result, state, prpState, con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

            closeConnections(result, state, con);
            return iItem;
        } catch (SQLException e) {
            throw new QueryError(e.toString());
        } finally {
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

            InvoiceItem invoiceItem = new InvoiceItem(
                    result.getInt("invoice"),
                    result.getInt("product")
            );
            closeConnections(result, state, con);
            return invoiceItem;
        } catch (SQLException e) {
            throw new QueryError(e.toString());
        } finally {
        }

    }

    @Override
    public void deleteInvoiceItem(InvoiceItem iItem){
        try {
            getInstance();
            String sql = "DELETE FROM invoice_items WHERE invoice = ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setInt(1, iItem.getInvoiceId());
            prpState.executeUpdate();

            //closeConnections(prpState);
            closeConnections(result, state, prpState, con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            closeConnections(result, state, con);
        }
    }
}
