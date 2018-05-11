package data_models;

import java.util.UUID;

public class Email {

	private String emailId;
	private String email;
	private String personId;

	public Email(){
this.emailId = UUID.randomUUID().toString();
	}

	public Email(String personId){
		this.personId = personId;
		this.emailId = UUID.randomUUID().toString();
	}

	public Email(String emailId, String email, String personId) {
		this.emailId = emailId;
		this.email = email;
		this.personId = personId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
}