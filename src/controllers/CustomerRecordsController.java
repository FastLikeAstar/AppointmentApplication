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
    @FXML
    TableColumn columnCustomerId;
    @FXML
    TableColumn columnCustomerName;
    @FXML
    TableColumn columnPhoneNumber;
    @FXML
    TableColumn columnAddress;
    @FXML
    TableColumn columnPostalCode;
    @FXML
    TableColumn columnFirstDiv;
    @FXML
    TableColumn columnCountry;


    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelFeedback.setText("No Appointments in 15 Minutes");


        ObservableList<Customer> customers = Main.dbCustomers.getAllCustomers();

        columnCustomerId.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        columnPostalCode.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        columnFirstDiv.setCellValueFactory(new PropertyValueFactory<>("Division"));
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));


        tableCustomerRecords.getColumns().addAll(columnCustomerId, columnCustomerName, columnPhoneNumber, columnAddress, columnPostalCode, columnFirstDiv, columnCountry);

        tableCustomerRecords.setItems(customers);

    }

    public void CreateNewCustomer(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/new-customer.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/new-customer.fxml"));
        Stage stage = new Stage();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.show();
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
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/main-menu.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/main-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
    }
}
