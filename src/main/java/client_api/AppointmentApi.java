package client_api;

import data_loader.data_access_object.AppointmentDao;
import data_models.Appointment;
import data_models.AppointmentListItem;

import javax.jws.WebService;
import java.util.List;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAppointmentApi")
public class AppointmentApi implements IAppointmentApi{

    @Override
    public List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt) {
        return AppointmentDao.getAppointmentsByCalendarWeek(ldt);
        // return null;
    }

    @Override
    public Appointment getAppointmentById(String appointmentId) {
        return null;
    }
}
