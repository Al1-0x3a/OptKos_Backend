package client_api;

import data_models.Appointment;

import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@WebService(endpointInterface = "client_api.IAppointmentApi")
public class AppointmentApi implements IAppointmentApi{
    @Override
    public List<Appointment> getAppointment() {
        return null;
    }

    @Override
    public Appointment getApointment(UUID apointmentId) {
        return null;
    }
}
