package Controllers;

import Code.Category;
import Code.Item;
import DAOs.CategoryDAO;
import DAOs.ProductDAO;
import Errors.InsertionError;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddProductController {
    @FXML private TextField productName;
    @FXML private TextField productDesc;
    @FXML private TextField productPrice;
    @FXML private TextField productCategory;


    public void addProduct() throws SQLException {
        String name = productName.getText();
        String desc = productDesc.getText();
        String priceString = productPrice.getText();
        String categoryIdString = productCategory.getText();
        // Checks for number related fields before applying ParseInt/double
        if (priceString == null || priceString.trim().isEmpty()){
            throw new InsertionError("Price field cannot be empty");
        }
        if (categoryIdString == null || categoryIdString.trim().isEmpty()){
            throw new InsertionError("Category Id cannot be empty");
        }
        double price = Double.parseDouble(priceString);
        int categoryID = Integer.parseInt(categoryIdString);

        if (categoryID == 0){
            throw new InsertionError("Category Id cannot be 0");
        } else {
            if (CategoryDAO.getInstance().validateId(categoryID)){
                throw new InsertionError("Category with this ID doesn't exist");
            }
        }

        if((name!=null || !name.trim().isEmpty()) && (desc != null || !desc.trim().isEmpty())){
            Category category = CategoryDAO.getInstance().getCategoryById(categoryID);
            Item item = new Item(
                    -1,
                    name,
                    desc,
                    price,
                    category
            );
            ProductDAO.getInstance().addProduct(item);
            Stage stage = (Stage) productName.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("something happen!");
        }

    }
}
