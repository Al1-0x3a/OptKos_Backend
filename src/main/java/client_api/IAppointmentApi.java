package client_api;

import data_models.Appointment;
import data_models.AppointmentListItem;
import data_models.AppointmentSuggestion;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAppointmentApi {
    @WebMethod
    Appointment getNewAppointment();
    @WebMethod
    List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt);
    @WebMethod
	Appointment getAppointmentById(String apointmentId);
    @WebMethod
    boolean createAppointment(Appointment appointment);
    @WebMethod
    boolean updateAppointment(Appointment appointment);
    @WebMethod
    boolean isSlotFree(Appointment appointment, String ltd);
    @WebMethod
    List<AppointmentSuggestion> findSuggestions(String startTime, String endTime);
}