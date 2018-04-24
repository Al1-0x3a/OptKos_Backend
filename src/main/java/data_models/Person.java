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

	public enum SALUTATION{
		HERR("Herr"), FRAU("Frau");
		private String salutation;
		SALUTATION(String salutation){
			this.salutation = salutation;
		}
		public String salutation(){
			return this.salutation;
		}
	}

	public enum TITLE{
		PROFESSOR("Prof."), DOKTOR("Dr."), DIPLOMING("Diplom Ing."), SENIOR("Sen."), JUNIOR("Jun.");
		private String title;

		TITLE(String title){
			this.title = title;
		}
		public String title(){
			return this.title;
		}
	}
	public enum GENDER{
		WEIBLICH('w'), MAENNLICH('m'), AUTOMIT4ZYLINDERREIHENMOTOR('a');

		private char gender;
		GENDER(char gender){
			this.gender = gender;
		}
		public char gender(){
			return gender;
		}
	}


	public Person(){
		phoneList = new ArrayList<Phone>();
		emailList = new ArrayList<Email>();
		personId = UUID.randomUUID();
	}

	public void setPersonId(UUID personId) {
		if(this.personId == null )
			this.personId = personId;
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

	public GENDER getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = GENDER.valueOf(String.valueOf(gender));
	}
	public void setGender(GENDER gender){
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

	public String getSalutation() {
		return salutation.salutation();
	}

	public void setSalutation(String salutation) {
		this.salutation = SALUTATION.valueOf(salutation);
	}


	public void setSalutation(SALUTATION salutation) {
		this.salutation = salutation;
	}

	public TITLE getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = TITLE.valueOf(title);
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