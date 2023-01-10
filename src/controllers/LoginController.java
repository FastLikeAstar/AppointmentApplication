package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void AttemptLogin(ActionEvent actionEvent) throws IOException {
        boolean successful = false;

        successful = true;
        if (successful)
        {
            Scene partScene;
            FXMLLoader controllerLoader = new FXMLLoader();
            controllerLoader.setLocation(getClass().getResource("/main-menu.fxml"));
            Parent tempParent = controllerLoader.load(Main.class.getResource("/main-menu.fxml"));
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            partScene = new Scene(tempParent);
            stage.setScene(partScene);
        }
    }
}
