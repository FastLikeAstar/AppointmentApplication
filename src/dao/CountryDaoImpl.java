package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Country;
import sample.Jdbc;

import java.sql.*;

public class CountryDaoImpl implements CountryDao{

    /**
     * Gets all countries in the database.
     * @return Observable List to possibly display in JavaFX.
     */
    @Override
    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int countryId = results.getInt("Country_ID");
                String countryName = results.getString("Country");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");

                Country country = new Country(countryId, countryName, createdDate, createdBy, lastUpdate, lastUpdatedBy);
                countryList.add(country);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return countryList;
    }

    /**
     * Gets country information from a country id.
     * @param id provided country id.
     * @return country information in country object.
     */
    @Override
    public Country getById(int id) {
        Country countryIfExists = null;
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                int countryId = results.getInt("Country_ID");
                String countryName = results.getString("Country");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");

                countryIfExists = new Country(countryId, countryName, createdDate, createdBy, lastUpdate, lastUpdatedBy);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return countryIfExists;
    }

    /**
     * Creates a new country record in the database.
     * @param country information to be saved as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int save(Country country) {
        int affectedRows = -1;
        String sql = "INSERT INTO countries "+
                "(Country, Create_Date, Created_By, Last_Update, Last_Updated_By)"+
                " VALUES (?,NOW(),?,NOW(),?)";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,country.getCountryName());
            statement.setString(2,country.getCreatedBy());
            statement.setString(3,country.getLastUpdatedBy());

            affectedRows = statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            country.setCountryId(generatedKey.getInt(1));

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not create country. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    /**
     * Updates a country already recorded in the database.
     * @param country information to be updated as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int update(Country country) {
        int affectedRows = -1;
        String sql = "UPDATE countries " +
                "SET Country = ?, " +
                "SET Last_Update = NOW(), " +
                "SET Last_Updated_By = ?, " +
                "WHERE Country_ID = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,country.getCountryName());
            statement.setString(2,country.getLastUpdatedBy());
            statement.setInt(3,country.getCountryId());

            affectedRows = statement.executeUpdate();

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not update country. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    /**
     * Deletes a country record by id.
     * @param id of country to delete.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM countries WHERE Country_ID = ?";

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
     * Gets the country id from a country's name
     * @param name of country.
     * @return id of country.
     */
    public int getIdFromName(String name) {
        int countryId = -1;
        String sql = "SELECT Country_ID FROM countries WHERE Country = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                countryId = results.getInt("Country_ID");
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return countryId;
    }
}
