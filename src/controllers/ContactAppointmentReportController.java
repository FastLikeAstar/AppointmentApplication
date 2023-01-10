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

public class ContactAppointmentReportController implements Initializable {
    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void BackToReportsMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/reports-menu.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/reports-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }
}
