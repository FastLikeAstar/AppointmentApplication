package sample;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Customer {

    int customerId;
    String customerName;
    String address;
    String zipCode;
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
     *  @param zipCode zip code of the address
     *  @param phone phone number of the customer
     *  @param createdDate timestamp when the customer is created
     *  @param createdBy the user who created the customer
     *  @param lastUpdate timestamp when the customer was last updated
     *  @param lastUpdatedBy the user who last updated the customer
     *  @param divisionId location division where the customer belongs (foreign key)
     */
    public Customer(int customerId, String customerName, String address, String zipCode, String phone, Timestamp createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.zipCode = zipCode;
        this.phone = phone;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

}
