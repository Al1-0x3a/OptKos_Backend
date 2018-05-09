package data_models;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;
import java.util.UUID;

public class Service {
	private String serviceId;
	private String name;
	private String description;
	private BigDecimal price;
	private Time durationPlanned;
	private Time durationAverage;
	private String isDeleted;

	public Service(){
this.serviceId = UUID.randomUUID().toString();
	}

	public Service(String serviceId, String name, String description, BigDecimal price,
	Time durationPlanned, Time durationAverage, String isDeleted) {
		this.serviceId = serviceId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.durationPlanned = durationPlanned;
		this.durationAverage = durationAverage;
		this.isDeleted = isDeleted;
	}


	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Time getDurationPlanned() {
		return durationPlanned;
	}

	public void setDurationPlanned(Time durationPlanned) {
		this.durationPlanned = durationPlanned;
	}

	public Time getDurationAverage() {
		return durationAverage;
	}

	public void setDurationAverage(Time durationAverage) {
		this.durationAverage = durationAverage;
	}

	public String getIsDeleted(){ return isDeleted; }

	public void setIsDeleted(String isDeleted) { this.isDeleted = isDeleted; }
}