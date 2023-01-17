package sample;

public class Contact {

    int contactId;
    String contactName;
    String email;

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * @return contact's id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId to assign as new id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return contact's name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName to assign as new name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return contact's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email to assign as new email for contact
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
