package Stages;

import Code.Invoice;
import Controllers.FakturaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Faktura{
    /**
     * Displays a faktura/invoice
     * controller sets values
     * @param faktura invoice to be broken down for display
     * @throws IOException
     * @throws SQLException
     */
    public void display(Invoice faktura) throws IOException, SQLException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/faktura2.fxml"));
        Parent root = loader.load();
        FakturaController controller = loader.getController();

        window.setTitle("Faktura");
        window.setScene(new Scene(root, 600, 800));

        controller.setValues(faktura);

        window.show();
    }

}
