package DAOs;

import Code.Invoice;
import Code.Item;
import Errors.QueryError;
import Interface.InvoiceDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

import static Code.Utility.checkTable;

public class InvoiceDAO implements InvoiceDAOIF {
    //TODO Open/close connection correctly, fix?
    private static InvoiceDAO IDAO = null;

    private InvoiceDAO(){}

    public static InvoiceDAO getInstance(){
        if (IDAO == null){
            IDAO = new InvoiceDAO();
        }
        return IDAO;
    }

    @Override
    public void addInvoice(Invoice invoice) throws SQLException {
        getInstance();
        ConnectionDAO CDAO = ConnectionDAO.getInstance();
        Connection con = CDAO.getConnection();
        if(!checkTable("invoice")) return;

        String sql = "INSERT OR REPLACE INTO invoice values(?,?,?);";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        if (invoice.getId() != -1) state.setInt(1, invoice.getId());
        state.setInt(2, invoice.getCustomerId());
        state.setString(3, invoice.getDue());
        state.execute();
        System.out.println("Added: Invoice to db");
    }

    @Override
    public Invoice getInvoiceById(int id) throws SQLException {
        getInstance();
        ConnectionDAO CDAO = ConnectionDAO.getInstance();
        Connection con = CDAO.getConnection();
        Statement state = con.createStatement();

        String sql = "SELECT * FROM invoice WHERE invoice_id="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within invoice table with id: "+id);

        String sql2 = "SELECT a.customer_id FROM invoice i INNER JOIN customer a ON a.customer_id=i.customer WHERE i.invoice_id="+id;
        Statement state2 = con.createStatement();
        ResultSet result2 = state2.executeQuery(sql2);

        ArrayList<Item> items = new ArrayList<>();

        String qItems = "SELECT product.product_id "
                + "FROM invoice "
                + "INNER JOIN invoice_items "
                + "INNER JOIN product "
                + "ON invoice.invoice_id=invoice_items.invoice "
                + "AND invoice_items.product=product.product_id "
                + "WHERE invoice_id="+id;
        Statement itemState = con.createStatement();
        ResultSet itemResult = itemState.executeQuery(qItems);
        con.close();

        while(itemResult.next()){
            items.add(ProductDAO.getInstance().getProductById(itemResult.getInt(1)));
        }

        return new Invoice(
                result.getInt("invoice_id"),
                result2.getInt("customer_id"),
                result.getString("dato"),
                items
        );


    }

    @Override
    public void deleteInvoice(Invoice invoice) throws SQLException {
        getInstance();
        ConnectionDAO CDAO = ConnectionDAO.getInstance();
        Connection con = CDAO.getConnection();

        String sql = "DELETE FROM invoice WHERE invoice_id = ?;";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        state.setInt(1, invoice.getId());
        state.executeUpdate();
    }

    @Override
    public ObservableList<Invoice> getAllInvoices() throws SQLException {
        ObservableList<Invoice> voices = FXCollections.observableArrayList();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM invoice ORDER BY invoice_id");
        con.close();

        while(rs.next()){
            Invoice in = new Invoice(
                    rs.getInt("invoice_id"),
                    rs.getInt("customer"),
                    rs.getString("dato")
            );
            voices.add(in);
        }
        return voices;
    }
}
