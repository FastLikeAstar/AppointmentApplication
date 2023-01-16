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
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import sample.*;
import tools.DateConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public ComboBox <Integer> startTimeCombo;
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
        endTimeCombo.setDisable(false);
        endTimeCombo.setValue(null);

        LocalTime selectedTime = LocalTime.of(startTimeCombo.getValue(), 0);
        LocalDate selectedDate = datePicker.getValue();
        LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate,selectedTime);

        ZonedDateTime selectedDateAsUtc = DateConverter.convertLocalToUtc(selectedDateTime);
        ZonedDateTime dateAsEst = selectedDateAsUtc.withZoneSameInstant(ZoneId.of("America/New_York"));


        LocalTime startOfDay = LocalTime.of(Main.START_OF_DAY, 0);
        LocalTime endOfDay = LocalTime.of(Main.END_OF_DAY, 0);


        LocalDateTime beginningOfSelectedDateTime = LocalDateTime.of(selectedDate,startOfDay);
        ZonedDateTime beginningOfSelectedDateAsUtc = DateConverter.convertLocalToUtc(beginningOfSelectedDateTime);
        ZonedDateTime beginningOfSelectedDateAsEst = beginningOfSelectedDateAsUtc.withZoneSameInstant(ZoneId.of("America/New_York"));

        LocalDateTime endOfSelectedDateTime = LocalDateTime.of(selectedDate,startOfDay);
        ZonedDateTime endOfSelectedDateAsUtc = DateConverter.convertLocalToUtc(endOfSelectedDateTime);
        ZonedDateTime endOfSelectedDateAsEst = endOfSelectedDateAsUtc.withZoneSameInstant(ZoneId.of("America/New_York"));

        if (dateAsEst.toLocalTime().isBefore(startOfDay) || dateAsEst.toLocalTime().isAfter(endOfDay))
        {
            labelFeedback.setTextFill(Color.RED);
            LocalTime opening = beginningOfSelectedDateAsEst.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
            LocalTime closing = endOfSelectedDateAsEst.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
            labelFeedback.setText("Please select a time between:\n" + opening + " and " + closing );
        }
        else{
            startTimeCombo.setValue(null);
            startTimeCombo.setDisable(false);
            endTimeCombo.setDisable(true);
            endTimeCombo.setValue(null);

            ObservableList<Integer> localTimes = FXCollections.observableArrayList();
            for (int i = 0; i < 24; i++){
                localTimes.add(i);
            }

            endTimeCombo.setItems(localTimes);
        }
    }

    public void UpdateStartTimeSelection(ActionEvent actionEvent) {
        startTimeCombo.setValue(null);
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
            startTimeCombo.setValue(null);
            startTimeCombo.setDisable(false);
            endTimeCombo.setDisable(true);
            endTimeCombo.setValue(null);

            ObservableList<Integer> localTimes = FXCollections.observableArrayList();
            for (int i = 0; i < 24; i++){
                localTimes.add(i);
            }

            startTimeCombo.setItems(localTimes);
        }


    }

    public void displayResultsInComboBox(ObservableList<Timestamp> results, ComboBox<LocalTime> comboBox) {
        comboBox.getItems().clear();
        for (Timestamp t : results) {
            LocalTime time = t.toLocalDateTime().toLocalTime();
            comboBox.getItems().add(time);
        }
    }
}
