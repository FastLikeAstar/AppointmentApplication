package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Appointment;
import sample.Customer;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAppointmentsController implements Initializable {
    @FXML
    public Button buttonSaveChanges;
    @FXML
    public Button buttonCancelChanges;
    @FXML
    public Button buttonBack;
    @FXML
    public Button buttonNewAppointment;
    @FXML
    public Button buttonEditAppointment;
    @FXML
    public Button buttonCancelAppointment;
    @FXML
    public Label labelFeedback;
    @FXML
    public Label labelSelectedAppointment;
    @FXML
    public TextField textFieldAppointmentId;
    @FXML
    public TextField textFieldTitle;
    @FXML
    public TextField textFieldDescription;
    @FXML
    public ComboBox comboCustomer;
    @FXML
    public ComboBox comboType;
    @FXML
    public ComboBox comboUser;
    @FXML
    public ComboBox comboContact;
    @FXML
    public TextField textFieldStartTime;
    @FXML
    public TextField textFieldEndTime;
    @FXML
    public TextField textFieldLocation;
    @FXML
    public RadioButton viewAll;
    @FXML
    public RadioButton viewMonth;
    @FXML
    public RadioButton viewWeek;
    @FXML
    public TableView table;
    @FXML
    public int selection;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int selection = 1;
        LoadTable(1);
    }

    public void LoadTable(int selection){
        table.getItems().clear();
        table.getColumns().clear();
        ObservableList<Appointment> appointmentsToShow = null;
        if (selection == 1){
            appointmentsToShow = Main.dbAppointments.getAllAppointments();
        }
        else if (selection == 2){

        }
        else if (selection == 3){

        }

        TableColumn<Appointment, Integer> columnAppointmentId = new TableColumn<>("Appointment ID");
        TableColumn<Appointment, String> columnTitle = new TableColumn<>("Title");
        TableColumn<Appointment, String> columnDescription = new TableColumn<>("Description");
        TableColumn<Appointment, String> columnLocation = new TableColumn<>("Location");
        TableColumn<Appointment, String> columnContact = new TableColumn<>("Contact");
        TableColumn<Appointment, String> columnType = new TableColumn<>("Type");
        TableColumn<Appointment, String> columnStart = new TableColumn<>("Start Time");
        TableColumn<Appointment, String> columnEnd = new TableColumn<>("End Time");
        TableColumn<Appointment, Integer> columnCustomerId = new TableColumn<>("Customer ID");
        TableColumn<Appointment, Integer> columnUserId = new TableColumn<>("User ID");

        columnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        columnStart.setCellValueFactory(new PropertyValueFactory<>("startTimeAsLocal"));
        columnEnd.setCellValueFactory(new PropertyValueFactory<>("endTimeAsLocal"));
        columnUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));


        table.getColumns().addAll(columnAppointmentId, columnTitle,columnDescription, columnLocation, columnContact,columnType, columnStart, columnEnd,columnCustomerId, columnUserId);

        table.setItems(appointmentsToShow);
    }

    public void CreateNewAppointment(ActionEvent actionEvent) {
    }

    public void EditSelectedAppointment(ActionEvent actionEvent) {
    }

    public void CancelSelectedAppointment(ActionEvent actionEvent) {
    }

    public void SaveChanges(ActionEvent actionEvent) {
    }

    public void CancelChanges(ActionEvent actionEvent) {
    }

    public void BackToMainMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/main-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }
}
