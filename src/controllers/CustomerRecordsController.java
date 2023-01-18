package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import sample.Country;
import sample.Customer;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    public TextField searchBar;
    @FXML
    Label labelFeedback;
    @FXML
    TableView<Customer> tableCustomerRecords;

    String selectedCountry;
    Customer customerBeingChanged;
    final StringProperty searchText = new SimpleStringProperty();
    ObservableList<Customer> customers;
    FilteredList<Customer> searchedItems;

    /**
     * Sets up the initial table for customers to be displayed.
     * @param url JavaFX param.
     * @param resourceBundle JavaFX param.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customers = Main.dbCustomers.getAllCustomers();
        labelFeedback.setText("");
        textFieldCustomerId.setDisable(true);
        comboCountry.setDisable(true);
        comboFirstDiv.setDisable(true);
        searchText.setValue("");
        searchBar.textProperty().bindBidirectional(searchText);
        searchedItems = new FilteredList<>(customers);

        searchedItems.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            if (searchText.get().isEmpty()) {
                return null;
            }
            return myData -> myData.getCustomerName().toLowerCase().contains(searchText.get().toLowerCase());
        }, searchText));

        LoadTable();
        tableCustomerRecords.setItems(searchedItems);

    }

    /**
     * Loads the table with customer data.
     */
    public void LoadTable(){
        tableCustomerRecords.getItems().clear();
        tableCustomerRecords.getColumns().clear();
        customers = Main.dbCustomers.getAllCustomers();

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

        tableCustomerRecords.setItems(searchedItems);
    }

    /**
     * Opens the new customer form when new customer is clicked.
     * @param actionEvent new customer clicked.
     * @throws IOException from loading fxml file.
     */
    public void CreateNewCustomer(ActionEvent actionEvent) throws IOException {
        Scene scene;
        Parent tempParent = FXMLLoader.load(Main.class.getResource("/new-customer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(tempParent);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    /**
     * Allows the selected customer to be edited and updated with the fields below the table.
     * Populates fields with data of selected customer.
     *
     * Lambda 3: Lambda is used to increase readability and maintainability of the code by using parsing through a list
     * of countries instead of writing an addition method in the CountryDaoImpl class that returns the names.
     *
     * @param actionEvent fires when edit customer is clicked.
     */
    public void EditSelectedCustomer(ActionEvent actionEvent) {
        if (tableCustomerRecords.getSelectionModel().getSelectedItem() != null) {
            comboCountry.setDisable(false);
            comboFirstDiv.setDisable(false);

            Customer selectedCustomer = tableCustomerRecords.getSelectionModel().getSelectedItem();
            customerBeingChanged = selectedCustomer;


            ObservableList<Country> countries = Main.dbCountries.getAllCountries();
            // Lambda 3
            ObservableList<String> countryNames = countries.stream()
                    .map(c -> c.getCountryName())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            comboCountry.setItems(countryNames);

            ObservableList<String> possibleDivisions = Main.dbDivisions.getDivisionsFromCountry(selectedCustomer.getCountry());

            if (!possibleDivisions.isEmpty()) {
                comboFirstDiv.setItems(possibleDivisions);
            } else {
                labelFeedback.setTextFill(Color.RED);
                labelFeedback.setText("No Divisions Found in Country.");
            }


            textFieldCustomerId.setText("" + selectedCustomer.getCustomerId() + "");
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

            labelFeedback.setTextFill(Color.DARKGREEN);
            labelFeedback.setText("You may now edit the \n selected customer.");
        }

    }

    /**
     * Deletes the selected customer from the database and updates the table accordingly.
     *
     * Lambda 2: Lambda is used to contain the majority of relevant code in this code block. Lambda increases the
     * maintainability of this method by avoiding creating additional methods across different classes to achieve
     * the same result.
     *
     * @param actionEvent fires when deleted customer is selected.
     */
    public void DeleteSelectedCustomer(ActionEvent actionEvent) {
        if (tableCustomerRecords.getSelectionModel().getSelectedItem() != null) {

            Customer selectedCustomer = tableCustomerRecords.getSelectionModel().getSelectedItem();
            ObservableList<Appointment> appointments = Main.dbAppointments.getAllAppointments();
            int customerIdToDelete = selectedCustomer.getCustomerId();

            // Lambda 2
            List<Integer> appointmentIdsOfCustomer = appointments.stream()
                    .filter(appointment -> appointment.getCustomerId() == customerIdToDelete)
                    .map(Appointment::getAppointmentId)
                    .collect(Collectors.toList());

            for (int appointmentId : appointmentIdsOfCustomer) {
                Main.dbAppointments.delete(appointmentId);
            }

            Main.dbCustomers.delete(customerIdToDelete);

            LoadTable();
            labelFeedback.setTextFill(Color.BLUE);
            labelFeedback.setText("Customer and Appointments Deleted.");
        } else {
            labelFeedback.setTextFill(Color.RED);
            labelFeedback.setText("No customer selected.");
        }
    }

    /**
     * Validates that all fields are populated and then saves updates to database if valid.
     * @param actionEvent fires when save updates is clicked.
     * @throws IOException from database connection.
     */
    public void SaveChanges(ActionEvent actionEvent) throws IOException {

        boolean valid = false;

        if (comboCountry.getValue() != null && comboFirstDiv.getValue() != null) {
            int id = Integer.valueOf(textFieldCustomerId.getText());
            String name = textFieldCustomerName.getText();
            String number = textFieldPhoneNumber.getText();
            String address = textFieldAddress.getText();
            String postalCode = textFieldPostalCode.getText();
            String country = comboCountry.getValue().toString();
            String division = comboFirstDiv.getValue().toString();


            valid = (!(name.isBlank()) &&
                    !(number.isBlank()) &&
                    !(address.isBlank()) &&
                    !(postalCode.isBlank()) &&
                    !(comboCountry.getSelectionModel().isEmpty()) &&
                    !(comboFirstDiv.getSelectionModel().isEmpty()));

            if (valid) {
                int divisionId = Main.dbDivisions.getIdFromName(division);
                Customer customer = new Customer(id, name, address, postalCode, number, customerBeingChanged.getCreatedDate(), customerBeingChanged.getCreatedBy(), customerBeingChanged.getLastUpdate(), Main.user, divisionId);
                Main.dbCustomers.update(customer);
                LoadTable();

                labelFeedback.setTextFill(Color.DARKGREEN);
                labelFeedback.setText("Customer Updated Successfully.");
            }
            else{
                labelFeedback.setTextFill(Color.RED);
                labelFeedback.setText("Please check that all \n fields have a valid entry.");
            }
        }
        else {
            labelFeedback.setTextFill(Color.RED);
            labelFeedback.setText("Please check that all \n fields have a valid entry.");
        }


    }

    /**
     * Resets the edit form to the original data or blank if a new customer is selected.
     * @param actionEvent fired from cancel changes.
     */
    public void CancelChanges(ActionEvent actionEvent) {
        Customer selectedCustomer = tableCustomerRecords.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            if (customerBeingChanged == selectedCustomer) {

            ObservableList<Country> countries = Main.dbCountries.getAllCountries();
            // Lambda 3
            ObservableList<String> countryNames = countries.stream()
                    .map(c -> c.getCountryName())
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            comboCountry.setItems(countryNames);

            ObservableList<String> possibleDivisions = Main.dbDivisions.getDivisionsFromCountry(selectedCustomer.getCountry());

            if (!possibleDivisions.isEmpty()) {
                comboFirstDiv.setItems(possibleDivisions);
            } else {
                labelFeedback.setTextFill(Color.RED);
                labelFeedback.setText("No Divisions Found in Country.");
            }


            textFieldCustomerId.setText("" + selectedCustomer.getCustomerId() + "");
            textFieldCustomerName.setText(selectedCustomer.getCustomerName());
            textFieldAddress.setText(selectedCustomer.getAddress());
            textFieldPhoneNumber.setText(selectedCustomer.getPhone());
            textFieldPostalCode.setText(selectedCustomer.getPostalCode());
            textFieldCustomerId.setDisable(true);
            comboCountry.setValue(selectedCustomer.getCountry());
            comboFirstDiv.setValue(selectedCustomer.getDivision());

            labelFeedback.setTextFill(Color.DARKGREEN);
            labelFeedback.setText("Values reset.");
        }
        else {
            textFieldCustomerId.setText("");
            textFieldCustomerName.setText("");
            textFieldAddress.setText("");
            textFieldPhoneNumber.setText("");
            textFieldPostalCode.setText("");
            textFieldCustomerId.setDisable(true);
            comboCountry.setValue(null);
            comboFirstDiv.setValue(null);

            textFieldCustomerName.setDisable(true);
            textFieldAddress.setDisable(true);
            textFieldPostalCode.setDisable(true);
            textFieldPhoneNumber.setDisable(true);
            comboCountry.setDisable(true);
            comboFirstDiv.setDisable(true);


         }
        }
    }

    /**
     * Navigates user back to main menu.
     * @param actionEvent fired when back is clicked.
     * @throws IOException from reading in fxml location.
     */
    public void BackToMainMenu(ActionEvent actionEvent) throws IOException {
        Scene scene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/main-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(tempParent);
        stage.setScene(scene);
        stage.centerOnScreen();
    }


    /**
     * Updates the Division selection to only display divisions from the selected country.
     * @param actionEvent fires when the country combo box is selected.
     */
    public void UpdateDivisionSelection(ActionEvent actionEvent){
        if (comboCountry.getValue() != null) {
            selectedCountry = comboCountry.getValue().toString();
            ObservableList<String> possibleDivisions = Main.dbDivisions.getDivisionsFromCountry(selectedCountry);

            if (!possibleDivisions.isEmpty()) {
                comboFirstDiv.setItems(possibleDivisions);
            } else {
                labelFeedback.setTextFill(Color.RED);
                labelFeedback.setText("No Divisions Found in Country.");
            }
        }
    }
}
