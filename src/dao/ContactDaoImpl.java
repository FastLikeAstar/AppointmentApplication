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
                int contactId = results.getInt("contact_id");
                String contactName = results.getString("contact_name");
                String email = results.getString("email");

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
        String sql = "SELECT * FROM contacts WHERE contact_id = ?";

        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();

            int contactId = results.getInt("contact_id");
            String contactName = results.getString("contact");
            String email = results.getString("email");

            contactIfExists = new Contact(contactId, contactName, email);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return contactIfExists;
    }

    @Override
    public int save(Contact contact) {
        int affectedRows = -1;
        String sql = "INSERT INTO contacts "+
                "(contact_name, email)"+
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
                "SET contact_name = ?, " +
                "SET email = ?, " +
                "WHERE id = ?";

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
        String sql = "DELETE FROM contacts WHERE id = ?";

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
