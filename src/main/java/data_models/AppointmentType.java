package data_models;

import java.util.UUID;

public class AppointmentType {

	private UUID appointmentTypeId;
	private String description;
	private boolean isCustomerAppointment;
	private String name;

	public AppointmentType(){
this.appointmentTypeId = UUID.randomUUID();
	}

	public AppointmentType(UUID appointmentTypeId, String name, String description) {
		this.appointmentTypeId = appointmentTypeId;
		this.name = name;
		this.description = description;
	}

	public UUID getAppointmentTypeId() {
		return appointmentTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCustomerAppointment() {
		return isCustomerAppointment;
	}

	public void setCustomerAppointment(boolean customerAppointment) {
		isCustomerAppointment = customerAppointment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
