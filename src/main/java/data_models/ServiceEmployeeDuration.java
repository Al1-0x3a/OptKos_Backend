package data_models;

import java.time.Duration;

public class ServiceEmployeeDuration {

	private Duration durationPlanned;
	private Duration durationAverage;
	private String employeeId;
	private String serviceId;
	private String firstName, lastName;

	public ServiceEmployeeDuration(){

	}

	public ServiceEmployeeDuration(Duration durationPlanned, Duration durationAverage, String employeeId,
								   String serviceId, String lastName, String firstName) {
		this.durationPlanned = durationPlanned;
		this.durationAverage = durationAverage;
		this.employeeId = employeeId;
		this.serviceId = serviceId;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}