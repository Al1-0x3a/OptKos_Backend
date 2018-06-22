package client_api;

import data_models.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAppointmentApi {
    @WebMethod
    Appointment getNewAppointment();
    @WebMethod
    Appointment getPreviousAppointment(String customerID);
    @WebMethod
    Appointment getNextAppointment(String customerID);
    @WebMethod
    List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt);
    @WebMethod
    boolean createAppointment(Appointment appointment);
    @WebMethod
    boolean updateAppointment(Appointment appointment);
    @WebMethod
    boolean deleteAppointment(String uuid);
    @WebMethod
    boolean isSlotFree(Appointment appointment, Employee employee);
    @WebMethod
    List<AppointmentSuggestion> findSuggestions(AppointmentSuggestion.Strategy strategy,
                                                AppointmentSuggestion suggestion);
    @WebMethod
    List<AppointmentType> getAllAppointmentTypes();
    @WebMethod
    void appointmentStart(Appointment appointment);
    @WebMethod
    void appointmentEnd(Appointment appointment);
    @WebMethod
    void calculateAverage();
}