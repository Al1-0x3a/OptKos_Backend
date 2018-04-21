package data_models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {

	private UUID appointmentId;
	private LocalDateTime endTime;
	private LocalDateTime endTimeActual;
	private LocalDateTime startTime;
	private LocalDateTime startTimeActual;
	public AppointmentType m_AppointmentType;
	public Employee m_Employee;
	public Customer m_Customer;

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

	public AppointmentType getM_AppointmentType() {
		return m_AppointmentType;
	}

	public void setM_AppointmentType(AppointmentType m_AppointmentType) {
		this.m_AppointmentType = m_AppointmentType;
	}

	public Employee getM_Employee() {
		return m_Employee;
	}

	public void setM_Employee(Employee m_Employee) {
		this.m_Employee = m_Employee;
	}

	public Customer getM_Customer() {
		return m_Customer;
	}

	public void setM_Customer(Customer m_Customer) {
		this.m_Customer = m_Customer;
	}
}