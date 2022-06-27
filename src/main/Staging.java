package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**This class creates a stage for windows. */
public class Staging {
     Stage stage;
     Parent scene;

    /**  Used to change between windows.
     * @param event used to create stage
     * @param location where it will open the fxml document
     * @throws IOException used to create stage
     */
    public  void stagingMethod(ActionEvent event, String location) throws IOException { // this is used to create the windows
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(location));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
