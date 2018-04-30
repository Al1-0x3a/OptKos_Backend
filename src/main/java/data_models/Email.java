package data_models;

import java.util.UUID;

public class Email {

	private UUID emailId;
	private String email;
	private UUID personId;

	public Email(){
this.emailId = UUID.randomUUID();
	}

	public Email(UUID emailId, String email, UUID personId) {
		this.emailId = emailId;
		this.email = email;
		this.personId = personId;
	}

	public UUID getEmailId() {
		return emailId;
	}

	public void setEmailId(UUID emailId) {
		this.emailId = emailId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}
}