package data_models;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
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

    @XmlEnum()
    public enum SALUTATION {
        @XmlEnumValue("Herr") HERR("Herr"),
        @XmlEnumValue("Frau") FRAU("Frau");
        private final String readableName;

        SALUTATION(String readableName) {
            this.readableName = readableName;
        }

        public String getReadableName() {
            return this.readableName;
        }
    }

    @XmlEnum
    public enum TITLE {
        @XmlEnumValue("Prof.") PROFESSOR("Prof."),
        @XmlEnumValue("Dr.") DOKTOR("Dr."),
        @XmlEnumValue("Dipl.") DIPLOM("Dipl."),
        @XmlEnumValue("Sen.") SENIOR("Sen."),
        @XmlEnumValue("Jun.") JUNIOR("Jun."),
        @XmlEnumValue("Kein Titel") KEINTITEL("Kein Titel");
        private String readableName;

        TITLE(String readableName) {
            this.readableName = readableName;
        }

        public String toString() {
            return this.readableName;
        }
    }

    @XmlEnum
    public enum GENDER {
        @XmlEnumValue("weiblich") W("weiblich"),
        @XmlEnumValue("maennlich") M("maennlich");
        private String readableName;

        GENDER(String readableName) {
            this.readableName = readableName;
        }

        public String toString() {
            return this.readableName;
        }
    }


    public Person() {
        phoneList = new ArrayList<Phone>();
        emailList = new ArrayList<Email>();
        personId = UUID.randomUUID();
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