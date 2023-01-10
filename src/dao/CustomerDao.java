package dao;

import java.time.ZonedDateTime;

public interface CustomerDao {
    // Create
    void createCustomerId(int newId);
    void createDivisionId(int newId);
    void createCustomerName(String newName);
    void createCreatedDate(ZonedDateTime newDate);
    void createCreatedBy(String newName);
    void createLastUpdateDate(ZonedDateTime newDate);
    void createLastUpdatedBy(String newName);
    void createPhoneNumber(String newPhoneNumber);
    void createPostalCode(String newPostalCode);
    void createAddress(String newAddress);


    // Read
    int getCustomerId();
    int getDivisionId();
    String getCustomerName();
    ZonedDateTime getCreatedDate();
    String getCreatedBy();
    ZonedDateTime getLastUpdateDate();
    String getLastUpdatedBy();
    String getPhoneNumber();
    String getPostalCode();
    String getAddress();

    // Update
    void updateCustomerId(int newId);
    void updateDivisionId(int newId);
    void updateCustomerName(String newName);
    void updateCreatedDate(ZonedDateTime newDate);
    void updateCreatedBy(String newName);
    void updateLastUpdateDate(ZonedDateTime newDate);
    void updateLastUpdatedBy(String newName);
    void updatePhoneNumber(String newPhoneNumber);
    void updatePostalCode(String newPostalCode);
    void updateAddress(String newAddress);

    // Delete
    void deleteCustomerId();
    void deleteDivisionId();
    void deleteCustomerName();
    void deleteCreatedDate();
    void deleteCreatedBy();
    void deleteLastUpdateDate();
    void deleteLastUpdatedBy();
    void deletePhoneNumber();
    void deletePostalCode();
    void deleteAddress();
}
