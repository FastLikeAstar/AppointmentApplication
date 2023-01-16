package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Contact;
import sample.Jdbc;

import java.sql.*;

public class ContactDaoImpl implements ContactDao{


    @Override
    public ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int contactId = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");
                String email = results.getString("Email");

                Contact contact = new Contact(contactId, contactName, email);
                contactList.add(contact);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return contactList;
    }

    @Override
    public Contact getById(int id) {
        Contact contactIfExists = null;
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                int contactId = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");
                String email = results.getString("Email");

                contactIfExists = new Contact(contactId, contactName, email);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return contactIfExists;
    }

    @Override
    public int save(Contact contact) {
        int affectedRows = -1;
        String sql = "INSERT INTO contacts "+
                "(Contact_Name, Email)"+
                " VALUES (?, ?)";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,contact.getContactName());
            statement.setString(2,contact.getEmail());

            affectedRows = statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            contact.setContactId(generatedKey.getInt(1));

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not create contact. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    @Override
    public int update(Contact contact) {
        int affectedRows = -1;
        String sql = "UPDATE contacts " +
                "SET Contact_Name = ?, " +
                "SET Email = ?, " +
                "WHERE Contact_ID = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,contact.getContactName());
            statement.setString(2,contact.getEmail());
            statement.setInt(3,contact.getContactId());

            affectedRows = statement.executeUpdate();

            // If SQL statement fails or affectedRows included in case the database is full (should see and Int rollover).
            if (affectedRows <= 0){
                throw new SQLException("Could not update contact. -1 or 0 denotes no rows affected."
                        + "Rows affected:" + affectedRows);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return affectedRows;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";

        try{
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,id);

            statement.executeUpdate();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    public ObservableList<String> getAllContactsNames() {
        ObservableList<String> contactList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name FROM contacts";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){

                String contactName = results.getString("Contact_Name");
                contactList.add(contactName);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return contactList;
    }
}
