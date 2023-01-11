package dao;

import javafx.collections.ObservableList;
import sample.Customer;

import java.time.ZonedDateTime;
import java.util.List;

public interface CustomerDao {
    ObservableList<Customer> getAllCustomers();
    Customer getById(int id);
    int save(Customer customer);
    int update(Customer customer);
    void delete(int id);
}
