package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAcquiredReportController implements Initializable {
    public TableView TableContactInfo;
    public Label label;

    /**
     * Fills in data of customer acquisitions for the past year.
     * @param url JavaFX param.
     * @param resourceBundle JavaFX param.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int count = Main.dbCustomers.getCountCustomersLastYear();
        label.setText("We have acquired " + count + " new customers in the past year to date! \n" +
                "Note: Acquired is calculated from when a customer is added to the database.");

    }

    /**
     * Navigates user back to report menu.
     * @param actionEvent fired when back is clicked.
     * @throws IOException from reading in fxml location.
     */
    public void BackToReportsMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/reports-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }
}
