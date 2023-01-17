package sample;

import tools.DateConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
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


        ZonedDateTime appointmentStart = startTimeAsUtc;
        appointmentStart = appointmentStart.withZoneSameInstant(ZoneId.systemDefault());
        this.startTimeAsLocal = appointmentStart.toLocalDateTime();

        ZonedDateTime appointmentEnd = endTimeAsUtc;
        appointmentEnd = appointmentEnd.withZoneSameInstant(ZoneId.systemDefault());
        this.endTimeAsLocal = appointmentEnd.toLocalDateTime();


        this.createdDateAsLocal = DateConverter.convertZonedToLocal(createdDateAsUtc);
        this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(lastUpdateAsUtc);

        Contact contact = Main.dbContacts.getById(contactId);
        this.contactName = contact.getContactName();

    }

    /**
     * Gets the appointments id
     * @return appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets the appointment Id
     * @param appointmentId to assign
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets title of appointment
     * @return title of appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of appointment
     * @param title to assign
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of appointment.
     * @return description of appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of appointment.
     * @param description to assign
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location of appointment.
     * @return location of appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of appointment,
     * @param location to assign
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type of appointment
     * @return type of appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of appointment
     * @param type to assign
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the start time of appointment as Timestamp in UTC.
     * @return start time of appointment as Timestamp in UTC.
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of appointment.
     * Calculates other times for conveniences.
     * @param startTime to assign as start time (in UTC)
     */
    public void setStartTime(Timestamp startTime) {

        this.startTime = startTime;
        this.startTimeAsUtc = DateConverter.convertTimestampToUtc(startTime);

        ZonedDateTime appointmentStart = startTimeAsUtc;
        appointmentStart = appointmentStart.withZoneSameInstant(ZoneId.systemDefault());
        this.startTimeAsLocal = appointmentStart.toLocalDateTime();

    }

    /**
     * Gets the end time as a timestamp in UTC
     * @return UTC timestamp
     */
    public Timestamp getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of appointment.
     * Calculates other times for conveniences.
     * @param endTime to assign as end time (in UTC)
     */
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        this.endTimeAsUtc = DateConverter.convertTimestampToUtc(endTime);


        ZonedDateTime appointmentEnd = endTimeAsUtc;
        appointmentEnd = appointmentEnd.withZoneSameInstant(ZoneId.systemDefault());
        this.endTimeAsLocal = appointmentEnd.toLocalDateTime();
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
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate timestamp (utc) of last update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
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


    /**
     * @return customer id assigned to appointment
     */
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return user id assigned to appointment
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return contact id assigned to appointment
     */
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
        this.startTimeAsUtc = DateConverter.convertSystemLocalDateTimeToUtc(startTimeAsLocal);
        this.endTime = DateConverter.convertUtcToTimestamp(this.startTimeAsUtc);
    }

    /**
     * @return end time in default local timezone
     */
    public LocalDateTime getEndTimeAsLocal() {
        return endTimeAsLocal;
    }

    /**
     * @param endTimeAsLocal end time as Local date time to system default time
     */
    public void setEndTimeAsLocal(LocalDateTime endTimeAsLocal) {
        this.endTimeAsLocal = endTimeAsLocal;
        this.endTimeAsUtc = DateConverter.convertSystemLocalDateTimeToUtc(endTimeAsLocal);
        this.endTime = DateConverter.convertUtcToTimestamp(this.endTimeAsUtc);
    }

    /**
     * @return local date time (UTC)
     */
    public LocalDateTime getCreatedDateAsLocal() {
        return createdDateAsLocal;
    }

    /**
     * @param createdDateAsLocal created date in local date time (UTC)
     */
    public void setCreatedDateAsLocal(LocalDateTime createdDateAsLocal) {
        this.createdDateAsLocal = createdDateAsLocal;
        this.createdDateAsUtc = DateConverter.convertSystemLocalDateTimeToUtc(createdDateAsLocal);
        this.createdDate = DateConverter.convertUtcToTimestamp(this.createdDateAsUtc);
    }

    /**
     * @return localDateTime in UTC
     */
    public LocalDateTime getLastUpdateAsLocal() {
        return lastUpdateAsLocal;
    }

    /**
     * @param lastUpdateAsLocal localDateTime in UTC
     */
    public void setLastUpdateAsLocal(LocalDateTime lastUpdateAsLocal) {
        this.lastUpdateAsLocal = lastUpdateAsLocal;
        this.lastUpdateAsUtc = DateConverter.convertSystemLocalDateTimeToUtc(lastUpdateAsLocal);
        this.lastUpdate = DateConverter.convertUtcToTimestamp(this.lastUpdateAsUtc);
    }

    public ZonedDateTime getStartTimeAsUtc() {
        return startTimeAsUtc;
    }

    /**
     * @param startTimeAsUtc in UTC
     */
    public void setStartTimeAsUtc(ZonedDateTime startTimeAsUtc) {
        this.startTimeAsUtc = startTimeAsUtc;
        this.startTimeAsLocal = DateConverter.convertZonedToLocal(startTimeAsUtc);
        this.startTime = DateConverter.convertUtcToTimestamp(startTimeAsUtc);
    }

    /**
     * @return end time in UTC
     */
    public ZonedDateTime getEndTimeAsUtc() {
        return endTimeAsUtc;
    }

    /**
     * @param endTimeAsUtc in UTC
     */
    public void setEndTimeAsUtc(ZonedDateTime endTimeAsUtc) {
        this.endTimeAsUtc = endTimeAsUtc;
        this.endTimeAsLocal = DateConverter.convertZonedToLocal(endTimeAsUtc);
        this.endTime = DateConverter.convertUtcToTimestamp(endTimeAsUtc);
    }

    /**
     * @return Date created in UTC
     */
    public ZonedDateTime getCreatedDateAsUtc() {
        return createdDateAsUtc;
    }

    /**
     * @param createdDateAsUtc ZonedDateTime of when appointment was created.
     */
    public void setCreatedDateAsUtc(ZonedDateTime createdDateAsUtc) {
        this.createdDateAsUtc = createdDateAsUtc;
        this.createdDateAsLocal = DateConverter.convertZonedToLocal(createdDateAsUtc);
        this.createdDate = DateConverter.convertUtcToTimestamp(createdDateAsUtc);
    }


    /**
     * @return contact name from appointment
     */
    public String getContactName() {
        return contactName;
    }
}
