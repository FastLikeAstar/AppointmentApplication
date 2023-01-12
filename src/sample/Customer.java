package sample;

import java.sql.Timestamp;
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
}
