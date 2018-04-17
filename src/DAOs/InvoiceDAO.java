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
    private static PreparedStatement prpState;
    private static Connection con = ConnectionDAO.getInstance().getConnection();


    private InvoiceDAO(){}

    public static InvoiceDAO getInstance(String prpString) throws SQLException {
        if (IDAO == null){
            IDAO = new InvoiceDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();
        if (prpString != null && (prpState == null || prpState.isClosed())) prpState = con.prepareStatement(prpString);

        return IDAO;
    }

    public static InvoiceDAO getInstance() throws SQLException {
        getInstance(null);
        return IDAO;
    }

    @Override
    public void addInvoice(Invoice invoice){
        try {
            String sql = "INSERT OR REPLACE INTO invoice values(?,?,?);";
            getInstance(sql);

            if (invoice.getId() != -1) prpState.setInt(1, invoice.getId());
            prpState.setInt(2, invoice.getCustomerId());
            prpState.setString(3, invoice.getDue());
            prpState.execute();
            System.out.println("Added: Invoice to db");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, prpState, con);
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
                items.add(ProductDAO.getInstance().getProductById(itemResult.getInt(1)));
            }

            itemResult.close();
            itemState.close();

            return new Invoice(
                    result.getInt("invoice_id"),
                    result.getInt("customer"),
                    result.getString("dato"),
                    items
            );
        } catch (SQLException e) {
            throw new QueryError(e.toString());
        } finally {
            closeConnections(result, state, prpState, con);
        }

    }

    @Override
    public void deleteInvoice(Invoice invoice){
        try {
            String sql = "DELETE FROM invoice WHERE invoice_id = ?;";
            getInstance(sql);

            prpState.setInt(1, invoice.getId());
            prpState.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, prpState, con);
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
            return voices;
        } catch (SQLException e) {
            return voices;
        } finally {
            closeConnections(result, state, prpState, con);
        }
    }
}
