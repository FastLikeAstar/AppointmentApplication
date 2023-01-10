package dao;

import java.time.ZonedDateTime;

public interface UserDao {
    // Create
    void createUserId(int newId);
    void createUserName(String newName);
    void createPassword(String newName);
    void createCreatedDate(ZonedDateTime newDate);
    void createCreatedBy(String newName);
    void createLastUpdateDate(ZonedDateTime newDate);
    void createLastUpdatedBy(String newName);

    // Read
    int getUserId();
    String getUserName();
    String getPassword();
    ZonedDateTime getCreatedDate();
    String getCreatedBy();
    ZonedDateTime getLastUpdateDate();
    String getLastUpdatedBy();

    // Update
    void updateUserId(int newId);
    void updateUserName(String newName);
    void updatePassword(String newPassword);
    void updateCreatedDate(ZonedDateTime newDate);
    void updateCreatedBy(String newName);
    void updateLastUpdateDate(ZonedDateTime newDate);
    void updateLastUpdatedBy(String newName);

    // Delete
    void deleteUserId();
    void deleteUserName();
    void deletePassword();
    void deleteCreatedDate();
    void deleteCreatedBy();
    void deleteLastUpdateDate();
    void deleteLastUpdatedBy();
}
