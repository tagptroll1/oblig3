package DAOs;

import Code.Category;
import Errors.QueryError;
import Interface.CategoryDAOIF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Code.Utility.checkTable;

public class CategoryDAO implements CategoryDAOIF {

    private static CategoryDAO CDAO = null;

    private CategoryDAO(){}

    public static CategoryDAO getInstance(){
        if (CDAO == null){
            CDAO = new CategoryDAO();
        }
        return CDAO;
    }

    public boolean validateId(int id) {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        try {
            Statement state = con.createStatement();

            String sql = "SELECT category_id FROM category WHERE category_id=" + id;
            ResultSet result = state.executeQuery(sql);

            result.next();
            if (result.getRow() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e){
            return false;
        }
    }

    @Override
    public void addCateory(Category category) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();
        if (!checkTable("category")) return;

        String sql = "INSERT OR REPLACE INTO category values(?,?);";
        PreparedStatement state = con.prepareStatement(sql);
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

        String sql = "SELECT * FROM category WHERE category_id="+id;
        ResultSet result = state.executeQuery(sql);
        // sjekk at den fikk noe
        result.next();
        if (result.getRow()==0) throw new QueryError("No result found within customer table with id: "+id);

        Category category = new Category();
        category.setId(result.getInt("category_id"));
        category.setName(result.getString("category_name"));
        return category;
    }

    @Override
    public void deleteCategory(Category category) throws SQLException {
        getInstance();
        Connection con = ConnectionDAO.getInstance().getConnection();

        String sql = "DELETE FROM category WHERE category_name LIKE ?;";
        PreparedStatement state = con.prepareStatement(sql);
        state.setString(1, category.getName());
        state.executeUpdate();
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        //TODO return observableList
        getInstance();

        List<Category> categories = new ArrayList<>();
        Connection con = ConnectionDAO.getInstance().getConnection();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM customer ORDER BY customer_id");

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
