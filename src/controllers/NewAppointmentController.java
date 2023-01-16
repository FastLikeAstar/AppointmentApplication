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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.*;
import tools.DateConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Iterator;
import java.util.ResourceBundle;

public class NewAppointmentController implements Initializable {

    @FXML
    public Label labelFeedback;
    @FXML
    public TextField titleTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField locationTextField;
    @FXML
    public ComboBox <Integer> contactCombo;
    @FXML
    public TextField typeTextField;
    @FXML
    public ComboBox <Integer> customerIdCombo;
    @FXML
    public ComboBox <Integer> userIdCombo;
    @FXML
    public ComboBox<LocalTime> endTimeCombo;
    @FXML
    public ComboBox <LocalTime> startTimeCombo;
    @FXML
    public DatePicker datePicker;


    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelFeedback.setText("");
        startTimeCombo.setDisable(true);
        endTimeCombo.setDisable(true);
        userIdCombo.setDisable(true);
        datePicker.setDisable(true);

        ObservableList<Integer> contacts = Main.dbContacts.getAllContactIds();
        contactCombo.setItems(contacts);

        ObservableList<Integer> customers = Main.dbCustomers.getAllCustomerIds();
        customerIdCombo.setItems(customers);

        ObservableList<Integer> users = Main.dbUsers.getAllUserIds();
        userIdCombo.setItems(users);
        int userIdFromName = Main.dbUsers.getIdByName(Main.user);
        userIdCombo.setValue(userIdFromName);

    }




    public void NavToAppointments(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    public void SaveData(ActionEvent actionEvent) throws IOException {
        boolean valid = false;

        if (contactCombo.getValue() != null && userIdCombo.getValue() != null && customerIdCombo.getValue() != null
            && startTimeCombo.getValue() != null && endTimeCombo.getValue() != null && datePicker.getValue() != null) {
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();

            LocalDate dateSelected = datePicker.getValue();
            LocalTime startTime = startTimeCombo.getValue();
            LocalTime endTime = endTimeCombo.getValue();

            LocalDateTime localStart = LocalDateTime.of(dateSelected, startTime);
            LocalDateTime localEnd = LocalDateTime.of(dateSelected, endTime);
            ZonedDateTime utcStart = ZonedDateTime.of(localStart, ZoneId.systemDefault());
            ZonedDateTime utcEnd = ZonedDateTime.of(localEnd, ZoneId.systemDefault());
            Timestamp start = DateConverter.convertUtcToTimestamp(utcStart);
            Timestamp end = DateConverter.convertUtcToTimestamp(utcEnd);

            int customerId = customerIdCombo.getValue().intValue();
            int contactId = contactCombo.getValue().intValue();
            int userId = userIdCombo.getValue().intValue();


            valid = (!(title.isBlank()) &&
                    !(description.isBlank()) &&
                    !(type.isBlank()) &&
                    !(location.isBlank()) &&
                    !(startTimeCombo.getSelectionModel().isEmpty()) &&
                    !(endTimeCombo.getSelectionModel().isEmpty()) &&
                    !(contactCombo.getSelectionModel().isEmpty()) &&
                    !(userIdCombo.getSelectionModel().isEmpty()) &&
                    !(customerIdCombo.getSelectionModel().isEmpty()));

            if (valid) {

                Appointment appointment = new Appointment(0, title, description, location, type, start, end, start, Main.user, start, Main.user, customerId, userId, contactId);
                Main.dbAppointments.save(appointment);

                // Navigate back
                Scene productScene;
                Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-appointments.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                productScene = new Scene(tempParent);
                stage.setScene(productScene);
                stage.centerOnScreen();
            }
            else{
                labelFeedback.setText("Please check that all fields have a valid entry.");
            }
        }
        else {
            labelFeedback.setText("Please check that all fields have a valid entry.");
        }

    }


    public void updateEndTimeSelection(ActionEvent actionEvent) {
        if (startTimeCombo.getValue() != null) {
            endTimeCombo.setDisable(false);
            LocalDate selectedDate = datePicker.getValue();
            Integer selectedCustomerId = customerIdCombo.getValue();

            ObservableList<Appointment> customerAppointments = Main.dbAppointments.getCustomerAppointments(selectedCustomerId.intValue());


            ObservableList<LocalTime> hours = FXCollections.observableArrayList();

            LocalTime startOfDay = LocalTime.of(Main.START_OF_DAY, 0);
            LocalTime endOfDay = LocalTime.of(Main.END_OF_DAY, 0);


            LocalDateTime beginningOfSelectedDateTime = LocalDateTime.of(selectedDate, startOfDay);
            ZonedDateTime beginningOfSelectedDateAsEst = ZonedDateTime.of(beginningOfSelectedDateTime, ZoneId.of("America/New_York"));

            LocalDateTime endOfSelectedDateTime = LocalDateTime.of(selectedDate, endOfDay);
            ZonedDateTime endOfSelectedDateTimeAsEst = ZonedDateTime.of(endOfSelectedDateTime, ZoneId.of("America/New_York"));

            ZonedDateTime closing = endOfSelectedDateTimeAsEst.withZoneSameInstant(ZoneId.systemDefault());
            for (int i = startTimeCombo.getValue().getHour(); i < closing.getHour(); i++) {
                hours.add(LocalTime.of(i, 0));
                hours.add(LocalTime.of(i, 30));
            }

            Iterator<LocalTime> iterator = hours.iterator();
            while (iterator.hasNext()) {
                LocalTime hour = iterator.next();
                if (hour.isBefore(startTimeCombo.getValue()) || hour.equals(startTimeCombo.getValue())) {
                    iterator.remove();
                }
            }

            for (Appointment appointment : customerAppointments) {
                    ZonedDateTime appointmentStart = appointment.getStartTimeAsUtc();
                    appointmentStart = appointmentStart.withZoneSameInstant(ZoneId.systemDefault());

                    LocalDate previousAppointment = appointmentStart.toLocalDate();


                    if (previousAppointment.equals(datePicker.getValue())) {
                        LocalTime finalAppointmentStart = appointmentStart.toLocalTime();
                        Iterator<LocalTime> iteratorHour = hours.iterator();
                        while (iteratorHour.hasNext()) {
                            LocalTime hour2 = iteratorHour.next();
                            if (hour2.isAfter(finalAppointmentStart)) {
                                iteratorHour.remove();
                            }
                        }
                    }

            }
            endTimeCombo.setItems(hours);
        }
    }

    public void updateStartTimeSelection(ActionEvent actionEvent) {
        startTimeCombo.setDisable(false);
        endTimeCombo.setDisable(true);
        endTimeCombo.setValue(null);

        LocalDate selectedDate = datePicker.getValue();
        ZonedDateTime selectedDateAsUtc = DateConverter.convertLocalDateToUTC(selectedDate);
        ZonedDateTime dateAsEst = selectedDateAsUtc.withZoneSameInstant(ZoneId.of("America/New_York"));

        if (7 == dateAsEst.getDayOfWeek().getValue() || 6 == dateAsEst.getDayOfWeek().getValue())
        {
            labelFeedback.setTextFill(Color.RED);
            labelFeedback.setText("Please select a weekday.");
        }
        else{

            Integer selectedCustomerId = customerIdCombo.getValue();

            ObservableList<Appointment> customerAppointments = Main.dbAppointments.getCustomerAppointments(selectedCustomerId.intValue());

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

            startTimeCombo.setItems(hours);
        }


    }

    public void displayResultsInComboBox(ObservableList<Timestamp> results, ComboBox<LocalTime> comboBox) {
        comboBox.getItems().clear();
        for (Timestamp t : results) {
            LocalTime time = t.toLocalDateTime().toLocalTime();
            comboBox.getItems().add(time);
        }
    }

    public void customerSelected(ActionEvent actionEvent) {
        datePicker.setDisable(false);
        startTimeCombo.setValue(null);
    }
}
