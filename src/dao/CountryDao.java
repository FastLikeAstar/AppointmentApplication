package dao;

import java.time.ZonedDateTime;

/**
 * This interface follows the Data Access Object (DAO) Pattern for the Countries
 *  entity in the provided Entity Relationship Diagram (ERD).
 */
public interface CountryDao {

    // Create
    void createCountryId(int newId);
    void createCountryName(String newName);
    void createCreatedDate(ZonedDateTime newDate);
    void createCreatedBy(String newName);
    void createLastUpdateDate(ZonedDateTime newDate);
    void createLastUpdatedBy(String newName);

    // Read
    int getCountryId();
    String getCountryName();
    ZonedDateTime getCreatedDate();
    String getCreatedBy();
    ZonedDateTime getLastUpdateDate();
    String getLastUpdatedBy();

    // Update
    void updateCountryId(int newId);
    void updateCountryName(String newName);
    void updateCreatedDate(ZonedDateTime newDate);
    void updateCreatedBy(String newName);
    void updateLastUpdateDate(ZonedDateTime newDate);
    void updateLastUpdatedBy(String newName);

    // Delete
    void deleteCountryId();
    void deleteCountryName();
    void deleteCreatedDate();
    void deleteCreatedBy();
    void deleteLastUpdateDate();
    void deleteLastUpdatedBy();

}
