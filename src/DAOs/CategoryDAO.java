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
            boolean value = result.getRow() != 0;
            closeConnections(result, state, con);
            return value;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        } finally {
        }
    }

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
            //closeConnections(prpState);
            closeConnections(result, state, prpState, con);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public Category getCategoryById(int id){
        try {
            getInstance();
            String sql = "SELECT * FROM category WHERE category_id=" + id;
            result = state.executeQuery(sql);
            // sjekk at den fikk noe
            result.next();
            if (result.getRow() == 0) throw new QueryError("No result found within customer table with id: " + id);
            Category category = new Category(
                    result.getInt("category_id"),
                    result.getString("category_name")
            );
            closeConnections(result, state, con);
            return category;
        } catch (SQLException e){
            throw new QueryError("No result found within customer table with id: " + id);
        } finally {
        }
    }

    @Override
    public void deleteCategory(Category category){
        try {
            getInstance();
            String sql = "DELETE FROM category WHERE category_name LIKE ?;";
            PreparedStatement prpState = con.prepareStatement(sql);

            prpState.setString(1, category.getName());
            prpState.executeUpdate();

            //closeConnections(prpState);
            closeConnections(result, state, prpState, con);
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
        }
    }

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
            closeConnections(result, state, con);
            return categories;
        } catch (SQLException e){
            return categories;
        } finally {
        }
    }
}
