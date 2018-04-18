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

	}

	public void finalize() throws Throwable {

	}
}