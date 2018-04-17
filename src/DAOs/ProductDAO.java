package DAOs;

import Code.Item;
import Errors.QueryError;
import Interface.ProductDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Code.Utility.closeConnections;


public class ProductDAO implements ProductDAOIF {
    private static ProductDAO PDAO = null;
    private static ResultSet result;
    private static Statement state;
    private static PreparedStatement prpState;
    private static Connection con = ConnectionDAO.getInstance().getConnection();


    private ProductDAO(){}

    public static ProductDAO getInstance(String prpString) throws SQLException {
        if(PDAO == null){
            PDAO = new ProductDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();
        if (prpString != null && (prpState == null || prpState.isClosed())) prpState = con.prepareStatement(prpString);

        return PDAO;
    }

    public static ProductDAO getInstance() throws SQLException {
        getInstance(null);
        return PDAO;
    }

    @Override
    public void addProduct(Item item){
        try {
            String sql = "INSERT OR REPLACE INTO product values(?,?,?,?,?);";
            getInstance(sql);

            if (item.getId() != -1) prpState.setInt(1, item.getId());
            prpState.setString(2, item.getName());
            prpState.setString(3, item.getDescription());
            prpState.setDouble(4, item.getPrice());
            prpState.setInt(5, item.getCategoryId());
            prpState.execute();
            System.out.println("Added: "+item.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, prpState, con);
        }
    }

    @Override
    public Item getProductById(int id){
        try {
            String sql = "SELECT * FROM product WHERE product_id="+id;
            getInstance();
            result = state.executeQuery(sql);

            // sjekk at den fikk noe
            result.next();
            if (result.getRow()==0) throw new QueryError("No result found within product table with id: "+id);

            return new Item(
                    result.getInt("product_id"),
                    result.getString("product_name"),
                    result.getString("description"),
                    result.getDouble("price"),
                    result.getInt("category")
            );
        } catch (SQLException e) {
            throw new QueryError(e.toString());
        } finally {
            closeConnections(result, state, prpState, con);
        }
    }

    @Override
    public void deleteProduct(Item item){
        try {
            String sql = "DELETE FROM product WHERE product_id = ?;";
            getInstance(sql);

            prpState.setInt(1, item.getId());
            prpState.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, prpState, con);
        }
    }

    @Override
    public ObservableList<Item> getAllProducts(){
        ObservableList<Item> products = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM product ORDER BY product_id";
            getInstance();
            result = state.executeQuery(sql);

            while(result.next()){
                Item item = new Item(
                        result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getInt("category")
                );
                products.add(item);
            }
            return products;
        } catch (SQLException e) {
            return products;
        } finally {
            closeConnections(result, state, prpState, con);
        }
    }
}
