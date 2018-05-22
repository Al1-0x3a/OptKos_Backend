package data_models;

import com.sun.istack.internal.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {

	private String appointmentId;
	private LocalDateTime endTime;
	private LocalDateTime endTimeActual;
	private LocalDateTime startTime;
	private LocalDateTime startTimeActual;
	private AppointmentType appointmentType;
	private String employeeid;
	private String customerid;

	public Appointment(){
	    this.appointmentId = UUID.randomUUID().toString();
	}

	public Appointment(String appointmentId, LocalDateTime endTime,
					   LocalDateTime startTime, String employeeid, String customerid) {
		this.appointmentId = appointmentId;
		this.endTime = endTime;
		this.startTime = startTime;
		this.employeeid = employeeid;
		this.customerid = customerid;
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

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
}