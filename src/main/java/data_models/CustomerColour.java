package data_models;

import java.util.UUID;

public class CustomerColour {

	private UUID customerColourId;
	private int contentWhite;
	private UUID customerId;
	private int exposureTime;
	private String natural;
	private String oxidation;
	private String result;

	public CustomerColour(){

	}

	public CustomerColour(UUID customerColourId, int contentWhite, UUID customerId, int exposureTime,
	String natural, String oxidation, String result) {
		this.customerColourId = customerColourId;
		this.contentWhite = contentWhite;
		this.customerId = customerId;
		this.exposureTime = exposureTime;
		this.natural = natural;
		this.oxidation = oxidation;
		this.result = result;
	}

	public UUID getCustomerColourId() {
		return customerColourId;
	}

	public void setCustomerColourId(UUID CustomerColourId) {
		this.customerColourId = customerColourId; }

	public int getContentWhite() {
		return contentWhite;
	}

	public void setContentWhite(int contentWhite) {
		this.contentWhite = contentWhite;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public int getExposureTime() {
		return exposureTime;
	}

	public void setExposureTime(int exposureTime) {
		this.exposureTime = exposureTime;
	}

	public String getNatural() {
		return natural;
	}

	public void setNatural(String natural) {
		this.natural = natural;
	}

	public String getOxidation() {
		return oxidation;
	}

	public void setOxidation(String oxidation) {
		this.oxidation = oxidation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}