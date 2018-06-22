package data_models;

import data_loader.data_access_object.ServiceEmployeeDurationDao;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class Service {
	private String serviceId;
	private String name;
	private String description;
	private BigDecimal price;
	private Duration durationPlanned;
	private Duration durationAverage;
	private String isDeleted;
	private List<ServiceEmployeeDuration> sedList;

	public Service(){
		this.serviceId = UUID.randomUUID().toString();
		sedList = ServiceEmployeeDurationDao.getSedListWithOnlyEmployees(this.serviceId);
		isDeleted="";
	}

	public Service(String serviceId, String name, String description, BigDecimal price,
	Duration durationPlanned, Duration durationAverage, String isDeleted) {
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

	public Duration getDurationPlanned() {
		return durationPlanned;
	}

	public void setDurationPlanned(Duration durationPlanned) {
		this.durationPlanned = durationPlanned;
	}

	public Duration getDurationAverage() {
		return durationAverage;
	}

	public void setDurationAverage(Duration durationAverage) {
		this.durationAverage = durationAverage;
	}

	public String getIsDeleted(){ return isDeleted; }

	public void setIsDeleted(String isDeleted) { this.isDeleted = isDeleted; }

	public List<ServiceEmployeeDuration> getSedList() {
		return sedList;
	}

	public void setSedList(List<ServiceEmployeeDuration> sedList) {
		this.sedList = sedList;
	}
}