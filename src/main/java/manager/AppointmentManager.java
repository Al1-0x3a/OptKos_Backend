package manager;

import data_loader.data_access_object.AppointmentDao;
import data_loader.data_access_object.EmployeeDao;
import data_models.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

public class AppointmentManager {

    public boolean isFree(Appointment appointment, String week, List<AppointmentListItem> appointmentListItems) {
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
                        findFirst().orElse(null);
                if (currentWorkingDay == null) {
                    System.err.println("Invalid working day");
                    return false;
                }
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

    public List<AppointmentSuggestion> findSuggestions(LocalDateTime startTime, LocalDateTime endTime) {
        List<Employee> employees = EmployeeDao.getAllEmployeesFromDb();
        String week = startTime.format(DateTimeFormatter.ISO_DATE);
        List<AppointmentListItem> appointmentListItems = AppointmentDao.getAppointmentsByCalendarWeek(week);
        List<AppointmentSuggestion> appointmentSuggestions = new ArrayList<>();
        for (Employee employee: employees) {
            Appointment tmp = new Appointment(UUID.randomUUID().toString(), endTime, startTime, employee.getEmployeeId());
            if (isFree(tmp, week, appointmentListItems)) {
                appointmentSuggestions.add(new AppointmentSuggestion(startTime, endTime, employee));
            }
        }
        return appointmentSuggestions;
    }

}
