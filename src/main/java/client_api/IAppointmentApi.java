package client_api;

import data_models.Appointment;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAppointmentApi {
    @WebMethod
	List<Appointment> getAppointments();
    @WebMethod
	Appointment getAppointmentById(String apointmentId);
}