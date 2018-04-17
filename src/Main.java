import Code.Address;
import Code.Customer;
import Code.Invoice;
import DAOs.AddressDAO;
import DAOs.ConnectionDAO;
import DAOs.CustomerDAO;
import DAOs.InvoiceDAO;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Ui/startpage.xml.fxml"));
        primaryStage.setTitle("Start Menu");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);

        ConnectionDAO.getInstance().closeConnection();

    }

}
