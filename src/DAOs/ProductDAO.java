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
    private static Connection con = ConnectionDAO.getInstance().getConnection();


    private ProductDAO(){}

    /**
     * Get's the instance of self, makes sure connection is open and state is open
     * @return returns self
     * @throws SQLException
     */
    public static ProductDAO getInstance() throws SQLException {
        if(PDAO == null){
            PDAO = new ProductDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return PDAO;
    }

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param item object to be broken down
     */
    @Override
    public void addProduct(Item item){
        try {
            getInstance();
            String sql = "INSERT OR REPLACE INTO product values(?,?,?,?,?);";
            PreparedStatement prpState = con.prepareStatement(sql);

            if (item.getId() != -1) prpState.setInt(1, item.getId());
            prpState.setString(2, item.getName());
            prpState.setString(3, item.getDescription());
            prpState.setDouble(4, item.getPrice());
            prpState.setInt(5, item.getCategoryId());
            prpState.execute();

            System.out.println("Added: "+item.getName());
            closeConnections(prpState);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return an item object
     */
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
            closeConnections(result, state, con);
        }
    }

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param item object that holds id
     */
    @Override
    public void deleteProduct(Item item){
        try {
            getInstance();
            String sql = "DELETE FROM product WHERE product_id = ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setInt(1, item.getId());
            prpState.executeUpdate();

            closeConnections(prpState);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
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
            closeConnections(result, state, con);
        }
    }
}
