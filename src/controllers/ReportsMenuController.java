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
     * Initializes the reports menus.
     * @param url JavaFX param.
     * @param resourceBundle JavaFX param.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    /**
     * Navigates the user to Customer Appointments Report.
     * @param actionEvent fired from button clicked.
     * @throws IOException from loading fxml file.
     */
    public void NavToCustomerAppointmentReport(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-appointment-report.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }

    /**
     * Navigates the user to Customers Acquired Report.
     * @param actionEvent fired from button clicked.
     * @throws IOException from loading fxml file.
     */
    public void NavToCustomersAcquiredReport(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = FXMLLoader.load(Main.class.getResource("/customers-acquired-report.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    /**
     * Navigates the user to Contact Schedule Report.
     * @param actionEvent fired from button clicked.
     * @throws IOException from loading fxml file.
     */
    public void NavToContactSchedule(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = FXMLLoader.load(Main.class.getResource("/contact-schedule-report.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }


    /**
     * Navigates the user to back to the Main Menu.
     * @param actionEvent fired from button clicked.
     * @throws IOException from loading fxml file.
     */
    public void BackToMainMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/main-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }
}
