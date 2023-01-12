package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Appointment;
import sample.Jdbc;

import java.sql.*;
import java.time.ZonedDateTime;

public class AppointmentDaoImpl implements AppointmentDao{
    @Override
    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int appointmentId = results.getInt("Appointment_ID");
                String appointmentName = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                Timestamp startTime = results.getTimestamp("Start");
                Timestamp endTime = results.getTimestamp("End");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdateBy = results.getString("Last_Updated_By");
                int customerId = results.getInt("Customer_ID");
                int userId = results.getInt("User_ID");
                int contactId = results.getInt("Contact_ID");




                Appointment appointment = new Appointment(appointmentId, appointmentName, description, location, type, startTime, endTime, createdDate,createdBy, lastUpdate, lastUpdateBy, customerId,userId,contactId);
                appointmentList.add(appointment);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return appointmentList;
    }

    @Override
    public Appointment getById(int id) {
        Appointment appointmentIfExists = null;
        String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            int appointmentId = results.getInt("Appointment_ID");
            String appointmentName = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            Timestamp startTime = results.getTimestamp("Start");
            Timestamp endTime = results.getTimestamp("End");
            Timestamp createdDate = results.getTimestamp("Create_Date");
            String createdBy = results.getString("Created_By");
            Timestamp lastUpdate = results.getTimestamp("Last_Update");
            String lastUpdateBy = results.getString("Last_Updated_By");
            int customerId = results.getInt("Customer_ID");
            int userId = results.getInt("User_ID");
            int contactId = results.getInt("Contact_ID");

            appointmentIfExists = new Appointment(appointmentId, appointmentName, description, location, type, startTime, endTime, createdDate,createdBy, lastUpdate, lastUpdateBy, customerId,userId,contactId);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return appointmentIfExists;
    }

    @Override
    public int save(Appointment appointment) {
        int affectedRows = -1;
        String sql = "INSERT INTO appointments "+
                "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"+
                " VALUES (t?, d?, l?, t?, s?,e?, NOW(), c?, NOW(), l?, c?, u?, c?)";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, appointment.getStartTime());
            statement.setTimestamp(6, appointment.getEndTime());
            statement.setString(7, appointment.getCreatedBy());
            statement.setString(8, appointment.getLastUpdatedBy());
            statement.setInt(9, appointment.getCustomerId());
            statement.setInt(10, appointment.getUserId());
            statement.setInt(11, appointment.getContactId());


            affectedRows = statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            appointment.setAppointmentId(generatedKey.getInt(1));

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not create appointment. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    @Override
    public int update(Appointment appointment) {
        int affectedRows = -1;
        String sql = "UPDATE appointments " +
                "SET appointment_name = ?, " +
                "SET email = ?, " +
                "WHERE id = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, appointment.getStartTime());
            statement.setTimestamp(6, appointment.getEndTime());
            statement.setString(8, appointment.getLastUpdatedBy());
            statement.setInt(9, appointment.getCustomerId());
            statement.setInt(10, appointment.getUserId());
            statement.setInt(11, appointment.getContactId());

            affectedRows = statement.executeUpdate();

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not update appointment. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,id);

            statement.executeUpdate();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
