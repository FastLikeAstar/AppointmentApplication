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

        if (!(createdDate == null)) {
            this.createdDateAsUtc = DateConverter.convertTimestampToUtc(createdDate);
            this.lastUpdateAsUtc = DateConverter.convertTimestampToUtc(lastUpdate);
            this.createdDateAsLocal = DateConverter.convertZonedToLocal(createdDateAsUtc);
            this.lastUpdateAsLocal = DateConverter.convertZonedToLocal(lastUpdateAsUtc);
        }


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

    /**
     * @param address is the new address of the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return postal code of customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode is the new postal code of the customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return phone number of customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone is the new phone number of the customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * @return divsion id assigned of customer
     */
    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return divsion name assigned of customer
     */
    public String getDivision() {
        if (this.division == null){
            FirstLevelDivision firstLevelDivision = Main.dbDivisions.getById(this.divisionId);
            this.division = firstLevelDivision.getDivisionName();
        }
        return division;
    }

    /**
     * @param division name of division the customer belongs to.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return country name assigned of customer
     */
    public String getCountry() {
        if (this.country == null){
            FirstLevelDivision firstLevelDivision = Main.dbDivisions.getById(divisionId);
            Country countryOfDivision = Main.dbCountries.getById(firstLevelDivision.getCountryId());
            this.country = countryOfDivision.getCountryName();
        }

        return country;
    }

    /**
     * Sets the name of the country.
     * @param country is the name of the country
     */
    public void setCountry(String country) {
        this.country = country;
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
        this.lastUpdate = DateConverter.convertUtcToTimestamp(this.lastUpdateAsUtc);
    }
}
