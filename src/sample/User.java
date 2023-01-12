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
        this.createdDateAsLocal = DateConverter.convertUtcToLocal(createdDateAsUtc);
        this.lastUpdateAsLocal = DateConverter.convertUtcToLocal(lastUpdateAsUtc);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
        this.createdDateAsLocal = DateConverter.convertUtcToLocal(this.createdDateAsUtc);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdate) {
        this.lastUpdateDate = lastUpdate;
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdate);
        this.lastUpdateAsLocal = DateConverter.convertUtcToLocal(this.lastUpdateAsUtc);
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public ZonedDateTime getCreatedDateAsUtc() {
        return createdDateAsUtc;
    }

    public void setCreatedDateAsUtc(ZonedDateTime createdDateAsUtc) {
        this.createdDateAsUtc = createdDateAsUtc;
        this.createdDateAsLocal = DateConverter.convertUtcToLocal(createdDateAsUtc);
        this.createdDate = DateConverter.convertUtcToTimestamp(createdDateAsUtc);
    }

    public ZonedDateTime getLastUpdateAsUtc() {
        return lastUpdateAsUtc;
    }

    public void setLastUpdateAsUtc(ZonedDateTime lastUpdateAsUtc) {
        this.lastUpdateAsUtc = lastUpdateAsUtc;
        this.lastUpdateAsLocal = DateConverter.convertUtcToLocal(lastUpdateAsUtc);
        this.lastUpdateDate = DateConverter.convertUtcToTimestamp(lastUpdateAsUtc);
    }

    public LocalDateTime getCreatedDateAsLocal() {
        return createdDateAsLocal;
    }

    public void setCreatedDateAsLocal(LocalDateTime createdDateAsLocal) {
        this.createdDateAsLocal = createdDateAsLocal;
        this.createdDateAsUtc = DateConverter.convertLocalToUtc(createdDateAsLocal);
        this.createdDate = DateConverter.convertUtcToTimestamp(this.createdDateAsUtc);
    }

    public LocalDateTime getLastUpdateAsLocal() {
        return lastUpdateAsLocal;
    }

    public void setLastUpdateAsLocal(LocalDateTime lastUpdateAsLocal) {
        this.lastUpdateAsLocal = lastUpdateAsLocal;
        this.lastUpdateAsUtc = DateConverter.convertLocalToUtc(lastUpdateAsLocal);
        this.lastUpdateDate = DateConverter.convertUtcToTimestamp(this.lastUpdateAsUtc);
    }
}
