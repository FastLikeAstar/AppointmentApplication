package dao;

public interface ContactDao {

    // Create
    void createContactId(int newId);
    void createContactName(String newName);
    void createEmail(String newEmail);

    // Read
    int getContactId();
    String getContactName();
    String getEmail();

    // Update
    void updateContactId(int newId);
    void updateContactName(String newName);
    void updateEmail(String newEmail);


    // Delete
    void deleteContactId();
    void deleteContactName();
    void deleteEmail();
}
