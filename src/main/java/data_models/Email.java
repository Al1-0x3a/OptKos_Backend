package data_models;

import java.util.UUID;

public class Email {

    private String emailId;
    private String emailAddress;
    private String personId;

    public Email() {
        this.emailId = UUID.randomUUID().toString();
    }

    public Email(String personId) {
        this.personId = personId;
        this.emailId = UUID.randomUUID().toString();
    }

    public Email(String emailId, String emailAddress, String personId) {
        this.emailId = emailId;
        this.emailAddress = emailAddress;
        this.personId = personId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}