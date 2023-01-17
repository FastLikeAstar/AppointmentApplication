package controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Appointment;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public Label labelWelcome;
    @FXML
    TableView tableUpcomingAppointment;
    @FXML
    Label labelAppointmentWarning;

    /**
     * Welcomes the user and alerts users to upcoming appointments.
     * @param url JavaFX param.
     * @param resourceBundle JavaFX param.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelWelcome.setText("Welcome " + Main.user);

        ObservableList<Appointment> upcomingAppointments = Main.dbAppointments.getUpcomingAppointments();

        if (!upcomingAppointments.isEmpty()){
            TableColumn<Appointment, String> nameColumn = new TableColumn<>("Title");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Appointment, String> locationColumn = new TableColumn<>("Location");
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

            TableColumn<Appointment, String> startTimeColumn = new TableColumn<>("Start");
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTimeAsLocal"));

            TableColumn<Appointment, String> endTimeColumn = new TableColumn<>("End");
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTimeAsLocal"));

            tableUpcomingAppointment.getColumns().addAll(nameColumn, locationColumn, startTimeColumn, endTimeColumn);

            tableUpcomingAppointment.setItems(upcomingAppointments);
        }
        else{
            labelAppointmentWarning.setText("No Appointments in 15 Minutes");
            Label label = new Label("This will populate with appointment(s) occurring within 15 minutes. \n Please click Customer Appointments to view Appointments in detail.");
            tableUpcomingAppointment.setPlaceholder(label);
        }
    }

    /**
     * Navigates to the Customer Records form when clicked.
     * @param actionEvent onClick from customer records button.
     * @throws IOException from loading fxml.
     */
    public void NavToCustomerRecords(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-records.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    /**
     * Navigates to the Appointments form when clicked.
     * @param actionEvent onClick from customer appointments button.
     * @throws IOException from loading fxml.
     */
    public void NavToCustomerAppointments(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    /**
     * Navigates to the Reports Menu form when clicked.
     * @param actionEvent onClick from reports menu button.
     * @throws IOException from loading fxml.
     */
    public void NavToReportsMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/reports-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    /**
     * Exits the program.
     * @param actionEvent event that triggers from close button.
     */
    public void ExitApplication(ActionEvent actionEvent) {

        Platform.exit();
    }
}
