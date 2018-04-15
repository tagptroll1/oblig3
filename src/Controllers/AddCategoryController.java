package Controllers;

import Code.Category;
import DAOs.CategoryDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddCategoryController {
    @FXML private TextField categoryName;

    public void addCategory() throws SQLException {
        String name = categoryName.getText();
        if(name!=null){
            Category category = new Category(
                    -1,
                    name
            );
            CategoryDAO.getInstance().addCateory(category);
            Stage stage = (Stage) categoryName.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("something happen!");
        }

    }
}
