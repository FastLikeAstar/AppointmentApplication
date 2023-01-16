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
import javafx.scene.paint.Color;
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

        // All View is 1, Monthly View is 2, Weekly View is 3,
        viewAll.setSelected(true);
        int selection = 1;
        LoadTable(selection);
    }

    public void LoadTable(int selection){
        table.getItems().clear();
        table.getColumns().clear();
        ObservableList<Appointment> appointmentsToShow = null;
        if (selection == 1){
            appointmentsToShow = Main.dbAppointments.getAllAppointments();
            labelFeedback.setTextFill(Color.DARKGREEN);
            labelFeedback.setText("Viewing All Appointments.");
            if (appointmentsToShow.isEmpty()){
                labelFeedback.setTextFill(Color.RED);
                labelFeedback.setText("No Appointments Found.");
                Label label = new Label("No Appointments. \n This will populate with appointment(s) when appointments are created.");
                table.setPlaceholder(label);
            }
        }
        else if (selection == 2){
            appointmentsToShow = Main.dbAppointments.getNextMonthOfAppointments();
            labelFeedback.setTextFill(Color.DARKGREEN);
            labelFeedback.setText("Viewing Next Month \n of Appointments.");
            if (appointmentsToShow.isEmpty()){
                labelFeedback.setTextFill(Color.RED);
                labelFeedback.setText("No Appointments \n Next Month");
                Label label = new Label("No Appointments in the next month found. \n This will populate with appointment(s) occurring within the next month.");
                table.setPlaceholder(label);
            }
        }
        else if (selection == 3){

            appointmentsToShow = Main.dbAppointments.getNextWeekOfAppointments();

            labelFeedback.setTextFill(Color.DARKGREEN);
            labelFeedback.setText("Viewing Next Week \n of Appointments.");
            if (appointmentsToShow.isEmpty()){
                labelFeedback.setTextFill(Color.RED);
                labelFeedback.setText("No Appointments \n Next Week");
                Label label = new Label("No Appointments in the next week found. \n This will populate with appointment(s) occurring within the next week.");
                table.setPlaceholder(label);
            }
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

    public void CreateNewAppointment(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/new-appointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
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

    public void ToggleAll(ActionEvent actionEvent) {
        this.viewAll.setSelected(true);
        this.selection = 1;
        LoadTable(this.selection);
    }

    public void ToggleMonth(ActionEvent actionEvent) {
        this.viewMonth.setSelected(true);
        this.selection = 2;
        LoadTable(this.selection);
    }

    public void ToggleWeek(ActionEvent actionEvent) {
        this.viewWeek.setSelected(true);
        this.selection = 3;
        LoadTable(this.selection);
    }
}
