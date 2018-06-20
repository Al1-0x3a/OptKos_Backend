package client_api;

import data_loader.data_access_object.AppointmentDao;
import data_loader.data_access_object.AppointmentTypeDao;
import data_models.*;
import manager.AppointmentManager;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAppointmentApi")
public class AppointmentApi implements IAppointmentApi {
    long start;

    private final AppointmentManager appointmentManager;

    public AppointmentApi() {
        appointmentManager = new AppointmentManager();
    }

    @Override
    public Appointment getNewAppointment() {
        return new Appointment(UUID.randomUUID().toString());
    }

    @Override
    public Appointment getPreviousAppointment(String customerID) {
        return AppointmentDao.getPreviousAppointmentByCustomerID(customerID);
    }

    @Override
    public Appointment getNextAppointment(String customerID) {
        return AppointmentDao.getNextAppointmentByCustomerID(customerID);
    }

    @Override
    public List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt) {
        String text = "Get all Apoointments by Calenderweek from Api";
        this.time(true, text);
        List<AppointmentListItem> appointmentListItems = AppointmentDao.getAppointmentsByCalendarWeek(ldt);
        this.time(false, text);
        return appointmentListItems;
    }

    @Override
    public Appointment getAppointmentById(String appointmentId) {
        return null;
    }

    @Override
    public boolean createAppointment(Appointment appointment) {
        return AppointmentDao.createAppointment(appointment);
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        return AppointmentDao.updateAppointment(appointment);
    }

    @Override
    public boolean deleteAppointment(String uuid) {
        return AppointmentDao.deleteAppointment(uuid);
    }

    @Override
    public boolean isSlotFree(Appointment appointment, Employee employee) {
        return appointmentManager.isFree(appointment, employee);
    }

    @Override
    public List<AppointmentSuggestion> findSuggestions(AppointmentSuggestion.Strategy strategy,
                                                       AppointmentSuggestion suggestion) {
        String text = "Get Appointment Suggestions from Api";
        this.time(true, text);
        List<AppointmentSuggestion> appointmentSuggestions = appointmentManager.findSuggestions(strategy, suggestion);
        this.time(false, text);
        return appointmentSuggestions;
    }

    @Override
    public List<AppointmentType> getAllAppointmentTypes() {
        return AppointmentTypeDao.getAllAppointmentTypesFromDb();
    }

    @Override
    public void appointmentStart(Appointment appointment) {
        appointment.setStartTimeActual(LocalDateTime.now());
        AppointmentDao.updateAppointment(appointment);
    }

    @Override
    public void appointmentEnd(Appointment appointment) {
        appointment.setEndTimeActual(LocalDateTime.now());
        AppointmentDao.updateAppointment(appointment);
    }

    public void time(boolean isStart, String text) {
        if (isStart) {
            this.start = System.nanoTime();
        } else {
            System.out.println(text +": " + (System.nanoTime() - this.start) / 1e6 + " ms");
        }
    }

    @Override
    public void calculateAverage() {
        appointmentManager.calculateAverage();
    }
}
