package controllers;

import javafx.collections.FXCollections;
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
import org.w3c.dom.Text;
import sample.Appointment;
import sample.Country;
import sample.Customer;
import sample.Main;
import tools.DateConverter;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public ComboBox <Integer> comboCustomer;
    @FXML
    public ComboBox <Integer> comboUser;
    @FXML
    public ComboBox <Integer> comboContact;

    @FXML
    public TextField textFieldLocation;
    @FXML
    public TextField textFieldType;
    @FXML
    public RadioButton viewAll;
    @FXML
    public RadioButton viewMonth;
    @FXML
    public RadioButton viewWeek;
    @FXML
    public TableView <Appointment> table;
    @FXML
    public int selection;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public ComboBox startTimeCombo;
    @FXML
    public ComboBox endTimeCombo;

    int previousCustomerId;
    int selectedAppointmentId;



    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textFieldAppointmentId.setDisable(true);
        textFieldDescription.setDisable(true);
        textFieldLocation.setDisable(true);
        textFieldTitle.setDisable(true);
        textFieldType.setDisable(true);

        comboContact.setDisable(true);
        comboCustomer.setDisable(true);
        comboUser.setDisable(true);

        startTimeCombo.setDisable(true);
        endTimeCombo.setDisable(true);
        startDatePicker.setDisable(true);




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

        textFieldDescription.setDisable(false);
        textFieldLocation.setDisable(false);
        textFieldTitle.setDisable(false);
        textFieldType.setDisable(false);

        comboContact.setDisable(false);
        comboCustomer.setDisable(false);
        comboUser.setDisable(false);

        startTimeCombo.setDisable(false);
        endTimeCombo.setDisable(false);
        startDatePicker.setDisable(false);




        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
        selectedAppointmentId = selectedAppointment.getAppointmentId();

        textFieldDescription.setText(selectedAppointment.getDescription());
        textFieldLocation.setText(selectedAppointment.getLocation());
        textFieldTitle.setText(selectedAppointment.getTitle());
        textFieldType.setText(selectedAppointment.getType());
        textFieldAppointmentId.setText(""+selectedAppointment.getAppointmentId());

        comboContact.setValue(selectedAppointment.getContactId());
        comboCustomer.setValue(selectedAppointment.getCustomerId());
        comboUser.setValue(selectedAppointment.getUserId());

        ZonedDateTime startTime = selectedAppointment.getStartTimeAsUtc();
        ZonedDateTime startTimeForUser = startTime.withZoneSameInstant(ZoneId.systemDefault());

        ZonedDateTime endTime = selectedAppointment.getEndTimeAsUtc();
        ZonedDateTime endTimeForUser = endTime.withZoneSameInstant(ZoneId.systemDefault());


        startDatePicker.setValue(startTimeForUser.toLocalDate());
        startTimeCombo.setValue(startTimeForUser.toLocalTime());
        endTimeCombo.setValue(endTimeForUser.toLocalTime());

        startTimeCombo.setDisable(false);
        endTimeCombo.setDisable(false);
        startDatePicker.setDisable(false);

        labelFeedback.setTextFill(Color.DARKGREEN);
        labelFeedback.setText("You may now edit the \n selected customer.");

        previousCustomerId = comboCustomer.getValue().intValue();

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

    public void customerSelected(ActionEvent actionEvent) {

        if (comboCustomer.getValue().intValue() != previousCustomerId){

            startDatePicker.setDisable(false);
            startTimeCombo.setDisable(true);
            endTimeCombo.setDisable(true);

            startTimeCombo.setValue(null);
            endTimeCombo.setValue(null);
        }
    }

    public void updateStartTimeSelection(ActionEvent actionEvent) {
        startTimeCombo.setDisable(false);
        endTimeCombo.setDisable(true);
        endTimeCombo.setValue(null);

        LocalDate selectedDate = startDatePicker.getValue();
        ZonedDateTime selectedDateAsUtc = DateConverter.convertLocalDateToUTC(selectedDate);
        ZonedDateTime dateAsEst = selectedDateAsUtc.withZoneSameInstant(ZoneId.of("America/New_York"));

        if (7 == dateAsEst.getDayOfWeek().getValue() || 6 == dateAsEst.getDayOfWeek().getValue())
        {
            labelFeedback.setTextFill(Color.RED);
            labelFeedback.setText("Please select a weekday.");
        }
        else{

            Integer selectedCustomerId = (Integer) comboCustomer.getValue();

            ObservableList<Appointment> customerAppointments = Main.dbAppointments.getCustomerAppointments(selectedCustomerId.intValue());

            startTimeCombo.setValue(null);
            startTimeCombo.setDisable(false);
            endTimeCombo.setDisable(true);
            endTimeCombo.setValue(null);

            ObservableList<LocalTime> hours = FXCollections.observableArrayList();

            LocalTime startOfDay = LocalTime.of(Main.START_OF_DAY, 0);
            LocalTime endOfDay = LocalTime.of(Main.END_OF_DAY, 0);


            LocalDateTime beginningOfSelectedDateTime = LocalDateTime.of(selectedDate, startOfDay);
            ZonedDateTime beginningOfSelectedDateAsEst = ZonedDateTime.of(beginningOfSelectedDateTime,ZoneId.of("America/New_York"));

            LocalDateTime endOfSelectedDateTime = LocalDateTime.of(selectedDate, endOfDay);
            ZonedDateTime endOfSelectedDateTimeAsEst = ZonedDateTime.of(endOfSelectedDateTime,ZoneId.of("America/New_York"));

            ZonedDateTime opening = beginningOfSelectedDateAsEst.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime closing = endOfSelectedDateTimeAsEst.withZoneSameInstant(ZoneId.systemDefault());
            for (int i = opening.getHour(); i < closing.getHour(); i++) {
                hours.add(LocalTime.of(i, 0));
                hours.add(LocalTime.of(i, 30));
            }

            for (Appointment appointment : customerAppointments){

                if (appointment.getAppointmentId() != selectedAppointmentId){
                    ZonedDateTime appointmentStart = appointment.getStartTimeAsUtc();
                    appointmentStart = appointmentStart.withZoneSameInstant(ZoneId.systemDefault());

                    ZonedDateTime appointmentEnd = appointment.getEndTimeAsUtc();
                    appointmentEnd = appointmentEnd.withZoneSameInstant(ZoneId.systemDefault());

                    LocalTime finalAppointmentStart = appointmentStart.toLocalTime();
                    LocalTime finalAppointmentEnd = appointmentEnd.toLocalTime();
                    Iterator<LocalTime> iterator = hours.iterator();
                    while (iterator.hasNext()){
                        LocalTime hour = iterator.next();
                        if (hour.isAfter(finalAppointmentStart) && hour.isBefore(finalAppointmentEnd)){
                            iterator.remove();
                        }
                    }
                }

            }

            startTimeCombo.setItems(hours);
        }
    }
}
