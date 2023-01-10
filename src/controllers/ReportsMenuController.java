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

public class ReportsMenuController implements Initializable {

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void NavToCustomerAppointmentReport(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/customer-appointment-report.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/customer-appointment-report.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }

    public void NavToContactAppointmentReport(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/contact-appointment-report.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/contact-appointment-report.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }

    public void NavToContactSchedule(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/contact-schedule-report.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/contact-schedule-report.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }

    public void ShowLoginLogPath(ActionEvent actionEvent) {
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
