package sample;


import tools.DateConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class FirstLevelDivision {

    int divisionId;
    String divisionName;
    Timestamp createdDate;
    String createdBy;
    Timestamp lastUpdateDate;
    String lastUpdatedBy;
    int countryId;



    public FirstLevelDivision(int divisionId, String divisionName, Timestamp createdDate, String createdBy, Timestamp lastUpdateDate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;

    }

    /**
     * @return division id assigned to this division
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId is the new id assigned to this division
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }


    /**
     * @return country id assigned to this division
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId is the new country id assigned to this division
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return name of this division
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName is the new division name.
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Getter for user date of creation (in db)
     * @return created date
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate timestamp (UTC) of when record was added to database.
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Getter for name of user that created this user (in db)
     * @return creator of user's username.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy username of creator (should not change unless to correct)
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for user date of last update (in db)
     * @return last update date
     */
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param lastUpdate timestamp (utc) of last update
     */
    public void setLastUpdateDate(Timestamp lastUpdate) {
        this.lastUpdateDate = lastUpdate;
    }

    /**
     * Getter for user who performed last update (in db)
     * @return last updater's username
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy user that last updated record
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}
