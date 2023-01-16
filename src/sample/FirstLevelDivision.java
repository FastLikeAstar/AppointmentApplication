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

    LocalDateTime createdDateAsLocal;
    LocalDateTime lastUpdateAsLocal;
    ZonedDateTime createdDateAsUtc;
    ZonedDateTime lastUpdateAsUtc;

    public FirstLevelDivision(int divisionId, String divisionName, Timestamp createdDate, String createdBy, Timestamp lastUpdateDate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdateDate = lastUpdateDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;

        this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdateDate);
        this.createdDateAsLocal = DateConverter.convertZonedToLocal(createdDateAsUtc);
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(lastUpdateAsUtc);
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
        this.createdDateAsLocal = DateConverter.convertZonedToLocal(this.createdDateAsUtc);
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
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(this.lastUpdateAsUtc);
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
        this.createdDateAsLocal = DateConverter.convertZonedToLocal(createdDateAsUtc);
        this.createdDate = DateConverter.convertUtcToTimestamp(createdDateAsUtc);
    }

    public ZonedDateTime getLastUpdateAsUtc() {
        return lastUpdateAsUtc;
    }

    public void setLastUpdateAsUtc(ZonedDateTime lastUpdateAsUtc) {
        this.lastUpdateAsUtc = lastUpdateAsUtc;
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(lastUpdateAsUtc);
        this.lastUpdateDate = DateConverter.convertUtcToTimestamp(lastUpdateAsUtc);
    }

    public LocalDateTime getCreatedDateAsLocal() {
        return createdDateAsLocal;
    }

    public void setCreatedDateAsLocal(LocalDateTime createdDateAsLocal) {
        this.createdDateAsLocal = createdDateAsLocal;
        this.createdDateAsUtc = DateConverter.convertSystemLocalDateTimeToUtc(createdDateAsLocal);
        this.createdDate = DateConverter.convertUtcToTimestamp(this.createdDateAsUtc);
    }

    public LocalDateTime getLastUpdateAsLocal() {
        return lastUpdateAsLocal;
    }

    public void setLastUpdateAsLocal(LocalDateTime lastUpdateAsLocal) {
        this.lastUpdateAsLocal = lastUpdateAsLocal;
        this.lastUpdateAsUtc = DateConverter.convertSystemLocalDateTimeToUtc(lastUpdateAsLocal);
        this.lastUpdateDate = DateConverter.convertUtcToTimestamp(this.lastUpdateAsUtc);
    }
}
