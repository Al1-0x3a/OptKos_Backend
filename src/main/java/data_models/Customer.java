package data_models;

import java.util.UUID;

public class Customer extends Person {

	private int annotation;
	private UUID costumerId;
	private boolean problemCustomer;
	private double timefactor;
	public CustomerCategory m_CustomerCategory;

	public Customer() {

	}
}
