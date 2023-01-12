package dao;

import javafx.collections.ObservableList;
import sample.Contact;

public interface ContactDao {

    ObservableList<Contact> getAllContacts();
    Contact getById(int id);
    int save(Contact contact);
    int update(Contact contact);
    void delete(int id);
}
