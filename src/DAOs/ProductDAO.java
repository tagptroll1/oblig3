package DAOs;

import Code.Category;
import Code.Item;
import Errors.QueryError;
import Interface.ProductDAOIF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Code.Utility.checkTable;

public class ProductDAO implements ProductDAOIF {

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
        if (item.getId() != -1) state.setInt(1, item.getId());
        state.setString(2, item.getName());
        state.setString(3, item.getDescription());
        state.setDouble(4, item.getPrice());
        state.setInt(5, item.getCategory().getId());
        state.execute();
        System.out.println("Added: "+item.getName());
    }

    @Override
    public Item getProductById(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();

        String sql = "SELECT * FROM product WHERE product_id="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within category table with id: "+id);

        Category category = CategoryDAO.getInstance().getCategoryById(result.getInt("category"));
        Item item = new Item(
                result.getInt("product_id"),
                result.getString("product_name"),
                result.getString("description"),
                result.getDouble("price"),
                category
        );
        return item;
    }

    @Override
    public void deleteProduct(Item item) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        String sql = "DELETE FROM product WHERE product_id = ?;";
        PreparedStatement state = con.prepareStatement(sql);
        state.setInt(1, item.getId());
        state.executeUpdate();
    }

    @Override
    public List<Item> getAllProducts() throws SQLException {
        //TODO return observableList

        getInstance();

        List<Item> products = new ArrayList<>();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM product ORDER BY product_id");

        while(rs.next()){
            Category category = CategoryDAO.getInstance().getCategoryById(rs.getInt("category"));

            Item item = new Item(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    category
            );
            products.add(item);
        }
        return products;
    }
}
