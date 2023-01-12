package dao;

import sample.Jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Customer;



import java.sql.*;

public class CustomerDaoImpl implements CustomerDao{


    /**
     * Method to retrieve all customers from the Database.
     * @return an ObservableList of Customer containing all customers.
     */
    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int customerId = results.getInt("customer_id");
                String customerName = results.getString("customer_name");
                String address = results.getString("address");
                String zipCode = results.getString("postal_code");
                String phone = results.getString("phone");
                Timestamp createdDate = results.getTimestamp("create_date");
                String createdBy = results.getString("created_by");
                Timestamp lastUpdate = results.getTimestamp("last_update");
                String lastUpdatedBy = results.getString("last_updated_by");
                int divisionId = results.getInt("division_id");

                Customer customer = new Customer(customerId, customerName, address, zipCode, phone, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                customerList.add(customer);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return customerList;
    }

    @Override
    public Customer getById(int id) {

        Customer customerIfExists = null;
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

                int customerId = results.getInt("customer_id");
                String customerName = results.getString("customer_name");
                String address = results.getString("address");
                String zipCode = results.getString("postal_code");
                String phone = results.getString("phone");
                Timestamp createdDate = results.getTimestamp("create_date");
                String createdBy = results.getString("created_by");
                Timestamp lastUpdate = results.getTimestamp("last_update");
                String lastUpdatedBy = results.getString("last_updated_by");
                int divisionId = results.getInt("division_id");

                customerIfExists = new Customer(customerId, customerName, address, zipCode, phone, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return customerIfExists;
    }

    @Override
    public int save(Customer customer) {
        int affectedRows = -1;
        String sql = "INSERT INTO customers "+
                "(customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, division_id)"+
                " VALUES (?,?,?,?,NOW(),?,NOW(),?,?)";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,customer.getCustomerName());
            statement.setString(2,customer.getAddress());
            statement.setString(3,customer.getPostalCode());
            statement.setString(4,customer.getPhone());
            statement.setString(5,customer.getCreatedBy());
            statement.setString(6,customer.getLastUpdatedBy());
            statement.setInt(7,customer.getDivisionId());

            affectedRows = statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            customer.setCustomerId(generatedKey.getInt(1));

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
    public int update(Customer customer) {
        int affectedRows = -1;
        String sql = "UPDATE customers " +
                "SET customer_name = ?, " +
                "SET address = ?, " +
                "SET postal_code = ?, " +
                "SET phone = ?, " +
                "SET last_update = NOW(), " +
                "SET last_updated_by = ?, " +
                "SET division_id = ? "+
                "WHERE id = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,customer.getCustomerName());
            statement.setString(2,customer.getAddress());
            statement.setString(3,customer.getPostalCode());
            statement.setString(4,customer.getPhone());
            statement.setString(5,customer.getLastUpdatedBy());
            statement.setInt(6,customer.getDivisionId());
            statement.setInt(7,customer.getCustomerId());

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

        String sql = "DELETE FROM customers WHERE id = ?";

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
