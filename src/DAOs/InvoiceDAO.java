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

    public static InvoiceDAO getInstance() throws SQLException {
        if (IDAO == null){
            IDAO = new InvoiceDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return IDAO;
    }

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
            //closeConnections(prpState);
            closeConnections(result, state, prpState, con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }

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

            String qItems = "SELECT product.product_id "
                    + "FROM invoice "
                    + "INNER JOIN invoice_items "
                    + "INNER JOIN product "
                    + "ON invoice.invoice_id=invoice_items.invoice "
                    + "AND invoice_items.product=product.product_id "
                    + "WHERE invoice_id="+id;
            Statement itemState = con.createStatement();
            ResultSet itemResult = itemState.executeQuery(qItems);

            while(itemResult.next()){
                int productID = itemResult.getInt("product_id");
                Item product = ProductDAO.getInstance().getProductById(productID);
                items.add(product);
            }

            Invoice invoice = new Invoice(
                    result.getInt("invoice_id"),
                    result.getInt("customer"),
                    result.getString("dato"),
                    items
            );
            closeConnections(itemResult);
            closeConnections(itemState);
            closeConnections(result, state, con);
            return invoice;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
            //throw new QueryError(e.toString());
        } finally {
        }

    }

    @Override
    public void deleteInvoice(Invoice invoice){
        try {
            getInstance();
            String sql = "DELETE FROM invoice WHERE invoice_id = ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setInt(1, invoice.getId());
            prpState.executeUpdate();

            //closeConnections(prpState);
            closeConnections(result, state, prpState, con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }

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
            closeConnections(result, state, con);
            return voices;
        } catch (SQLException e) {
            return voices;
        } finally {
        }
    }
}
