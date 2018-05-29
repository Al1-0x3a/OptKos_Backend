package data_models;

import java.util.UUID;

public class Customer extends Person {

	private String annotation;
	private String costumerId;
	private String personId;
	private boolean problemCustomer;
	private double timefactor;
	private CustomerCategory customerCategory;

	public Customer() {
		super();
		this.costumerId = UUID.randomUUID().toString();
	}

	public Customer(String personId) {
	    super(personId);
	    costumerId = UUID.randomUUID().toString();
    }

	public Customer( String costumerId, double timefactor, String annotation, char problemCustomer, String personId) {
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

	public String getCostumerId() {
		return costumerId;
	}

	public void setCostumerId(String costumerId) {
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
        return customerCategory;
    }

    public void setCustomerCategory(CustomerCategory customerCategory) {
        this.customerCategory = customerCategory;
    }
}
