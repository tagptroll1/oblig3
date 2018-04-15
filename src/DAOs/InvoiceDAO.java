package DAOs;

import Code.Customer;
import Code.Invoice;
import Errors.QueryError;
import Interface.InvoiceDAOIF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Code.Utility.checkTable;

public class InvoiceDAO implements InvoiceDAOIF {
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
        if (invoice.getId() != -1) state.setInt(1, invoice.getId());
        state.setInt(2, invoice.getCustomer().getId());
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

        Invoice invoice = new Invoice();
        invoice.setId(result.getInt("invoice_id"));
        invoice.setDue(result.getString("dato"));

        String sql2 = "SELECT a.customer_id FROM invoice i INNER JOIN customer a ON a.customer_id=i.customer WHERE i.invoice_id="+id;
        ResultSet result2 = state.executeQuery(sql2);
        CustomerDAO UDAO = CustomerDAO.getInstance();
        Customer customer = UDAO.getUserById(result2.getInt("customer_id"));
        invoice.setCustomer(customer);
        return invoice;
    }

    @Override
    public void deleteInvoice(Invoice invoice) throws SQLException {
        getInstance();
        ConnectionDAO CDAO = ConnectionDAO.getInstance();
        Connection con = CDAO.getConnection();

        String sql = "DELETE FROM invoice WHERE invoice_id = ?;";
        PreparedStatement state = con.prepareStatement(sql);
        state.setInt(1, invoice.getId());
        state.executeUpdate();
    }

    @Override
    public List<Invoice> getAllInvoices() throws SQLException {
        //TODO return observableList
        //TODO fix this, finner ikke "invoice_id"
        List<Invoice> voices = new ArrayList<>();
        CustomerDAO UDAO = CustomerDAO.getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM invoice ORDER BY invoice_id");

        while(rs.next()){
            Invoice in = new Invoice(
                    rs.getInt("invoice_id"),
                    new Customer(),
                    rs.getString("dato")
            );
            System.out.println(rs.getInt(1));

            String sql2 = "SELECT a.customer_id FROM invoice i INNER JOIN customer a ON a.customer_id=i.customer WHERE i.invoice_id="+rs.getInt("invoice_id");
            ResultSet result2 = state.executeQuery(sql2);
            Customer customer = UDAO.getUserById(result2.getInt("customer_id"));
            in.setCustomer(customer);
            voices.add(in);
        }
        return voices;
    }
}
