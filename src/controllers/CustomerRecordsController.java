package controllers;

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
import javafx.stage.WindowEvent;
import sample.Appointment;
import sample.Customer;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerRecordsController implements Initializable {

    @FXML
    Label labelFeedback;
    @FXML
    TableView tableCustomerRecords;



    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelFeedback.setText("No Appointments in 15 Minutes");


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

    public void CreateNewCustomer(ActionEvent actionEvent) throws IOException {
        Scene scene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/new-customer.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/new-customer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(tempParent);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void EditSelectedCustomer(ActionEvent actionEvent) {
    }

    public void DeleteSelectedCustomer(ActionEvent actionEvent) {
    }

    public void SaveChanges(ActionEvent actionEvent) {
    }

    public void CancelChanges(ActionEvent actionEvent) {
    }

    public void BackToMainMenu(ActionEvent actionEvent) throws IOException {
        Scene scene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/main-menu.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/main-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(tempParent);
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
