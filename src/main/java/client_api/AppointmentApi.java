package client_api;

import com.migesok.jaxb.adapter.javatime.LocalDateXmlAdapter;
import data_loader.data_access_object.AppointmentDao;
import data_models.Appointment;
import data_models.AppointmentListItem;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
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
