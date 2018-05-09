package data_models;

import java.time.Duration;
import java.util.UUID;

public class CustomerCategory {

	private String customerCategoryId;
	private String description;
	private String name;
	private Duration timeBonus;
	private double timefactor;

	public CustomerCategory(){
this.customerCategoryId = UUID.randomUUID().toString();
	}

	public CustomerCategory(String customerCategoryId, String description, String name) {
		this.customerCategoryId = customerCategoryId;
		this.description = description;
		this.name = name;
	}

	public String getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(String customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Duration getTimeBonus() {
		return timeBonus;
	}

	public void setTimeBonus(Duration timeBonus) {
		this.timeBonus = timeBonus;
	}

	public double getTimefactor() {
		return timefactor;
	}

	public void setTimefactor(double timefactor) {
		this.timefactor = timefactor;
	}
}