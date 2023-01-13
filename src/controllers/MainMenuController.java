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

    @FXML
    TableView tableUpcomingAppointment;
    @FXML
    Label labelAppointmentWarning;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ObservableList<Appointment> upcomingAppointments = Main.dbAppointments.getUpcomingAppointments();

        if (!upcomingAppointments.isEmpty()){
            TableColumn<Appointment, String> nameColumn = new TableColumn<>("Title");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));

            TableColumn<Appointment, String> locationColumn = new TableColumn<>("Location");
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));

            TableColumn<Appointment, String> startTimeColumn = new TableColumn<>("Start");
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));

            TableColumn<Appointment, String> endTimeColumn = new TableColumn<>("End");
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("End"));

            tableUpcomingAppointment.getColumns().addAll(nameColumn, locationColumn, startTimeColumn, endTimeColumn);

            tableUpcomingAppointment.setItems(upcomingAppointments);
        }
        else{
            labelAppointmentWarning.setText("No Appointments in 15 Minutes");
            Label label = new Label("This will populate with appointment(s) occurring within 15 minutes. \n Please click Customer Appointments to view Appointments in detail.");
            tableUpcomingAppointment.setPlaceholder(label);
        }
    }

    public void NavToCustomerRecords(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/customer-records.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/customer-records.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }

    public void NavToCustomerAppointments(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/customer-appointments.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/customer-appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }

    public void NavToReportsMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/reports-menu.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/reports-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }

    public void ExitApplication(ActionEvent actionEvent) {

        // Exit the Program after disconnecting
        Platform.exit();
    }
}
