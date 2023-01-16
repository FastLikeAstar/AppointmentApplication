package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.User;
import sample.Jdbc;
import java.sql.*;


public class UserDaoImpl implements UserDao{

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


        return validLogin;
    }

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
