package Stages;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationWindow {
    /**
     * Displays a custom alert window to prompt the user for a yes/no
     * used for deletions currently
     * @return a boolean to confirm deletion or not
     */
    public boolean display(){
            SimpleBooleanProperty deleteIt = new SimpleBooleanProperty(false);
            Stage window = new Stage();

            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(250);
            window.setWidth(250);
            window.setTitle("Delete entry");

            Label label = new Label();
            label.setText("Delete this entry?");
            Button yesButton = new Button("Yes");
            yesButton.setOnAction(e -> {
                 {
                     deleteIt.set(true);
                     window.close();
                }
            });

            Button noButton = new Button("No");
            noButton.setOnAction(e -> window.close());

            VBox layout = new VBox(10);
            HBox buttonLayout = new HBox(10);
            buttonLayout.getChildren().addAll(yesButton, noButton);
            buttonLayout.setAlignment(Pos.CENTER);
            layout.getChildren().addAll(label, buttonLayout);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.setResizable(false);
            window.showAndWait();
            return deleteIt.get();
        }
}
