package data_models;

import java.time.Duration;
import java.util.UUID;

public class CustomerCategory {

	private UUID customerCategoryId;
	private String description;
	private String name;
	private Duration timeBonus;
	private double timefactor;

	public CustomerCategory(){
this.customerCategoryId = UUID.randomUUID();
	}

	public CustomerCategory(UUID customerCategoryId, String description, String name) {
		this.customerCategoryId = customerCategoryId;
		this.description = description;
		this.name = name;
	}

	public UUID getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(UUID customerCategoryId) {
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