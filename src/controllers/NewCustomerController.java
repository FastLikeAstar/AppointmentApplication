package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    @FXML
    DialogPane newCustomerForm;

    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;


    /**
     * Lambda 1's Justification: Inorder to assign button values to the DialogPane I need access to the stage, but I
     * need to wait until the state has been initialized. By using a lambda function to set the method of an event call
     * I maintain the order of execution for the application and increase maintainability.
     *
     * Lambda 2's Justification: Using lambda to assign the stage.close() function to the cancel button event increases
     * readability and thus maintainability.
     *
     * Lambda 3's Justification: Using lambda to assign the save function to the finish button event increases
     * readability and thus maintainability.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void assignButtonEvents(){

//        cancelButton.setOnAction(onClick -> { closeWithoutSaving(); });
//        saveButton.setOnAction(onClick -> { saveData(); });
    }


    public void NavToCustomerRecords(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/customer-records.fxml"));
        Parent tempParent = (Parent) controllerLoader.load(Main.class.getResource("/customer-records.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }

    public void SaveData(ActionEvent actionEvent) {


    }
}
