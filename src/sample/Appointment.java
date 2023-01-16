package sample;

import tools.DateConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Appointment {

    int appointmentId;
    String title;
    String description;
    String location;
    String type;
    Timestamp startTime;
    Timestamp endTime;
    Timestamp createdDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;

    String contactName;
    int customerId;
    int userId;
    int contactId;
    LocalDateTime startTimeAsLocal;
    LocalDateTime endTimeAsLocal;
    LocalDateTime createdDateAsLocal;
    LocalDateTime lastUpdateAsLocal;
    ZonedDateTime startTimeAsUtc;
    ZonedDateTime endTimeAsUtc;
    ZonedDateTime createdDateAsUtc;
    ZonedDateTime lastUpdateAsUtc;

    public Appointment(int appointmentId, String title, String description, String location, String type, Timestamp startTime, Timestamp endTime, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;


        this.startTimeAsUtc = DateConverter.convertTimestampToUtc(startTime);
        this.endTimeAsUtc = DateConverter.convertTimestampToUtc(endTime);
        this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdate);
        this.startTimeAsLocal = DateConverter.convertZonedToLocal(startTimeAsUtc);
        this.endTimeAsLocal = DateConverter.convertZonedToLocal(endTimeAsUtc);
        this.createdDateAsLocal = DateConverter.convertZonedToLocal(createdDateAsUtc);
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(lastUpdateAsUtc);

        Contact contact = Main.dbContacts.getById(contactId);
        this.contactName = contact.getContactName();

    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {

        this.startTime = startTime;
        this.startTimeAsUtc = DateConverter.convertTimestampToUtc(startTime);
        this.startTimeAsLocal = DateConverter.convertZonedToLocal(this.startTimeAsUtc);
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        this.endTimeAsUtc = DateConverter.convertTimestampToUtc(endTime);
        this.endTimeAsLocal = DateConverter.convertZonedToLocal(this.endTimeAsUtc);
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

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdate);
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(this.lastUpdateAsUtc);
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
        Contact contact = Main.dbContacts.getById(contactId);
        this.contactName = contact.getContactName();
    }

    public LocalDateTime getStartTimeAsLocal() {
        return startTimeAsLocal;
    }

    public void setStartTimeAsLocal(LocalDateTime startTimeAsLocal) {
        this.startTimeAsLocal = startTimeAsLocal;
        this.startTimeAsUtc = DateConverter.convertLocalToUtc(startTimeAsLocal);
        this.endTime = DateConverter.convertUtcToTimestamp(this.startTimeAsUtc);
    }

    public LocalDateTime getEndTimeAsLocal() {
        return endTimeAsLocal;
    }

    public void setEndTimeAsLocal(LocalDateTime endTimeAsLocal) {
        this.endTimeAsLocal = endTimeAsLocal;
        this.endTimeAsUtc = DateConverter.convertLocalToUtc(endTimeAsLocal);
        this.endTime = DateConverter.convertUtcToTimestamp(this.endTimeAsUtc);
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
        this.lastUpdate = DateConverter.convertUtcToTimestamp(this.lastUpdateAsUtc);
    }

    public ZonedDateTime getStartTimeAsUtc() {
        return startTimeAsUtc;
    }

    public void setStartTimeAsUtc(ZonedDateTime startTimeAsUtc) {
        this.startTimeAsUtc = startTimeAsUtc;
        this.startTimeAsLocal = DateConverter.convertZonedToLocal(startTimeAsUtc);
        this.startTime = DateConverter.convertUtcToTimestamp(startTimeAsUtc);
    }

    public ZonedDateTime getEndTimeAsUtc() {
        return endTimeAsUtc;
    }

    public void setEndTimeAsUtc(ZonedDateTime endTimeAsUtc) {
        this.endTimeAsUtc = endTimeAsUtc;
        this.endTimeAsLocal = DateConverter.convertZonedToLocal(endTimeAsUtc);
        this.endTime = DateConverter.convertUtcToTimestamp(endTimeAsUtc);
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
        this.lastUpdate = DateConverter.convertUtcToTimestamp(lastUpdateAsUtc);
    }

    public String getContactName() {
        return contactName;
    }
}
