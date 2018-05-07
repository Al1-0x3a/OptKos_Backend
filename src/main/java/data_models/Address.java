package data_models;

import java.util.UUID;

public class Address {

	private String addressId;
	private String postcode;
	private String city;
	private String street;
	private String housenr;
	private String addition;
	private String personId;

	public Address(String personId){
		this.addressId = UUID.randomUUID().toString();
		this.personId = personId;
	}
	public Address(){}

	public Address(String addressId, String postcode, String city, String street, String housenr, String personId, String addition) {
		this.addressId = addressId;
		this.postcode = postcode;
		this.city = city;
		this.street = street;
		this.housenr = housenr;
		this.personId = personId;
		this.addition = addition;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
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

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}
}