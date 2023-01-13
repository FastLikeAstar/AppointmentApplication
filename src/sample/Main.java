package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Main extends Application {
    public static String user = "";



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
//        HelloApplication.class.getResource("hello-view.fxml")
        Parent mainMenu = loader.load(Main.class.getResource("/login.fxml"));



        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/login.fxml"));
//        Parent tempParent = (Parent) controllerLoader.load();

        Scene mainScene = new Scene(mainMenu);

        stage.setTitle("Appointment Manager");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        Jdbc.openConnection();
        launch();
        Jdbc.closeConnection();
    }
}
