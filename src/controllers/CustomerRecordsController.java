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

public class CustomerRecordsController implements Initializable {
    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void CreateNewCustomer(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/new-customer.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/new-customer.fxml"));
        Stage stage = new Stage();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.show();
    }

    public void EditSelectedCustomer(ActionEvent actionEvent) {
    }

    public void DeleteSelectedCustomer(ActionEvent actionEvent) {
    }

    public void SaveChanges(ActionEvent actionEvent) {
    }

    public void CancelChanges(ActionEvent actionEvent) {
    }

    public void BackToMainMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/main-menu.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/main-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }
}
