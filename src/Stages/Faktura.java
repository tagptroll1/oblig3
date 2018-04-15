package Stages;

import Code.Invoice;
import Controllers.FakturaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Faktura{

    public void display(Invoice faktura) throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Ui/faktura.fxml"));
        Parent root = loader.load();
        FakturaController controller = loader.getController();

        window.setTitle("Faktura");
        window.setScene(new Scene(root, 600, 900));

        controller.setValues(faktura);

        window.show();
    }

}
