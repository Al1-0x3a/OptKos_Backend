package data_models;

import java.util.UUID;

public class Customer extends Person {

	private String annotation;
	private UUID costumerId;
	private UUID personId;
	private boolean problemCustomer;
	private double timefactor;
	public CustomerCategory CustomerCategory;

	public Customer() {

	}

	public Customer( UUID costumerId, double timefactor, String annotation, char problemCustomer, UUID personId) {
		this.annotation = annotation;
		this.costumerId = costumerId;
		if(problemCustomer == '0')
			this.problemCustomer = false;
		if(problemCustomer == '1')
			this.problemCustomer = true;
		this.timefactor = timefactor;
		this.personId = personId;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public UUID getCostumerId() {
		return costumerId;
	}

	public void setCostumerId(UUID costumerId) {
		this.costumerId = costumerId;
	}

	public boolean isProblemCustomer() {
		return problemCustomer;
	}

	public void setProblemCustomer(boolean problemCustomer) {
		this.problemCustomer = problemCustomer;
	}

	public double getTimefactor() {
		return timefactor;
	}

	public void setTimefactor(double timefactor) {
		this.timefactor = timefactor;
	}

	public CustomerCategory getCustomerCategory() {
		return CustomerCategory;
	}

	public void setCustomerCategory(CustomerCategory m_CustomerCategory) {
		this.CustomerCategory = m_CustomerCategory;
	}
}
