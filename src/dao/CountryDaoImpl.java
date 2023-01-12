package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Country;
import sample.Jdbc;

import java.sql.*;

public class CountryDaoImpl implements CountryDao{

    @Override
    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int countryId = results.getInt("country_id");
                String countryName = results.getString("country");
                Timestamp createdDate = results.getTimestamp("create_date");
                String createdBy = results.getString("created_by");
                Timestamp lastUpdate = results.getTimestamp("last_update");
                String lastUpdatedBy = results.getString("last_updated_by");

                Country country = new Country(countryId, countryName, createdDate, createdBy, lastUpdate, lastUpdatedBy);
                countryList.add(country);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return countryList;
    }

    @Override
    public Country getById(int id) {
        Country countryIfExists = null;
        String sql = "SELECT * FROM countries WHERE country_id = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            int countryId = results.getInt("country_id");
            String countryName = results.getString("country");
            Timestamp createdDate = results.getTimestamp("create_date");
            String createdBy = results.getString("created_by");
            Timestamp lastUpdate = results.getTimestamp("last_update");
            String lastUpdatedBy = results.getString("last_updated_by");

            countryIfExists = new Country(countryId, countryName, createdDate, createdBy, lastUpdate, lastUpdatedBy);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return countryIfExists;
    }

    @Override
    public int save(Country country) {
        int affectedRows = -1;
        String sql = "INSERT INTO countries "+
                "(country, create_date, created_by, last_update, last_updated_by)"+
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

    @Override
    public int update(Country country) {
        int affectedRows = -1;
        String sql = "UPDATE countries " +
                "SET country = ?, " +
                "SET last_update = NOW(), " +
                "SET last_updated_by = ?, " +
                "WHERE id = ?";

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

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM countries WHERE id = ?";

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
