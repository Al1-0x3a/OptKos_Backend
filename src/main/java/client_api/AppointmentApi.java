package client_api;

import data_loader.data_access_object.AppointmentDao;
import data_models.Appointment;
import data_models.AppointmentListItem;
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
    public List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt) {
        return AppointmentDao.getAppointmentsByCalendarWeek(ldt);
    }

    @Override
    public Appointment getAppointmentById(String appointmentId) {
        return null;
    }

    @Override
    public boolean isSlotFree(Appointment appointment, String week) {
        return appointmentManager.isFree(appointment, week);
    }
}
