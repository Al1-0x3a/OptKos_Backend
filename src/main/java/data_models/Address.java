package data_models;

import java.util.UUID;

public class Address {

	private UUID addressId;
	private String postcode;
	private String city;
	private String street;
	private String housenr;
	private UUID personId;

	public Address(UUID personId){
		this.addressId = UUID.randomUUID();
		this.personId = personId;
	}
	public Address(){}

	public Address(UUID addressId, String postcode, String city, String street, String housenr, UUID personId) {
		this.addressId = UUID.randomUUID();
		this.postcode = postcode;
		this.city = city;
		this.street = street;
		this.housenr = housenr;
		this.personId = personId;
	}

	public UUID getAddressId() {
		return addressId;
	}

	public void setAddressId(UUID addressId) {
		this.addressId = addressId;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHousenr() {
		return housenr;
	}

	public void setHousenr(String housenr) {
		this.housenr = housenr;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}
}