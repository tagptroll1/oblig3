package DAOs;

import Code.Category;
import Errors.QueryError;
import Interface.CategoryDAOIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import static Code.Utility.checkTable;

public class CategoryDAO implements CategoryDAOIF {
    //TODO Open/close connection correctly, fix?
    private static CategoryDAO CDAO = null;

    private CategoryDAO(){}

    public static CategoryDAO getInstance(){
        if (CDAO == null){
            CDAO = new CategoryDAO();
        }
        return CDAO;
    }

    public boolean validateId(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();


        Statement state = con.createStatement();

        String sql = "SELECT category_id FROM category WHERE category_id=" + id;
        ResultSet result = state.executeQuery(sql);
        con.close();

        result.next();
        if (result.getRow() != 0) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void addCateory(Category category) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        if (!checkTable("category")) return;

        String sql = "INSERT OR REPLACE INTO category values(?,?);";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        if (category.getId() != -1) state.setInt(1, category.getId());
        state.setString(2, category.getName());
        state.execute();
        System.out.println("Added: "+category.getName());
    }

    @Override
    public Category getCategoryById(int id) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        Statement state = con.createStatement();
        con.close();

        String sql = "SELECT * FROM category WHERE category_id="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within customer table with id: "+id);
        return new Category(
                result.getInt("category_id"),
                result.getString("category_name")
        );
    }

    @Override
    public void deleteCategory(Category category) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        String sql = "DELETE FROM category WHERE category_name LIKE ?;";
        PreparedStatement state = con.prepareStatement(sql);
        con.close();

        state.setString(1, category.getName());
        state.executeUpdate();
    }

    @Override
    public ObservableList<Category> getAllCategories() throws SQLException {
        getInstance();

        ObservableList<Category> categories = FXCollections.observableArrayList();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM category ORDER BY category_id");
        con.close();

        while(rs.next()){
            Category category = new Category(
                    rs.getInt("category_id"),
                    rs.getString("category_name")
            );
            categories.add(category);
        }
        return categories;
    }
}
