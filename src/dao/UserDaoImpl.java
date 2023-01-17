package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.User;
import sample.Jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;


public class UserDaoImpl implements UserDao{

    /**
     * Gets all users in the database.
     * @return Observable List to possibly display in JavaFX.
     */
    @Override
    public ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int userId = results.getInt("User_ID");
                String userName = results.getString("User_Name");
                String password = results.getString("Password");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");

                User user = new User(userId, userName, password, createdDate, createdBy, lastUpdate, lastUpdatedBy);
                userList.add(user);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return userList;
    }

    /**
     * Gets user information from a user id.
     * @param id provided user id.
     * @return user information in user object.
     */
    @Override
    public User getById(int id) {
        User userIfExists = null;
        String sql = "SELECT * FROM users WHERE User_ID = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                int userId = results.getInt("User_ID");
                String userName = results.getString("User_Name");
                String password = results.getString("Password");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");

                userIfExists = new User(userId, userName, password, createdDate, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return userIfExists;
    }

    /**
     * Creates a new user record in the database.
     * @param user information to be saved as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int save(User user) {


        int affectedRows = -1;
        String sql = "INSERT INTO users "+
                "(User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By)"+
                " VALUES (?, ?, NOW(), ?, NOW(), ?)";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,user.getUserName());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getCreatedBy());
            statement.setString(4,user.getLastUpdatedBy());

            affectedRows = statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            user.setUserId(generatedKey.getInt(1));

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not create user. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    /**
     * Updates a user already recorded in the database.
     * @param user information to be updated as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int update(User user) {
        int affectedRows = -1;
        String sql = "UPDATE users " +
                "SET User_Name = ?, " +
                "SET Password = ?, " +
                "SET Last_Update = NOW(), " +
                "SET Last_Updated_By = ?, " +
                "WHERE User_ID = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,user.getUserName());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getLastUpdatedBy());
            statement.setInt(4,user.getUserId());

            affectedRows = statement.executeUpdate();

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not update user. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    /**
     * Deletes a user record by id.
     * @param id of user to delete.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE User_ID = ?";

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
     * Validates a login attempt by seeing if both the username and password match with information in the database.
     * Records the login attempt in a login attempt log.
     * @param username user inputted username.
     * @param password user inputted password.
     * @return success or failure of login.
     */
    public boolean login(String username, String password){
        boolean validLogin = false;
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();

            validLogin = results.next();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


        final String FILENAME = "login_activity.txt";
        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(FILENAME, true);
            LocalDateTime now = LocalDateTime.now();
            String date = now.toLocalDate().toString();
            String time = now.toLocalTime().toString();
            String status = validLogin ? "SUCCESS" : "FAILURE";
            fileWriter.append(username + "," + date + "," + time + "," + status + "\n");
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



        return validLogin;
    }

    /**
     * Gets a list of all user ids in the database.
     * @return list of user ids.
     */
    public ObservableList<Integer> getAllUserIds() {
        ObservableList<Integer> userList = FXCollections.observableArrayList();
        String sql = "SELECT User_ID FROM users";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int userId = results.getInt("User_ID");

                userList.add(userId);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return userList;
    }

    /**
     * Gets the id of a user by their name.
     * @param name of user.
     * @return id of user.
     */
    public int getIdByName(String name) {
        int userId = -1;
        String sql = "SELECT User_ID FROM users WHERE User_Name = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                userId = results.getInt("User_ID");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return userId;
    }
}
