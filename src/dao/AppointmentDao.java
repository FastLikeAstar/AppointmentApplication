package dao;

import java.time.ZonedDateTime;

public interface AppointmentDao {
    // Create
    void createCustomerId(int newId);
    void createAppointmentId(int newId);
    void createTitle(String newName);
    void createCreatedDate(ZonedDateTime newDate);
    void createCreatedBy(String newName);
    void createLastUpdateDate(ZonedDateTime newDate);
    void createLastUpdatedBy(String newName);
    void createDescription(String newDescription);
    void createLocation(String newLocation);
    void createType(String newType);
    void createUserId(int newId);
    void createContactId(int newId);
    void createStartTime(ZonedDateTime newDate);
    void createEndTime(ZonedDateTime newDate);


    // Read
    int getCustomerId();
    int getAppointmentId();
    String getTitle();
    ZonedDateTime getCreatedDate();
    String getCreatedBy();
    ZonedDateTime getLastUpdateDate();
    String getLastUpdatedBy();
    String getDescription();
    String getLocation();
    String getType();
    int getUserId();
    int getContactId();
    ZonedDateTime getStartTime();
    ZonedDateTime getEndTime();

    // Update
    void updateCustomerId(int newId);
    void updateAppointmentId(int newId);
    void updateTitle(String newName);
    void updateCreatedDate(ZonedDateTime newDate);
    void updateCreatedBy(String newName);
    void updateLastUpdateDate(ZonedDateTime newDate);
    void updateLastUpdatedBy(String newName);
    void updateDescription(String newDescription);
    void updateLocation(String newLocation);
    void updateType(String newType);
    void updateUserId(int newId);
    void updateContactId(int newId);
    void updateStartTime(ZonedDateTime newDate);
    void updateEndTime(ZonedDateTime newDate);

    // Delete
    void deleteCustomerId();
    void deleteAppointmentId();
    void deleteTitle();
    void deleteCreatedDate();
    void deleteCreatedBy();
    void deleteLastUpdateDate();
    void deleteLastUpdatedBy();
    void deleteDescription();
    void deleteLocation();
    void deleteType();
    void deleteUserId();
    void deleteContactId();
    void deleteStartTime();
    void deleteEndTime();
}
