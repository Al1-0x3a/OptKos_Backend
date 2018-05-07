package data_models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Person {

    private String firstname;
    private GENDER gender;
    private String lastname;
    private UUID personId;
    private SALUTATION salutation;
    private TITLE title;
    private Address address;
    private List<Phone> phoneList;
    private List<Email> emailList;

    public enum SALUTATION {
        Herr, Frau;
    }

    public enum TITLE {
        Professor, Doktor, Diplom, Senior, Junior, Default;
    }

    public enum GENDER {
        W, M;

    }


    public Person() {
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();

        personId = UUID.randomUUID();
        this.address = new Address(this.personId);
    }

    public Person(UUID personId) {
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();
        this.personId = personId;
        this.address = new Address(this.personId);
    }

    public void setPersonId(UUID personId) {
        if (this.personId == null)
            this.personId = personId;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void addPhoneNumber(Phone phone) {
        this.phoneList.add(phone);
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UUID getPersonId() {
        return personId;
    }

    public SALUTATION getSalutation() {
        return salutation;
    }

    public void setSalutation(SALUTATION salutation) {
        this.salutation = salutation;
    }

    public TITLE getTitle() {
        return title;
    }

    public void setTitle(TITLE title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", gender=" + gender +
                ", lastname='" + lastname + '\'' +
                ", personId=" + personId +
                ", salutation='" + salutation + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}