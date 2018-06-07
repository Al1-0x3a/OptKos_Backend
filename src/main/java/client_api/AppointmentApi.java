package client_api;

import data_loader.data_access_object.AppointmentDao;
import data_models.Appointment;
import data_models.AppointmentListItem;
import data_models.AppointmentSuggestion;
import data_models.Employee;
import manager.AppointmentManager;

import javax.jws.WebService;
import java.util.List;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAppointmentApi")
public class AppointmentApi implements IAppointmentApi{
    private final AppointmentManager appointmentManager;

    public AppointmentApi() {
        appointmentManager = new AppointmentManager();
    }

    @Override
    public Appointment getNewAppointment() {
        return new Appointment();
    }

    @Override
    public List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt) {
        return AppointmentDao.getAppointmentsByCalendarWeek(ldt);
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
    public boolean isSlotFree(Appointment appointment, Employee employee) {
        return appointmentManager.isFree(appointment, employee);
    }

    @Override
    public List<AppointmentSuggestion> findSuggestions(AppointmentSuggestion.Strategy strategy,
                                                       AppointmentSuggestion suggestion) {
        return appointmentManager.findSuggestions(strategy, suggestion);
    }
}
