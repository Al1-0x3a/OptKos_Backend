package manager;

import data_loader.data_access_object.AppointmentDao;
import data_models.Appointment;
import data_models.AppointmentListItem;
import data_models.Employee;
import data_models.WorkingDay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class AppointmentManager {
    // use this option for the generator
    public static final int STATIC_FETCH = 0;
    // use this option for requests from the frontend
    public static final int DYNAMIC_FETCH = 1;

    public boolean isFree(Appointment appointment, String week, int strategy) {
        List<AppointmentListItem> appointmentListItems;
        if (strategy == STATIC_FETCH) {
            appointmentListItems = AppointmentDao.getAppointmentsByCalendarWeekFast(week);
        } else if (strategy == DYNAMIC_FETCH) {
            appointmentListItems = AppointmentDao.getAppointmentsByCalendarWeek(week);
        } else {
            System.err.println("Invalid fetch strategy: " + strategy);
            return false;
        }
        if (appointmentListItems == null) {
            return true;
        }
        for (AppointmentListItem appointmentListItem: appointmentListItems) {
            if (appointment.getEmployeeid().equals(appointmentListItem.getEmployee().getEmployeeId())) {
                // check if within working day and not within break time
                Employee currentEmployee = appointmentListItem.getEmployee();
                LocalDate currentWeek = LocalDate.parse(week);
                WorkingDay currentWorkingDay = currentEmployee.getWorkingDays().stream().filter(w -> w.getDay().
                        equals(currentWeek.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMAN))).
                        findFirst().get();
                LocalDateTime startWork = LocalDateTime.of(currentWeek, currentWorkingDay.getStartWorkingTime());
                LocalDateTime endWork = LocalDateTime.of(currentWeek, currentWorkingDay.getEndWorkingTime());
                LocalDateTime startBreak = LocalDateTime.of(currentWeek, currentWorkingDay.getStartBreakTime());
                LocalDateTime endBreak = LocalDateTime.of(currentWeek, currentWorkingDay.getEndBreakTime());

                if (appointment.getStartTime().isBefore(startWork) || appointment.getEndTime().isAfter(endWork)) 
                    return false;
                
                if (isCollision(appointment.getStartTime(), appointment.getEndTime(), startBreak, endBreak)) 
                    return false;

                List<Appointment> employeeAppointments = appointmentListItem.getAppointmentList();
                for (Appointment employeeAppointment: employeeAppointments) {
                    // check if slot not already taken
                    if (employeeAppointment == null) {
                        return true;
                    }
                    if (isCollision(appointment.getStartTime(), appointment.getEndTime(), 
                            employeeAppointment.getStartTime(), employeeAppointment.getEndTime())) return false;
                }
            }
        }
        return true;
    }

    private boolean isCollision(LocalDateTime targetStart, LocalDateTime targetEnd, LocalDateTime existingStart,
                                LocalDateTime existingEnd) {
        if (targetStart.equals(targetEnd)) return false;
        return (targetStart.isBefore(existingEnd) && targetEnd.isAfter(existingStart));
    }
}
