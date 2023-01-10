package dao;

import java.time.ZonedDateTime;

public interface FirstLevelDivisionDao {

    // Create
    void createDivisionId(int newId);
    void createCountryId(int newId);
    void createDivisionName(String newName);
    void createCreatedDate(ZonedDateTime newDate);
    void createCreatedBy(String newName);
    void createLastUpdateDate(ZonedDateTime newDate);
    void createLastUpdatedBy(String newName);

    // Read
    int getDivisionId();
    int getCountryId();
    String getDivisionName();
    ZonedDateTime getCreatedDate();
    String getCreatedBy();
    ZonedDateTime getLastUpdateDate();
    String getLastUpdatedBy();

    // Update
    void updateDivisionId(int newId);
    void updateCountryId(int newId);
    void updateDivisionName(String newName);
    void updateCreatedDate(ZonedDateTime newDate);
    void updateCreatedBy(String newName);
    void updateLastUpdateDate(ZonedDateTime newDate);
    void updateLastUpdatedBy(String newName);

    // Delete
    void deleteDivisionId();
    void deleteCountryId();
    void deleteDivisionName();
    void deleteCreatedDate();
    void deleteCreatedBy();
    void deleteLastUpdateDate();
    void deleteLastUpdatedBy();
}
