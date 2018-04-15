package DAOs;

import Code.InvoiceItem;
import Errors.InsertionError;
import Errors.QueryError;
import Interface.InvoiceItemDAOIF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Code.Utility.checkTable;

public class InvoiceItemDAO implements InvoiceItemDAOIF {

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
        if (!(iItem.getInvoice() != 0 && iItem.getProduct() != 0)){
            throw new InsertionError("Invoice item insertion error: one or both ids in invoice item are invalid");
        }
        state.setInt(1, iItem.getInvoice());
        state.setInt(2, iItem.getProduct());
        state.execute();
        System.out.println("Added: invoice item to db");
    }

    @Override
    public InvoiceItem getInvoiceItemByInvoiceId(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();

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

        String sql = "SELECT * FROM invoice_items WHERE product="+id;
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
    public void deleteInvoiceItem(InvoiceItem iItem) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        String sql = "DELETE FROM invoice_items WHERE invoice = ?;";
        PreparedStatement state = con.prepareStatement(sql);
        state.setInt(1, iItem.getInvoice());
        state.executeUpdate();
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() throws SQLException {
        //TODO return observableList

        getInstance();

        List<InvoiceItem> iItems = new ArrayList<>();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM invoice_items ORDER BY invoice");

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