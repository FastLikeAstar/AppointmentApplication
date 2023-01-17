package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Appointment;
import sample.AppointmentReport;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerAppointmentReportController implements Initializable {
    public TableView TableCustomerAppointments;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        ObservableList<Appointment> appointmentsToShow = Main.dbAppointments.getAllAppointments();
        ObservableList<AppointmentReport> allReports = FXCollections.observableArrayList();
        List<String> allTypes = new ArrayList<>();
        for (Appointment appointment : appointmentsToShow){
            String appointmentType = appointment.getType();
            if (!allTypes.contains(appointmentType)){
                allTypes.add(appointmentType);
            }
        }
        for (String type: allTypes){
            for (int i = 1 ;i <13 ; i++){
                int count = Main.dbAppointments.getAppointmentCountByTypeAndMonth(type, i);
                AppointmentReport reportToAdd = new AppointmentReport(count, i, type);
                allReports.add(reportToAdd);
            }
        }


        TableColumn<AppointmentReport, Integer> columnMonth = new TableColumn<>("Month");
        TableColumn<AppointmentReport, String> columnType = new TableColumn<>("Type");
        TableColumn<AppointmentReport, Integer> columnCount = new TableColumn<>("Count");


        columnMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnCount.setCellValueFactory(new PropertyValueFactory<>("count"));



        TableCustomerAppointments.getColumns().addAll(columnMonth, columnType, columnCount);

        TableCustomerAppointments.setItems(allReports);

    }

    public void BackToReportsMenu(ActionEvent actionEvent) throws IOException {
        Scene productScene;
        Parent tempParent = (Parent) FXMLLoader.load(Main.class.getResource("/reports-menu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        productScene = new Scene(tempParent);
        stage.setScene(productScene);
        stage.centerOnScreen();
    }
}
