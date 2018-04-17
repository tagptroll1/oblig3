package Controllers;

import Code.Category;
import DAOs.CategoryDAO;
import Errors.InsertionError;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddCategoryController {
    @FXML private TextField categoryName;
    @FXML private Label categoryTitle;
    @FXML private Label categoryIdLabel;
    private dbViewerController database;
    private int id;

    public void addCategory() throws SQLException {
        String name = categoryName.getText();
        if(name!=null && !name.trim().isEmpty()){
            Category category = new Category(
                    id,
                    name
            );
            CategoryDAO.getInstance().addCateory(category);
            Stage stage = (Stage) categoryName.getScene().getWindow();
            if (database!=null) database.dbGoCategory();
            stage.close();
        } else {
            throw new InsertionError("Name field that is required in a new category is empty!");
        }

    }

    public void setOptionalId(Category category, dbViewerController db, String title){
        if (category==null){
            categoryIdLabel.setText("Auto ID");
            id = -1;
        } else {
            categoryIdLabel.setText("Id: "+category.getId());
            categoryName.setText(category.getName());
            this.id = category.getId();
        }
        categoryTitle.setText(title);
        this.database = db;
    }
}
