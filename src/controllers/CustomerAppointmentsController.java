package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Customer;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAppointmentsController implements Initializable {
    public Button buttonSaveChanges;
    public Button buttonCancelChanges;
    public Button buttonBack;
    public Button buttonNewAppointment;
    public Button buttonEditAppointment;
    public Button buttonCancelAppointment;
    public Label labelFeedback;
    public Tab tabWeek;
    public TableView tableWeekView;
    public Tab tabMonth;
    public TableView tableMonthView;
    public Label labelSelectedAppointment;
    public TextField textFieldAppointmentId;
    public TextField textFieldTitle;
    public TextField textFieldDescription;
    public ComboBox comboCustomer;
    public ComboBox comboType;
    public ComboBox comboUser;
    public ComboBox comboContact;
    public TextField textFieldStartTime;
    public TextField textFieldEndTime;
    public TextField textFieldLocation;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void LoadTable(){
        tableCustomerRecords.getItems().clear();
        tableCustomerRecords.getColumns().clear();
        ObservableList<Customer> customers = Main.dbCustomers.getAllCustomers();

        TableColumn<Customer, Integer> columnCustomerId = new TableColumn<>("ID");
        TableColumn<Customer, String> columnCustomerName = new TableColumn<>("Name");
        TableColumn<Customer, String> columnPhoneNumber = new TableColumn<>("Phone");
        TableColumn<Customer, String> columnAddress = new TableColumn<>("Address");
        TableColumn<Customer, String> columnPostalCode = new TableColumn<>("Postal Code");
        TableColumn<Customer, String> columnFirstDiv = new TableColumn<>("Division");
        TableColumn<Customer, String> columnCountry = new TableColumn<>("Country");

        columnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        columnFirstDiv.setCellValueFactory(new PropertyValueFactory<>("division"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));


        tableCustomerRecords.getColumns().addAll(columnCustomerId, columnCustomerName, columnPhoneNumber, columnAddress, columnPostalCode, columnFirstDiv, columnCountry);

        tableCustomerRecords.setItems(customers);
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
