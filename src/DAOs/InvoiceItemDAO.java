package DAOs;

import Code.InvoiceItem;
import Errors.InsertionError;
import Errors.QueryError;
import Interface.InvoiceItemDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Code.Utility.checkTable;

public class InvoiceItemDAO implements InvoiceItemDAOIF {
    //TODO Open/close connection correctly, fix?
    private static InvoiceItemDAO IIDAO = null;

    private InvoiceItemDAO(){};

    public static InvoiceItemDAO getInstance(){
        if (IIDAO == null){
            IIDAO = new InvoiceItemDAO();
        }
        return IIDAO;
    }

    @Override
    public void addInvoiceItem(InvoiceItem iItem) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        if (!checkTable("invoice_items")) return;

        String sql = "INSERT OR REPLACE INTO invoice_items values(?,?);";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        if (!(iItem.getInvoiceId() != 0 && iItem.getProductId() != 0)){
            throw new InsertionError("Invoice item insertion error: one or both ids in invoice item are invalid");
        }
        state.setInt(1, iItem.getInvoiceId());
        state.setInt(2, iItem.getProductId());
        state.execute();
        System.out.println("Added: invoice item to db");
    }

    @Override
    public InvoiceItem getInvoiceItemByInvoiceId(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();
        con.close();

        String sql = "SELECT * FROM invoice_items WHERE invoice="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within category table with id: "+id);

        InvoiceItem iItem = new InvoiceItem(
                result.getInt("invoice"),
                result.getInt("product")
        );

        return iItem;
    }

    @Override
    public InvoiceItem getInvoiceItemByProductId(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();
        con.close();

        String sql = "SELECT * FROM invoice_items WHERE product="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within category table with id: "+id);

        return new InvoiceItem(
                result.getInt("invoice"),
                result.getInt("product")
        );

    }

    @Override
    public void deleteInvoiceItem(InvoiceItem iItem) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        String sql = "DELETE FROM invoice_items WHERE invoice = ?;";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        state.setInt(1, iItem.getInvoiceId());
        state.executeUpdate();
    }

    @Override
    public ObservableList<InvoiceItem> getAllInvoiceItems() throws SQLException {
        getInstance();

        ObservableList<InvoiceItem> iItems = FXCollections.observableArrayList();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM invoice_items ORDER BY invoice");
        con.close();

        while(rs.next()){
            InvoiceItem iItem = new InvoiceItem(
                    rs.getInt("invoice"),
                    rs.getInt("product")
            );
            iItems.add(iItem);
        }
        return iItems;
    }
}
