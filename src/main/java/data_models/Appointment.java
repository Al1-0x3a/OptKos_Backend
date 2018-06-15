package data_models;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Appointment {
	private String appointmentId;
	private LocalDateTime endTime;
	private LocalDateTime endTimeActual;
	private LocalDateTime startTime;
	private LocalDateTime startTimeActual;
	private AppointmentType appointmentType;
	private String employeeid;
	private Customer customer;
	private Service service;

	public Appointment(){
	    this.appointmentId = UUID.randomUUID().toString();
	}

	public Appointment(String appointmentId, LocalDateTime endTime,
					   LocalDateTime startTime, String employeeid) {
		this.appointmentId = appointmentId;
		this.endTime = endTime;
		this.startTime = startTime;
		this.employeeid = employeeid;
	}

	public Appointment(String appointmentId, LocalDateTime endTime, LocalDateTime startTime, String employeeid,
					   LocalDateTime endTimeActual, LocalDateTime startTimeActual) {
		this.appointmentId = appointmentId;
		this.endTime = endTime;
		this.endTimeActual = endTimeActual;
		this.startTime = startTime;
		this.startTimeActual = startTimeActual;
		this.employeeid = employeeid;
	}

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		if(this.appointmentId == null)
			this.appointmentId = appointmentId;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getEndTimeActual() {
		return endTimeActual;
	}

	public void setEndTimeActual(LocalDateTime endTimeActual) {
		this.endTimeActual = endTimeActual;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getStartTimeActual() {
		return startTimeActual;
	}

	public void setStartTimeActual(LocalDateTime startTimeActual) {
		this.startTimeActual = startTimeActual;
	}

	public AppointmentType getAppointmentType() {
		return this.appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public long getAppointmentDuration(){
		return this.startTimeActual.until(endTimeActual, ChronoUnit.MINUTES);
	}
}