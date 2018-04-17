package Controllers;

import Code.Item;
import DAOs.ProductDAO;
import Errors.InsertionError;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddProductController {
    @FXML private TextField productName;
    @FXML private TextField productDesc;
    @FXML private TextField productPrice;
    @FXML private TextField productCategory;
    @FXML private Label productIdLabel, productTitle;
    private dbViewerController database;
    private int id;

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
        }

        if((name!=null || !name.trim().isEmpty())){
            Item item = new Item(
                    id,
                    name,
                    desc,
                    price,
                    categoryID
            );
            ProductDAO.getInstance().addProduct(item);
            Stage stage = (Stage) productName.getScene().getWindow();
            if (this.database!=null) this.database.dbGoProduct();
            stage.close();
        } else {
            System.out.println("something happen!");
        }
    }

    public void setOptionalId(Item product, dbViewerController db, String title){
        if (product==null){
            productIdLabel.setText("Auto ID");
            this.id = -1;
        } else {
            productIdLabel.setText("Id: "+product.getId());
            productName.setText(product.getName());
            productDesc.setText(product.getDescription());
            productPrice.setText(product.getPrice()+"");
            productCategory.setText(product.getCategoryId()+"");
            this.id = product.getId();
        }
        productTitle.setText(title);
        this.database = db;
    }
}
