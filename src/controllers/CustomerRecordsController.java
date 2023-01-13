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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Appointment;
import sample.Country;
import sample.Customer;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerRecordsController implements Initializable {

    @FXML
    public Button buttonNewCustomer;
    @FXML
    public Button buttonEditCustomer;
    @FXML
    public Button buttonDeleteCustomer;
    @FXML
    public TextField textFieldCustomerId;
    @FXML
    public TextField textFieldCustomerName;
    @FXML
    public TextField textFieldPhoneNumber;
    @FXML
    public TextField textFieldAddress;
    @FXML
    public TextField textFieldPostalCode;
    @FXML
    public ComboBox comboCountry;
    @FXML
    public ComboBox comboFirstDiv;
    @FXML
    public Button buttonSaveChanges;
    @FXML
    public Button buttonCancelChanges;
    @FXML
    public Button buttonBack;
    @FXML
    Label labelFeedback;
    @FXML
    TableView<Customer> tableCustomerRecords;

    String selectedCountry;



    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelFeedback.setText("");
        textFieldCustomerId.setDisable(true);
        comboCountry.setDisable(true);
        comboFirstDiv.setDisable(true);


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

    /**
     * Lambda 3: Lambda is used to increase readability and maintainability of the code by using parsing through a list
     * of countries instead of writing an addition method in the CountryDaoImpl class that returns the names.
     * @param actionEvent
     */
    public void EditSelectedCustomer(ActionEvent actionEvent) {
        comboCountry.setDisable(false);
        comboFirstDiv.setDisable(false);

        Customer selectedCustomer = tableCustomerRecords.getSelectionModel().getSelectedItem();

        ObservableList<Country> countries = Main.dbCountries.getAllCountries();
        // Lambda 3
        ObservableList<String> countryNames = countries.stream()
                .map(c -> c.getCountryName())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        comboCountry.setItems(countryNames);

        ObservableList<String> possibleDivisions = Main.dbDivisions.getDivisionsFromCountry(selectedCustomer.getCountry());

        if (!possibleDivisions.isEmpty()){
            comboFirstDiv.setItems(possibleDivisions);
        }
        else{
            labelFeedback.setText("No Divisions Found in Country.");
        }


        textFieldCustomerId.setText(""+ selectedCustomer.getCustomerId() + "");
        textFieldCustomerName.setText(selectedCustomer.getCustomerName());
        textFieldAddress.setText(selectedCustomer.getAddress());
        textFieldPhoneNumber.setText(selectedCustomer.getPhone());
        textFieldPostalCode.setText(selectedCustomer.getPostalCode());
        textFieldCustomerId.setDisable(true);
        comboCountry.setValue(selectedCustomer.getCountry());
        selectedCountry = selectedCustomer.getCountry();
        comboFirstDiv.setValue(selectedCustomer.getDivision());

        textFieldCustomerName.setDisable(false);
        textFieldCustomerName.setEditable(true);
        textFieldAddress.setDisable(false);
        textFieldAddress.setEditable(true);
        textFieldPhoneNumber.setDisable(false);
        textFieldPhoneNumber.setEditable(true);
        textFieldPostalCode.setDisable(false);
        textFieldPostalCode.setEditable(true);


        labelFeedback.setText("You may now edit the selected customer.");


    }

    public void DeleteSelectedCustomer(ActionEvent actionEvent) {
    }

    public void SaveChanges(ActionEvent actionEvent) {

    }

    public void CancelChanges(ActionEvent actionEvent) {
        Customer selectedCustomer = tableCustomerRecords.getSelectionModel().getSelectedItem();

        ObservableList<Country> countries = Main.dbCountries.getAllCountries();
        // Lambda 3
        ObservableList<String> countryNames = countries.stream()
                .map(c -> c.getCountryName())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        comboCountry.setItems(countryNames);

        ObservableList<String> possibleDivisions = Main.dbDivisions.getDivisionsFromCountry(selectedCustomer.getCountry());

        if (!possibleDivisions.isEmpty()){
            comboFirstDiv.setItems(possibleDivisions);
        }
        else{
            labelFeedback.setText("No Divisions Found in Country.");
        }


        textFieldCustomerId.setText(""+ selectedCustomer.getCustomerId() + "");
        textFieldCustomerName.setText(selectedCustomer.getCustomerName());
        textFieldAddress.setText(selectedCustomer.getAddress());
        textFieldPhoneNumber.setText(selectedCustomer.getPhone());
        textFieldPostalCode.setText(selectedCustomer.getPostalCode());
        textFieldCustomerId.setDisable(true);
        comboCountry.setValue(selectedCustomer.getCountry());
        comboFirstDiv.setValue(selectedCustomer.getDivision());

        labelFeedback.setText("Values reset.");
    }

    public void BackToMainMenu(ActionEvent actionEvent) throws IOException {
        Scene scene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/main-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(tempParent);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void UpdateDivisionSelection(ActionEvent actionEvent){
        if (comboCountry.getValue() != null) {
            selectedCountry = comboCountry.getValue().toString();
            ObservableList<String> possibleDivisions = Main.dbDivisions.getDivisionsFromCountry(selectedCountry);

            if (!possibleDivisions.isEmpty()) {
                comboFirstDiv.setItems(possibleDivisions);
            } else {
                labelFeedback.setText("No Divisions Found in Country.");
            }
        }
    }
}
