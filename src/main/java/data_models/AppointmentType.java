package data_models;

import java.util.UUID;

public class AppointmentType {

	private String appointmentTypeId;
	private String description;
	private boolean isCustomerAppointment;
	private String name;

	public AppointmentType(){
this.appointmentTypeId = UUID.randomUUID().toString();
	}

	public AppointmentType(String appointmentTypeId, String name, String description) {
		this.appointmentTypeId = appointmentTypeId;
		this.name = name;
		this.description = description;
	}

	public String getAppointmentTypeId() {
		return appointmentTypeId;
	}

	public void setAppointmentTypeId(String appointmentTypeId) {
		this.appointmentTypeId = appointmentTypeId;
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
