package data_models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {

	private UUID appointmentId;
	private LocalDateTime endTime;
	private LocalDateTime endTimeActual;
	private LocalDateTime startTime;
	private LocalDateTime startTimeActual;
	public AppointmentType appointmentType;
	public Employee employee;
	public Customer customer;

	public Appointment(){
	    this.appointmentId = UUID.randomUUID();
	}

	public Appointment(UUID appointmentId, LocalDateTime endTime, LocalDateTime endTimeActual,
					   LocalDateTime startTime, LocalDateTime startTimeActual
					   ) {
		this.appointmentId = appointmentId;
		this.endTime = endTime;
		this.endTimeActual = endTimeActual;
		this.startTime = startTime;
		this.startTimeActual = startTimeActual;
	}

	public UUID getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(UUID appointmentId) {
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
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}