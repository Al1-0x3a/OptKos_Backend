package manager;

import data_loader.data_access_object.AppointmentDao;
import data_models.Appointment;
import data_models.AppointmentListItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {

    public boolean isFree(Appointment appointment, String ldt) {
        List<AppointmentListItem> appointmentListItems = AppointmentDao.getAppointmentsByCalendarWeek(ldt);
        for (AppointmentListItem appointmentListItem: appointmentListItems) {
            if (appointment.getEmployeeid().equals(appointmentListItem.getEmployeeId())) {
                List<Appointment> employeeAppointments = appointmentListItem.getAppointmentList();
                for (Appointment employeeAppointment: employeeAppointments) {
                    if (isCollision(employeeAppointment, appointment)) return false;
                }
            }
        }
        return true;
    }

    private boolean isCollision(Appointment fromExisting, Appointment target) {
        LocalDateTime targetStart = target.getStartTime();
        LocalDateTime targetEnd = target.getEndTime();
        LocalDateTime existingStart = fromExisting.getStartTime();
        LocalDateTime existingEnd = fromExisting.getEndTime();
        return (targetStart.isBefore(existingEnd) && targetEnd.isAfter(existingStart));
    }
}
