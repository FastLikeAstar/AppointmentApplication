package dao;

import javafx.collections.ObservableList;
import sample.Appointment;

import java.time.ZonedDateTime;

public interface AppointmentDao {

    ObservableList<Appointment> getAllAppointments();
    Appointment getById(int id);
    int save(Appointment appointment);
    int update(Appointment appointment);
    void delete(int id);
}
