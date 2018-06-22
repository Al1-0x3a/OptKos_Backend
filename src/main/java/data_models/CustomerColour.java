package data_models;


import java.util.UUID;

public class CustomerColour {

	private String customerColourId;
	private int contentWhite;
	private String customerId;
	private int exposureTime;
	private String natural;
	private double oxidation;
	private String result;

	public CustomerColour(){
		this.customerColourId = UUID.randomUUID().toString();
	}

	public CustomerColour(String customerColourId, int contentWhite, String customerId, int exposureTime,
	String natural, Double oxidation, String result) {
		this.customerColourId = customerColourId;
		this.contentWhite = contentWhite;
		this.customerId = customerId;
		this.exposureTime = exposureTime;
		this.natural = natural;
		this.oxidation = oxidation;
		this.result = result;
	}

	public String getCustomerColourId() {
		return customerColourId;
	}

	public void setCustomerColourId(String customerColourId) {
		this.customerColourId = customerColourId; }

	public int getContentWhite() {
		return contentWhite;
	}

	public void setContentWhite(int contentWhite) {
		this.contentWhite = contentWhite;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
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

	public Double getOxidation() {
		return oxidation;
	}

	public void setOxidation(Double oxidation) {
		this.oxidation = oxidation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}