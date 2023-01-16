package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Tags from Scene:
 * LabelWelcome
 * LabelLogin
 * LabelLocation
 * LabelUsername
 * LabelPassword
 * TextFieldUsername
 * TextFieldPassword
 * LabelFeedback
 * ButtonLogin, AttemptLogin
 * LabelActualLocation
 */
public class LoginController implements Initializable {

    Locale locale;
    String languageCode;
    String countryName;

    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelLogin;
    @FXML
    private Label labelLocation ;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Label labelFeedback;
    @FXML
    private Button buttonLogin;
    @FXML
    private Label labelActualLocation;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locale = Locale.getDefault();
        //Locale locale = new Locale("fr", "CA"); // Test Locale.
        languageCode = locale.getLanguage();

        if (languageCode.contentEquals("fr")){
                labelWelcome.setText("Bienvenu");
                labelLogin.setText("Connectez-vous pour continuer");
                labelLocation.setText("Emplacement:");
                labelUsername.setText("Nom d’utilisateur");
                labelPassword.setText("Mot de passe");
                textFieldUsername.setPromptText("Entrez votre nom d’utilisateur...");
                textFieldPassword.setPromptText("Entrez votre mot de passe..");
                buttonLogin.setText("connectez-vous");
        }
        labelActualLocation.setText(ZoneId.systemDefault().toString());
        labelFeedback.setText("");

    }

    public void AttemptLogin(ActionEvent actionEvent) throws IOException {
        boolean successful = false;

        String enteredUsername = textFieldUsername.getText();
        String enteredPassword = textFieldPassword.getText();

        successful = Main.dbUsers.login(enteredUsername, enteredPassword);

        if (successful)
        {
            Main.user = enteredUsername;
            Scene mainMenuScene;
            Parent tempParent = FXMLLoader.load(Main.class.getResource("/main-menu.fxml"));
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            mainMenuScene = new Scene(tempParent);
            stage.setScene(mainMenuScene);
            stage.centerOnScreen();
        }
        else {
            if (languageCode.contentEquals("fr")) {
                labelFeedback.setText("Connexion incorrecte...");
            } else {
                labelFeedback.setText("Incorrect login...");
            }
        }

    }
}
