package manager;

import data_loader.data_access_object.AppointmentDao;
import data_models.Appointment;
import data_models.AppointmentListItem;
import data_models.Employee;
import data_models.WorkingDay;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentManagerAufCrack {
    private static LocalTime startDay = LocalTime.of(0, 0,0);
    private static LocalTime endDay = LocalTime.of(23, 59,59);

    public boolean isFree(Appointment appointment, Employee employee) {
        Map<Employee, List<Interval>> takenIntervals = generateIntervals(appointment.getStartTime().format(DateTimeFormatter.ISO_DATE));
        List<Interval> employeeIntervals = takenIntervals.get(employee);
        Interval target = new Interval(appointment.getStartTime().toLocalTime(), appointment.getEndTime().toLocalTime());
        for (Interval interval: employeeIntervals) {
            if (target.startTime.isBefore(interval.endTime) && target.endTime.isAfter(interval.startTime)) {
                return false;
            }
        }
        return true;
    }

    public Map<Employee, List<Interval>> generateIntervals(String date) {
        long start = System.currentTimeMillis();
        List<AppointmentListItem> tmp = AppointmentDao.getAppointmentsByCalendarWeek(date);
        long endDao = System.currentTimeMillis();

        HashMap<Employee, List<Interval>> result = new HashMap<>();
        LocalDate localDate = LocalDate.parse(date);
        DayOfWeek day = localDate.getDayOfWeek();

        for (AppointmentListItem item: tmp) {
            List<Interval> intervals = item.getAppointmentList().stream().
                    filter(a -> a.getStartTime().getDayOfWeek().equals(day)).
                    map(yikes -> new Interval(yikes.getStartTime().toLocalTime(), yikes.getEndTime().toLocalTime())).
                    collect(Collectors.toList());
            WorkingDay currentWorkingDay = item.getEmployee().getWorkingDays().stream().filter(w -> w.getDay().
                    equals(day.getDisplayName(TextStyle.FULL, Locale.GERMAN))).findFirst().get();
            intervals.add(new Interval(startDay, currentWorkingDay.getStartWorkingTime()));
            if (!currentWorkingDay.getStartBreakTime().equals(currentWorkingDay.getEndBreakTime())) {
                intervals.add(new Interval(currentWorkingDay.getStartBreakTime(), currentWorkingDay.getEndBreakTime()));
            }
            intervals.add(new Interval(currentWorkingDay.getEndWorkingTime(), endDay));
            intervals.sort(new IntervalComparator());
            result.put(item.getEmployee(), intervals);
        }
        long end = System.currentTimeMillis();
        System.out.printf("getAppointmentsByCalendarWeek took %d ms, generateIntervals took %d ms%n",
                (endDao - start), (end - endDao));
        return result;
    }

    public class Interval {
        LocalTime startTime;
        LocalTime endTime;

        public Interval(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    class IntervalComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.startTime.compareTo(o2.startTime);
        }
    }
}
