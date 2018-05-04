package data_models;

import java.util.UUID;

public class Phone {

	private UUID phoneId;
	private String annotation;
	private String number;
	private String description;
	private UUID personId;


	public Phone(){
		this.phoneId = UUID.randomUUID();
	}

	public Phone(UUID phoneId, String number, String annotation, String description, UUID personId) {
		this.annotation = annotation;
		this.number = number;
		this.phoneId = phoneId;
		this.description = description;
		this.personId = personId;
	}

	public Phone(UUID personId){
		this.personId = personId;
		this.phoneId = UUID.randomUUID();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getPersonId() {
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

	public UUID getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(UUID phoneId) {
		this.phoneId = phoneId;
	}
}