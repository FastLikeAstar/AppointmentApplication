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
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewCustomerController implements Initializable {

    @FXML
    public ComboBox comboFirstDiv;
    @FXML
    public ComboBox comboCountry;
    @FXML
    public TextField textFieldPostalCode;
    @FXML
    public TextField textFieldAddress;
    @FXML
    public TextField textFieldPhoneNumber;
    @FXML
    public TextField textFieldName;
    @FXML
    public Label labelFeedback;
    @FXML
    DialogPane newCustomerForm;

    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;


    /**
     * Loads up form and country fills combo box while disabling the division combo box.
     *
     * Lambda 1: Lambda is used to increase readability and maintainability of the code by using parsing through a list
     * of countries instead of writing an addition method in the CountryDaoImpl class that returns the names.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelFeedback.setText("");
        ObservableList<Country> countries = Main.dbCountries.getAllCountries();
        // Lambda 1
        ObservableList<String> countryNames = countries.stream()
                .map(c -> c.getCountryName())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        comboCountry.setItems(countryNames);

        comboFirstDiv.setDisable(true);
    }


    /**
     * Returns the user back to the Customer Records without saving a new customer.
     * @param actionEvent from cancel button fired.
     * @throws IOException from loading fxml file.
     */
    public void NavToCustomerRecords(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-records.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    /**
     * Validates all fields are filled out and then saves the customer to database if valid.
     * Navigates back to customer records if successful.
     * @param actionEvent from save button clicked.
     * @throws IOException from loading fxml file.
     */
    public void SaveData(ActionEvent actionEvent) throws IOException {
        boolean valid = false;

        if (comboCountry.getValue() != null && comboFirstDiv.getValue() != null) {
            String name = textFieldName.getText();
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
                Customer customer = new Customer(-1, name, address, postalCode, number, null, Main.user, null, Main.user, divisionId);
                Main.dbCustomers.save(customer);

                // Navigate back
                Scene productScene;
                Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/customer-records.fxml"));
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

    /**
     * Updates the division combo box with divisions that relate to the selected country.
     * Enables the combo box to allow users to select a division now the country box has a value.
     * @param actionEvent from selecting a country in the country combo box.
     */
    public void UpdateDivisionSelection(ActionEvent actionEvent){
        comboFirstDiv.setDisable(false);
        String country = comboCountry.getValue().toString();
        ObservableList<String> possibleDivisions = Main.dbDivisions.getDivisionsFromCountry(country);

        if (!possibleDivisions.isEmpty()){
            comboFirstDiv.setItems(possibleDivisions);
        }
        else{
            labelFeedback.setText("No Divisions Found in Country.");
        }
    }
}
