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
    public ComboBox contactCombo;
    @FXML
    public TextField typeTextField;
    @FXML
    public ComboBox customerIdCombo;
    @FXML
    public ComboBox userIdCombo;
    @FXML
    public ComboBox <Integer> endTimeCombo;
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
        datePicker.setDisable(true);

        ObservableList<String> contacts = Main.dbContacts.getAllContactsNames();
        contactCombo.setItems(contacts);

        ObservableList<Integer> customers = Main.dbCustomers.getAllCustomerIds();
        customerIdCombo.setItems(customers);

        ObservableList<Integer> users = Main.dbUsers.getAllUserIds();
        customerIdCombo.setItems(users);


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
//        boolean valid = false;
//
//        if (contactCombo.getValue() != null && userIdCombo.getValue() != null && customerIdCombo.getValue() != null) {
//            String title = titleTextField.getText();
//            String description = descriptionTextField.getText();
//            String location = locationTextField.getText();
//            LocalDate dateSelected = datePicker.getValue();
//            String country = comboCountry.getValue().toString();
//            String division = comboFirstDiv.getValue().toString();
//
//
//            valid = (!(title.isBlank()) &&
//                    !(number.isBlank()) &&
//                    !(address.isBlank()) &&
//                    !(postalCode.isBlank()) &&
//                    !(contactCombo.getSelectionModel().isEmpty()) &&
//                    !(userIdCombo.getSelectionModel().isEmpty()) &&
//                    !(customerIdCombo.getSelectionModel().isEmpty()));
//
//            if (valid) {
//
//                Customer customer = new Customer(-1, name, address, postalCode, number, null, Main.user, null, Main.user, divisionId);
//                Main.dbCustomers.save(customer);
//
//                // Navigate back
//                Scene productScene;
//                Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-records.fxml"));
//                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//                productScene = new Scene(tempParent);
//                stage.setScene(productScene);
//                stage.centerOnScreen();
//            }
//            else{
//                labelFeedback.setText("Please check that all fields have a valid entry.");
//            }
//        }
//        else {
//            labelFeedback.setText("Please check that all fields have a valid entry.");
//        }

    }


    public void UpdateEndTimeSelection(ActionEvent actionEvent) {
        if (startTimeCombo.getValue() != null) {
            endTimeCombo.setDisable(false);

            LocalTime selectedTime = startTimeCombo.getValue();
            LocalDate selectedDate = datePicker.getValue();
            LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);

            ZonedDateTime selectedDateAsUtc = DateConverter.convertSystemLocalDateTimeToUtc(selectedDateTime);
            ZonedDateTime dateAsEst = selectedDateAsUtc.withZoneSameInstant(ZoneId.of("America/New_York"));


            LocalTime startOfDay = LocalTime.of(Main.START_OF_DAY, 0);
            LocalTime endOfDay = LocalTime.of(Main.END_OF_DAY, 0);


            LocalDateTime beginningOfSelectedDateTime = LocalDateTime.of(selectedDate, startOfDay);
            ZonedDateTime beginningOfSelectedDateAsEst = ZonedDateTime.of(beginningOfSelectedDateTime,ZoneId.of("America/New_York"));

            LocalDateTime endOfSelectedDateTime = LocalDateTime.of(selectedDate, endOfDay);
            ZonedDateTime endOfSelectedDateTimeAsEst = ZonedDateTime.of(endOfSelectedDateTime,ZoneId.of("America/New_York"));

            ZonedDateTime opening = beginningOfSelectedDateAsEst.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime closing = endOfSelectedDateTimeAsEst.withZoneSameInstant(ZoneId.systemDefault());

            if (selectedDateTime.toLocalTime().isBefore(opening.toLocalTime()) || selectedDateTime.toLocalTime().isAfter(closing.toLocalTime())) {
                labelFeedback.setTextFill(Color.RED);

                labelFeedback.setText("Please select a time between:\n" + opening.toLocalTime() + " and " + closing.toLocalTime());
            } else {
                endTimeCombo.setDisable(false);

                ObservableList<Integer> localTimes = FXCollections.observableArrayList();
                for (int i = 0; i < 24; i++) {
                    localTimes.add(i);
                }

                endTimeCombo.setItems(localTimes);
            }

        }
    }

    public void UpdateStartTimeSelection(ActionEvent actionEvent) {
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

            Integer selectedCustomerId = (Integer) customerIdCombo.getValue();

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
