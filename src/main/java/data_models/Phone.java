package data_models;

import java.util.UUID;

public class Phone {

	private String phoneId;
	private String annotation;
	private String number;
	private String description;
	private String personId;


	public Phone(){
		this.phoneId = UUID.randomUUID().toString();
	}

	public Phone(String phoneId, String number, String annotation, String description, String personId) {
		this.annotation = annotation;
		this.number = number;
		this.phoneId = phoneId;
		this.description = description;
		this.personId = personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Phone(String personId){
		this.personId = personId;
		this.phoneId = UUID.randomUUID().toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPersonId() {
		return personId;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}
}