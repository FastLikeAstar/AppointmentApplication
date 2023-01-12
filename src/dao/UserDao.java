package dao;

import javafx.collections.ObservableList;
import sample.User;


public interface UserDao {

    ObservableList<User> getAllUsers();
    User getById(int id);
    int save(User user);
    int update(User user);
    void delete(int id);

}
