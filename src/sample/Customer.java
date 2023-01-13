package sample;

import tools.DateConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Customer {

    int customerId;
    String customerName;
    String address;
    String postalCode;
    String phone;
    Timestamp createdDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;
    int divisionId;
    String division;
    String country;

    LocalDateTime createdDateAsLocal;
    LocalDateTime lastUpdateAsLocal;
    ZonedDateTime createdDateAsUtc;
    ZonedDateTime lastUpdateAsUtc;

    /**
     *  Constructor class to create a Customer object represented in the Database.
     *  Note that Database is set to UTC time.
     *  @param customerId id of the customer (primary key)
     *  @param customerName name of the customer
     *  @param address address of the customer
     *  @param postalCode postal code (or zip code) of the address
     *  @param phone phone number of the customer
     *  @param createdDate timestamp when the customer is created
     *  @param createdBy the user who created the customer
     *  @param lastUpdate timestamp when the customer was last updated
     *  @param lastUpdatedBy the user who last updated the customer
     *  @param divisionId location division where the customer belongs (foreign key)
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;

        this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdate);
        this.createdDateAsLocal = DateConverter.convertUtcToLocal(createdDateAsUtc);
        this.lastUpdateAsLocal = DateConverter.convertUtcToLocal(lastUpdateAsUtc);

        FirstLevelDivision firstLevelDivision = Main.dbDivisions.getById(divisionId);
        Country countryOfDivision = Main.dbCountries.getById(firstLevelDivision.getCountryId());
        this.country = countryOfDivision.getCountryName();
        this.division = firstLevelDivision.getDivisionName();
    }

    /**
     * Get the ID of the customer.
     * This should be a database generated value because it is the primary key (PK).
     * @return the customer's id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the customer
     * This should be a database generated value because it is the primary key (PK).
     * @param customerId database generated integer representing customer id.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the customer name.
     * This can be first, middle, and last names as one string.
     * @return the customer's full name as one string
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer name.
     * This can be first, middle, and last names as one string.
     * @param customerName the customer's full name as one string
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *  Gets the customer street address.
     *  This is only the street address as more location information is entered
     *  else where. See PostalCode (aka ZipCode) and Division Id (including States/Provinces)
     * @return
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
        this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdate);
        this.lastUpdateAsLocal = DateConverter.convertUtcToLocal(this.lastUpdateAsUtc);
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        this.lastUpdate = DateConverter.convertUtcToTimestamp(lastUpdateAsUtc);
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
}
