package dao;

import JDBC.Jdbc;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Country;
import sample.Customer;



import java.sql.*;
import java.time.ZonedDateTime;
import java.util.List;

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
        return null;
    }

    @Override
    public void save(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(int id) {

    }
}
