package Stages;

import Code.Category;
import Controllers.AddCategoryController;
import Controllers.dbViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class AddCategoryWindow {
    /**
     * Opens UI for adding categories to db
     * @param category categories to be added
     * @param db dbcontroller to change table according
     * @param title Title of the UI
     * @throws IOException
     */
    public void display(Category category, dbViewerController db, String title) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/addCategory.fxml"));
        Parent root = loader.load();
        AddCategoryController controller = loader.getController();

        window.initModality(Modality.APPLICATION_MODAL);

        controller.setOptionalId(category, db, title);
        window.setTitle("Add a category");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.showAndWait();
    }

    /**
     * Overload function for a simple insertian window
     * @param title title of the window
     * @throws IOException
     */
    public void display(String title) throws IOException {
        display(null, null, title);
    }
}



