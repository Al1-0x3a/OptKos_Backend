package client_api;

import data_models.Appointment;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@WebService
public interface IAppointmentApi {
    @WebMethod
	List<Appointment> getAppointment();
    @WebMethod
	Appointment getApointment(UUID apointmentId);

	// public List<Apointment> getApointment();

	// public void getApointment();

}