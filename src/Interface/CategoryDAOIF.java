package Interface;

import Code.Category;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAOIF {
    /**
     * @param id to validate
     * @return true if theres a row with this id
     */
    boolean validateId(int id);

    /**
     * Breaks down an object into fields ands stores them in the database
     * @param category object to be broken down
     */
    void addCateory(Category category);

    /**
     * Queries database for id, fetches row with id in given database
     * Creates an Object of related class with rows from query
     * @param id id to be querried
     * @return a category object
     */
    Category getCategoryById(int id);

    /**
     * Fetches id from object parameter, queries db with id and deletes row given
     * @param category object that holds id
     */
    void deleteCategory(Category category);

    /**
     * Querries all rows in said category, iterates over every row and fetches all columns
     * uses columns to create new Objects and stores them in an observablelist
     * @return An observable list used for db display/iterations
     */
    ObservableList<Category> getAllCategories();
}
