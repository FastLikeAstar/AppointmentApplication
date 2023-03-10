package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Appointment;
import sample.Jdbc;
import sample.Main;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;

public class AppointmentDaoImpl implements AppointmentDao{

    /**
     * Gets all appointments in the database.
     * @return Observable List to possibly display in JavaFX.
     */
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

    /**
     * Gets appointment information from an appointment id.
     * @param id provided appointment id.
     * @return appointment information in appointment object.
     */
    @Override
    public Appointment getById(int id) {
        Appointment appointmentIfExists = null;
        String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
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

                appointmentIfExists = new Appointment(appointmentId, appointmentName, description, location, type, startTime, endTime, createdDate, createdBy, lastUpdate, lastUpdateBy, customerId, userId, contactId);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return appointmentIfExists;
    }

    /**
     * Creates a new appointment record in the database.
     * @param appointment information to be saved as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int save(Appointment appointment) {
        int affectedRows = -1;
        String sql = "INSERT INTO appointments "+
                "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)"+
                " VALUES (?, ?, ?, ?, ?,?, NOW(), ?, NOW(), ?, ?, ?, ?)";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, appointment.getStartTime());
            statement.setTimestamp(6, appointment.getEndTime());
            statement.setString(7, Main.user);
            statement.setString(8, Main.user);
            statement.setInt(9, appointment.getCustomerId());
            statement.setInt(10, appointment.getUserId());
            statement.setInt(11, appointment.getContactId());


            affectedRows = statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            generatedKey.next();
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

    /**
     * Updates an appointment already recorded in the database.
     * @param appointment information to be updated as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int update(Appointment appointment) {
        int affectedRows = -1;
        String sql = "UPDATE appointments " +
                "SET Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Last_Update = NOW(), " +
                "Last_Updated_By = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, appointment.getStartTime());
            statement.setTimestamp(6, appointment.getEndTime());
            statement.setString(7, appointment.getLastUpdatedBy());
            statement.setInt(8, appointment.getCustomerId());
            statement.setInt(9, appointment.getUserId());
            statement.setInt(10, appointment.getContactId());
            statement.setInt(11, appointment.getAppointmentId());

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

    /**
     * Deletes an appointment record by id.
     * @param id of appointment to delete.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,id);

            statement.executeUpdate();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Gets a list of upcoming appointments occurring in the next 15 minutes.
     * @return List of appointments in JavaFX ObservableList (for easy display).
     */
    public ObservableList<Appointment> getUpcomingAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments " +
                "WHERE Start <= DATE_ADD(NOW(), INTERVAL 15 MINUTE) " +
                "AND End >= NOW();";
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

    /**
     * Gets a list of upcoming appointments occurring in the next month.
     * @return List of appointments in JavaFX ObservableList (for easy display).
     */
    public ObservableList<Appointment> getNextMonthOfAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments " +
                "WHERE Start BETWEEN DATE_ADD(NOW(), INTERVAL 1 MONTH) " +
                "AND DATE_ADD(NOW(), INTERVAL 2 MONTH);";
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

    /**
     * Gets a list of upcoming appointments occurring in the next week.
     * @return List of appointments in JavaFX ObservableList (for easy display).
     */
    public ObservableList<Appointment> getNextWeekOfAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments " +
                "WHERE Start BETWEEN DATE_ADD(NOW(), INTERVAL 1 WEEK) " +
                "AND DATE_ADD(NOW(), INTERVAL 2 WEEK);";
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


    /**
     * Gets all appointments related to a specific customer.
     * @param customerId of customer that appointments relate to.
     * @return List of appointments in JavaFX ObservableList (for easy display).
     */
    public ObservableList<Appointment> getCustomerAppointments(int customerId) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
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


    /**
     * Gets a count of appointments occurring in a month and of a specific type.
     * @param type of appointment to look for.
     * @param month when to look for that appointment.
     * @return total occurrences of that appointment type given the month.
     */
    public int getAppointmentCountByTypeAndMonth(String type, int month){
        int count = 0;

        String sql = "SELECT COUNT(*) FROM appointments WHERE Type = ? AND MONTH(Start) = ?";
        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            statement.setInt(2, month);
            ResultSet results = statement.executeQuery();
            if (results.next()){
                count = results.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return count;
    }


    /**
     * Gets a list of appointments that relate to a specific contact.
     * @param givenContactId the contact related to the appointments.
     * @return list of all the contact's appointments.
     */
    public ObservableList<Appointment> getAppointmentsForContact(int givenContactId) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, givenContactId);
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
}

