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
        String sql = "SELECT * FROM customers " +
                    "WHERE NOT EXISTS " +
                    "(SELECT * FROM customers " +
                    "WHERE customers.Customer_Name IS NULL)";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int customerId = results.getInt("Customer_ID");
                String customerName = results.getString("Customer_Name");
                String address = results.getString("Address");
                String zipCode = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                int divisionId = results.getInt("Division_Id");

                Customer customer = new Customer(customerId, customerName, address, zipCode, phone, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                customerList.add(customer);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return customerList;
    }

    /**
     * Gets customer information from an customer id.
     * @param id provided customer id.
     * @return customer information in customer object.
     */
    @Override
    public Customer getById(int id) {

        Customer customerIfExists = null;
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

                int customerId = results.getInt("Customer_ID");
                String customerName = results.getString("Customer_Name");
                String address = results.getString("Address");
                String zipCode = results.getString("Postal_Code");
                String phone = results.getString("Phone");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_By");
                Timestamp lastUpdate = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                int divisionId = results.getInt("Division_ID");

                customerIfExists = new Customer(customerId, customerName, address, zipCode, phone, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return customerIfExists;
    }

    /**
     * Creates a new customer record in the database.
     * @param customer information to be saved as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int save(Customer customer) {
        int affectedRows = -1;
        String sql = "INSERT INTO customers "+
                "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)"+
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
            generatedKey.next();
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

    /**
     * Updates a customer already recorded in the database.
     * @param customer information to be updated as a record.
     * @return returns the rows affected if verification is needed.
     */
    @Override
    public int update(Customer customer) {
        int affectedRows = -1;
        String sql = "UPDATE customers " +
                "SET Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Last_Update = NOW(), " +
                "Last_Updated_By = ?, " +
                "Division_ID = ? "+
                "WHERE Customer_ID = ?";

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

    /**
     * Deletes a customer record by id.
     * @param id of customer to delete.
     */
    @Override
    public void delete(int id) {

        String sql = "DELETE FROM customers WHERE Customer_ID = ?";

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
     * Gets a list of all customer ids
     * @return list of customer ids
     */
    public ObservableList<Integer> getAllCustomerIds() {
        ObservableList<Integer> customerList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID FROM customers " +
                "WHERE NOT EXISTS " +
                "(SELECT * FROM customers " +
                "WHERE customers.Customer_Name IS NULL)";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int customerId = results.getInt("Customer_ID");


                customerList.add(customerId);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return customerList;
    }

    /**
     * Counts how many customers have a create_date of within a year from today.
     * @return count of customers acquired the past year.
     */
    public int getCountCustomersLastYear() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM customers WHERE Create_Date >= DATE_SUB(NOW(), INTERVAL 1 YEAR)";
        try (Connection connection = Jdbc.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery(sql);
            if (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
