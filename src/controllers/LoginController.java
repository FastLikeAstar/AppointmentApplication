package controllers;

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
        languageCode = locale.getLanguage();
        countryName = locale.getDisplayCountry();
        //        Locale montreal = new Locale("fr", "CA");
        if (languageCode.contentEquals("fr")){
                labelWelcome.setText("Bienvenu");
                labelLogin.setText("Connectez-vous pour continuer");
                labelLocation.setText("Emplacement:");
                labelUsername.setText("Nom d’utilisateur");
                labelPassword.setText("Mot de passe");
                textFieldUsername.setPromptText("Entrez votre nom d’utilisateur...");
                textFieldPassword.setPromptText("Entrez votre mot de passe..");
                buttonLogin.setText("connectez-vous");
                labelActualLocation.setText(countryName);
        }

    }

    public void AttemptLogin(ActionEvent actionEvent) throws IOException {
        boolean successful = false;




        successful = true;
        if (successful)
        {
            Scene partScene;
            FXMLLoader controllerLoader = new FXMLLoader();
            controllerLoader.setLocation(getClass().getResource("/main-menu.fxml"));
            Parent tempParent = controllerLoader.load(Main.class.getResource("/main-menu.fxml"));
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            partScene = new Scene(tempParent);
            stage.setScene(partScene);
        }
        else{
            labelFeedback.setText("Connexion incorrecte...");
        }
    }
}
