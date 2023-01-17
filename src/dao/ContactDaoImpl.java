package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Contact;
import sample.Jdbc;

import java.sql.*;

public class ContactDaoImpl implements ContactDao{


    /**
     * Gets all contacts in the database.
     * @return Observable List to possibly display in JavaFX.
     */
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

    /**
     * Gets contact information from a contact id.
     * @param id provided contact id.
     * @return contact information in contact object.
     */
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

    /**
     * Creates a new contact record in the database.
     * @param contact information to be saved as a record.
     * @return returns the rows affected if verification is needed.
     */
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

    /**
     * Updates a contact already recorded in the database.
     * @param contact information to be updated as a record.
     * @return returns the rows affected if verification is needed.
     */
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

    /**
     * Deletes a contact record by id.
     * @param id of contact to delete.
     */
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

    /**
     * Gets a list of all contact ids.
     * @return list of contact ids.
     */
    public ObservableList<Integer> getAllContactIds() {
        ObservableList<Integer> contactList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID FROM contacts " +
                "WHERE NOT EXISTS " +
                "(SELECT * FROM contacts " +
                "WHERE contacts.Contact_Name IS NULL)";
        try {
            Connection connection = Jdbc.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                int contactId = results.getInt("Contact_ID");


                contactList.add(contactId);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return contactList;
    }

    /**
     * Gets a list of all contact names in the database.
     * @return list of contact names.
     */
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
