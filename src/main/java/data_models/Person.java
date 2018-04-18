package data_models;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Person {

	private String firstname;
	private char gender;
	private String lastname;
	private UUID personId;
	private String salutation;
	private String title;
	private Address address;
	private List<Phone> phoneList;
	private List<Email> emailList;

	public Person(){
        phoneList = new ArrayList<>();
        emailList = new ArrayList<>();
	}

	public List<Phone> getPhoneList() {
		return phoneList;
	}
	public void addPhoneNumber(Phone phone){
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

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
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

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
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