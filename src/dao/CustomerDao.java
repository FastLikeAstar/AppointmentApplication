package dao;

import javafx.collections.ObservableList;
import sample.Customer;

import java.time.ZonedDateTime;
import java.util.List;

public interface CustomerDao {
    ObservableList<Customer> getAllCustomers();
    Customer getById(int id);
    void save(Customer customer);
    void update(Customer customer);
    void delete(int id);
}
