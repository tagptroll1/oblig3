package DAOs;

import Code.Item;
import Errors.QueryError;
import Interface.ProductDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Code.Utility.checkTable;

public class ProductDAO implements ProductDAOIF {
    //TODO Open/close connection correctly
    private static ProductDAO PDAO = null;

    private ProductDAO(){}

    public static ProductDAO getInstance(){
        if(PDAO == null){
            PDAO = new ProductDAO();
        }
        return PDAO;
    }

    @Override
    public void addProduct(Item item) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        if (!checkTable("product")) return;

        String sql = "INSERT OR REPLACE INTO product values(?,?,?,?,?);";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        if (item.getId() != -1) state.setInt(1, item.getId());
        state.setString(2, item.getName());
        state.setString(3, item.getDescription());
        state.setDouble(4, item.getPrice());
        state.setInt(5, item.getCategoryId());
        state.execute();
        System.out.println("Added: "+item.getName());
    }

    @Override
    public Item getProductById(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();
        con.close();

        String sql = "SELECT * FROM product WHERE product_id="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within product table with id: "+id);

        Item item = new Item(
                result.getInt("product_id"),
                result.getString("product_name"),
                result.getString("description"),
                result.getDouble("price"),
                result.getInt("category")
        );
        return item;
    }

    @Override
    public void deleteProduct(Item item) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        String sql = "DELETE FROM product WHERE product_id = ?;";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        state.setInt(1, item.getId());
        state.executeUpdate();
    }

    @Override
    public ObservableList<Item> getAllProducts() throws SQLException {
        getInstance();

        ObservableList<Item> products = FXCollections.observableArrayList();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM product ORDER BY product_id");
        con.close();
        
        while(rs.next()){
            Item item = new Item(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("category")
            );
            products.add(item);
        }
        return products;
    }
}
