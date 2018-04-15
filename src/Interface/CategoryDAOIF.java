package Interface;

import Code.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAOIF {
    void addCateory(Category category) throws SQLException;
    Category getCategoryById(int id) throws SQLException;
    void deleteCategory(Category category) throws SQLException;
    //TODO return observableList
    List<Category> getAllCategories() throws SQLException;
}
