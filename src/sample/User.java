package sample;

import tools.DateConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class User {

    int userId;
    String userName;
    String password;
    Timestamp createdDate;
    String createdBy;
    Timestamp lastUpdateDate;
    String lastUpdatedBy;

    LocalDateTime createdDateAsLocal;
    LocalDateTime lastUpdateAsLocal;
    ZonedDateTime createdDateAsUtc;
    ZonedDateTime lastUpdateAsUtc;

    public User(int userId, String userName, String password, Timestamp createdDate, String createdBy, Timestamp lastUpdateDate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdatedBy = lastUpdatedBy;

        this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdateDate);
        this.createdDateAsLocal = DateConverter.convertZonedToLocal(createdDateAsUtc);
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(lastUpdateAsUtc);
    }

    /**
     * Getter for user id.
     * @return id of user
     */
    public int getUserId() {
        return userId;
    }


    /**
     * @param userId is the new id for the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for user name.
     * @return name of user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName is the new name for the user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for user password.
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password is the new password
     */
    public void setPassword(String password) {
        this.password = password;
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
        this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
        this.createdDateAsLocal = DateConverter.convertZonedToLocal(this.createdDateAsUtc);
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
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdate);
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(this.lastUpdateAsUtc);
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
