package DAOs;

import Code.Category;
import Errors.QueryError;
import Interface.CategoryDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import static Code.Utility.closeConnections;

public class CategoryDAO implements CategoryDAOIF {
    private static CategoryDAO CDAO = null;
    private static ResultSet result;
    private static Statement state;
    private static Connection con = ConnectionDAO.getInstance().getConnection();

    private CategoryDAO(){}

    /**
     * Get's the instance of self, makes sure connection is open and state is open
     * @return returns self
     * @throws SQLException
     */
    public static CategoryDAO getInstance() throws SQLException{
        if (CDAO == null){
            CDAO = new CategoryDAO();
        }

        // Open and prepare resultset, statements and connection
        if (con.isClosed()) con = ConnectionDAO.getInstance().getConnection();
        if (state == null || state.isClosed()) state = con.createStatement();

        return CDAO;
    }


    /**
     *
     * @param id to validate
     * @return true if theres a row with this id
     */
    public boolean validateId(int id) {
        try {
            getInstance();
            String sql = "SELECT category_id FROM category WHERE category_id=" + id;
            result = state.executeQuery(sql);

            result.next();
            return result.getRow() != 0;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param category object to be broken down
     */
    @Override
    public void addCateory(Category category){
        try {
            getInstance();
            String sql = "INSERT OR REPLACE INTO category values(?,?);";
            PreparedStatement prpState = con.prepareStatement(sql);

            if (category.getId() != -1) prpState.setInt(1, category.getId());
            prpState.setString(2, category.getName());
            prpState.execute();

            System.out.println("Added: " + category.getName());
            closeConnections(prpState);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return
     */
    @Override
    public Category getCategoryById(int id){
        try {
            getInstance();
            String sql = "SELECT * FROM category WHERE category_id=" + id;
            result = state.executeQuery(sql);
            // sjekk at den fikk noe
            result.next();
            if (result.getRow() == 0) throw new QueryError("No result found within customer table with id: " + id);
            return new Category(
                    result.getInt("category_id"),
                    result.getString("category_name")
            );
        } catch (SQLException e){
            throw new QueryError("No result found within customer table with id: " + id);
        } finally {
            closeConnections(result, state, con);
        }
    }

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param category object that holds id
     */
    @Override
    public void deleteCategory(Category category){
        try {
            getInstance();
            String sql = "DELETE FROM category WHERE category_name LIKE ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setString(1, category.getName());
            prpState.executeUpdate();

            closeConnections(prpState);
        } catch (SQLException e){
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
    public ObservableList<Category> getAllCategories(){

        ObservableList<Category> categories = FXCollections.observableArrayList();
        try {
            getInstance();
            result = state.executeQuery("SELECT * FROM category ORDER BY category_id");

            while (result.next()) {
                Category category = new Category(
                        result.getInt("category_id"),
                        result.getString("category_name")
                );
                categories.add(category);
            }
            return categories;
        } catch (SQLException e){
            return categories;
        } finally {
            closeConnections(result, state, con);
        }
    }
}
