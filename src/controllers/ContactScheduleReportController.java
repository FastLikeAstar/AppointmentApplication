package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Appointment;
import sample.AppointmentReport;
import sample.Contact;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ContactScheduleReportController implements Initializable {

    public ComboBox <Integer> ComboContactSelect;
    public TextField TextFieldContactName;
    public TableView <Appointment> TableContactSchedule;
    public TextField TextFieldContactId;

    /**
     * Populates the combo box with Contact IDs to select.
     * @param url JavaFX param.
     * @param resourceBundle JavaFX param.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> contacts = Main.dbContacts.getAllContactIds();
        ComboContactSelect.setItems(contacts);

    }

    /**
     * Method triggers on back button click to navigate the user back to the reports menu.
     * @param actionEvent back button click.
     * @throws IOException from reading in fxml file.
     */
    public void BackToReports(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/reports-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    /**
     * Populates table with schedule information for the selected contact.
     * @param contactId contact id of the selected contact to view the schedule.
     */
    public void LoadTable(int contactId){

        ObservableList<Appointment> appointmentsToShow = Main.dbAppointments.getAppointmentsForContact(contactId);

        TableColumn<Appointment, Integer> columnAppointmentId = new TableColumn<>("Appointment ID");
        TableColumn<Appointment, String> columnTitle = new TableColumn<>("Title");
        TableColumn<Appointment, String> columnDescription = new TableColumn<>("Description");
        TableColumn<Appointment, String> columnLocation = new TableColumn<>("Location");
        TableColumn<Appointment, String> columnType = new TableColumn<>("Type");
        TableColumn<Appointment, String> columnStart = new TableColumn<>("Start Time");
        TableColumn<Appointment, String> columnEnd = new TableColumn<>("End Time");
        TableColumn<Appointment, Integer> columnCustomerId = new TableColumn<>("Customer ID");



        columnAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnStart.setCellValueFactory(new PropertyValueFactory<>("startTimeAsLocal"));
        columnEnd.setCellValueFactory(new PropertyValueFactory<>("endTimeAsLocal"));
        columnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));



        TableContactSchedule.getColumns().addAll(columnAppointmentId, columnTitle,columnDescription, columnLocation,columnType, columnStart, columnEnd,columnCustomerId);

        TableContactSchedule.setItems(appointmentsToShow);
    }

    /**
     * Event when a contact is selected from the combo box.
     * Fills page with Name and Id, as well as loads the table for their schedule.
     * @param actionEvent trigger of onAction of combo box.
     */
    public void contactSelected(ActionEvent actionEvent) {
        int contactId = ComboContactSelect.getValue().intValue();
        Contact selectedContact = Main.dbContacts.getById(contactId);
        TextFieldContactName.setText(selectedContact.getContactName());

        TextFieldContactId.setText(""+selectedContact.getContactId());
        LoadTable(contactId);
    }

}

