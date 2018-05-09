package client_api;

import data_models.Appointment;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAppointmentApi")
public class AppointmentApi implements IAppointmentApi{
    @Override
    public List<Appointment> getAppointments() {
        return new ArrayList<>();
    }

    @Override
    public Appointment getAppointmentById(String apointmentId) {
        return null;
    }
}
