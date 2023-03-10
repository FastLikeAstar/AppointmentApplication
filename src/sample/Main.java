package sample;

import dao.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Main extends Application {
    public static String user = "";
    public static AppointmentDaoImpl dbAppointments;
    public static ContactDaoImpl dbContacts;
    public static CountryDaoImpl dbCountries;
    public static CustomerDaoImpl dbCustomers;
    public static FirstLevelDivisionDaoImpl dbDivisions;
    public static UserDaoImpl dbUsers;



    public static int START_OF_DAY = 8; // EST
    public static int END_OF_DAY = 22; // EST


    /**
     * Starts the JavaFX rending by opening the initial stage.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent mainMenu = loader.load(Main.class.getResource("/login.fxml"));



        FXMLLoader controllerLoader = new FXMLLoader();
        controllerLoader.setLocation(getClass().getResource("/login.fxml"));
        Scene mainScene = new Scene(mainMenu);

        stage.setTitle("Appointment Manager");
        stage.setScene(mainScene);
        stage.show();
    }

    /**
     * Programs main.
     * Opens the database connections for static fields.
     * @param args
     */
    public static void main(String[] args) {
        Jdbc.openConnection();
        dbAppointments = new AppointmentDaoImpl();
        dbContacts = new ContactDaoImpl();
        dbCountries = new CountryDaoImpl();
        dbCustomers = new CustomerDaoImpl();
        dbDivisions = new FirstLevelDivisionDaoImpl();
        dbUsers = new UserDaoImpl();

        launch();
        Jdbc.closeConnection();
    }
}
