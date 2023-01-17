package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.FirstLevelDivision;
import sample.Jdbc;

import java.sql.*;

public class FirstLevelDivisionDaoImpl implements FirstLevelDivisionDao{

    /**
     * Gets all divisions in the database.
     * @return Observable List to possibly display in JavaFX.
     */
    @Override
    public ObservableList<FirstLevelDivision> getAllDivisions() {
        ObservableList<FirstLevelDivision> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int divisionId = results.getInt("Division_ID");
                String divisionName = results.getString("Division");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                int countryId = results.getInt("Country_ID");

                FirstLevelDivision division = new FirstLevelDivision(divisionId, divisionName, createdDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                divisionList.add(division);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return divisionList;
    }

    /**
     * Gets division information from a division id.
     * @param id provided division id.
     * @return division information in division object.
     */
    @Override
    public FirstLevelDivision getById(int id) {
        FirstLevelDivision divisionIfExists = null;
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";



        try {

            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();


            while(results.next()) {
                int divisionId = results.getInt("Division_ID");

                String divisionName = results.getString("Division");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");

                String lastUpdatedBy = results.getString("Last_Updated_By");

                int countryId = results.getInt("Country_ID");



                divisionIfExists = new FirstLevelDivision(divisionId, divisionName, createdDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                if (divisionIfExists == null) {

                }
            }
        } catch (SQLException throwable) {

            throwable.printStackTrace();
        }

        return divisionIfExists;
    }


    /**
     * Creates a new division record in the database.
     * @param division information to be saved as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int save(FirstLevelDivision division) {
        int affectedRows = -1;
        String sql = "INSERT INTO first_level_divisions "+
                "(Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID)"+
                " VALUES (?,NOW(),?,NOW(),?, ?)";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,division.getDivisionName());
            statement.setString(2,division.getCreatedBy());
            statement.setString(3,division.getLastUpdatedBy());
            statement.setInt(4,division.getCountryId());

            affectedRows = statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            division.setDivisionId(generatedKey.getInt(1));

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not create division. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }


    /**
     * Updates a division already recorded in the database.
     * @param division information to be updated as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int update(FirstLevelDivision division) {
        int affectedRows = -1;
        String sql = "UPDATE first_level_divisions " +
                "SET Division = ?, " +
                "SET Last_Update = NOW(), " +
                "SET Last_Updated_By = ?, " +
                "SET Country_ID = ?, " +
                "WHERE Division_ID = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,division.getDivisionName());
            statement.setString(2,division.getLastUpdatedBy());
            statement.setInt(3,division.getCountryId());
            statement.setInt(4,division.getDivisionId());

            affectedRows = statement.executeUpdate();

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not update division. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    /**
     * Deletes a division record by id.
     * @param id of division to delete.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM first_level_divisions WHERE Division_ID = ?";

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
     * Returns the id of a division from its name.
     * @param name of division
     * @return id of division.
     */
    public int getIdFromName(String name) {
        int divisionId = -1;
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                divisionId = results.getInt("Division_ID");
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return divisionId;
    }

    /**
     * Gets a list of all divisions from a country's name.
     * @param country's name.
     * @return list of divisions in country.
     */
    public ObservableList<String> getDivisionsFromCountry(String country){
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT first_level_divisions.Division "+
                "FROM first_level_divisions " +
                "JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID " +
                "WHERE countries.Country = ?";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,country);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                String divisionName = results.getString("Division");
                divisionList.add(divisionName);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return divisionList;
    }
}
