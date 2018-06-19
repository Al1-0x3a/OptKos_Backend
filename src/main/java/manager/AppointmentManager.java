package manager;

import data_loader.data_access_object.AppointmentDao;
import data_models.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentManager {
    private static LocalTime startDay = LocalTime.of(0, 0,0);
    private static LocalTime endDay = LocalTime.of(23, 59,59);

    public boolean isFree(Appointment appointment, Employee employee) {
        Map<Employee, List<Interval>> takenIntervals = generateIntervals(appointment.getStartTime().
                format(DateTimeFormatter.ISO_DATE));
        List<Interval> employeeIntervals = takenIntervals.get(employee);
        if (employeeIntervals == null) return false;
        Interval target = new Interval(appointment.getStartTime().toLocalTime(), appointment.getEndTime().toLocalTime());
        for (Interval interval: employeeIntervals) {
            //System.out.println("Comparing target" + target +" with " + interval + " from employee " + employee.getLastname());
            if (target.startTime.isBefore(interval.endTime) && target.endTime.isAfter(interval.startTime)) {
                return false;
            }
        }
        return true;
    }

    public List<AppointmentSuggestion> findSuggestions(AppointmentSuggestion.Strategy strategy,
                                                       AppointmentSuggestion request) {
        List<AppointmentSuggestion> result = new ArrayList<>();

        if (strategy == AppointmentSuggestion.Strategy.FIRST_SLOT) {
            request.setEndTime(LocalDateTime.of(request.getStartTime().toLocalDate(), endDay));
            result = findSlots(request);
        } else if (strategy == AppointmentSuggestion.Strategy.SLOT_IN_RANGE) {
            result = findSlots(request);
        }

        return result;
    }

    private List<AppointmentSuggestion> findSlots(AppointmentSuggestion appointmentSuggestion) {
        Optional<Employee> employee = Optional.ofNullable(appointmentSuggestion.getEmployee());
        LocalDate day = appointmentSuggestion.getStartTime().toLocalDate();
        long duration = appointmentSuggestion.getService().getDurationAverage().toMinutes();
        Map<Employee, List<Interval>> employeeGaps = invert(generateIntervals(day.format(DateTimeFormatter.ISO_DATE)));
        List<AppointmentSuggestion> result = new ArrayList<>();
        Interval requestedSlot = new Interval(appointmentSuggestion.getStartTime().toLocalTime(),
                appointmentSuggestion.getEndTime().toLocalTime());

        employeeGaps.forEach((k,v) -> {
            if (employee.isPresent() && !employee.get().equals(k)) return;
            for (Interval interval : v) {
                if (interval.isWithin(requestedSlot) && interval.getDuration() >= duration) {
                    result.add(new AppointmentSuggestion(
                            LocalDateTime.of(day, interval.startTime),
                            LocalDateTime.of(day, interval.startTime.plus(Duration.ofMinutes(duration))),
                            k,
                            appointmentSuggestion.getService()));
                }
                Interval subSlot;
                if (interval.isWithinSubSlot(requestedSlot) && (subSlot = interval.trimSlotLeft(requestedSlot)).getDuration() >= duration) {
                    result.add(new AppointmentSuggestion(
                            LocalDateTime.of(day, subSlot.startTime),
                            LocalDateTime.of(day, subSlot.startTime.plus(Duration.ofMinutes(duration))),
                            k,
                            appointmentSuggestion.getService()));
                }
            }
        });
        return result;
    }

    public Map<Employee, List<Interval>> generateIntervals(String date) {
        List<AppointmentListItem> tmp = AppointmentDao.getAppointmentsByCalendarWeek(date);

        HashMap<Employee, List<Interval>> result = new HashMap<>();
        LocalDate localDate = LocalDate.parse(date);
        DayOfWeek day = localDate.getDayOfWeek();

        for (AppointmentListItem item: tmp) {
            List<Interval> intervals = item.getAppointmentList().stream().
                    filter(Objects::nonNull).
                    filter(a -> a.getStartTime().getDayOfWeek().equals(day)).
                    map(yikes -> new Interval(yikes.getStartTime().toLocalTime(), yikes.getEndTime().toLocalTime())).
                    collect(Collectors.toList());
            WorkingDay currentWorkingDay = item.getEmployee().getWorkingDays().stream().filter(w -> w.getDay().
                    equals(day.getDisplayName(TextStyle.FULL, Locale.GERMAN))).findFirst().get();

            if (currentWorkingDay.getStartWorkingTime().equals(currentWorkingDay.getEndWorkingTime())) {
                intervals.add(new Interval(startDay, endDay));
                continue;
            }

            intervals.add(new Interval(startDay, currentWorkingDay.getStartWorkingTime()));
            intervals.add(new Interval(currentWorkingDay.getEndWorkingTime(), endDay));
            if (!currentWorkingDay.getStartBreakTime().equals(currentWorkingDay.getEndBreakTime())) {
                intervals.add(new Interval(currentWorkingDay.getStartBreakTime(), currentWorkingDay.getEndBreakTime()));
            }

            intervals.sort(new IntervalComparator());
            result.put(item.getEmployee(), intervals);
        }

        return result;
    }

    public Map<Employee, List<Interval>> invert(Map<Employee, List<Interval>> original) {
        Map<Employee, List<Interval>> result = new HashMap<>();
        original.forEach((k,v) -> {
            List<Interval> intervalsForKey = new ArrayList<>();
            String gapStart = null, gapEnd;
            for (int i = 0; i < v.size(); i++) {
                Interval interval = v.get(i);
                if (i == 0) {
                    gapStart = (interval.endTime.plus(Duration.ofMinutes(1))).toString();
                    continue;
                }
                gapEnd = interval.startTime.toString();
                intervalsForKey.add(new Interval(LocalTime.parse(gapStart), LocalTime.parse(gapEnd)));
                gapStart = (interval.endTime.plus(Duration.ofMinutes(1))).toString();
            }
            result.put(k, intervalsForKey);
        });
        return result;
    }

    public class Interval {
        LocalTime startTime;
        LocalTime endTime;

        public Interval(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime.minus(Duration.ofMinutes(1));
        }

        public boolean isWithin(Interval interval) {
            return this.startTime.isAfter(interval.startTime) && this.endTime.isBefore(interval.endTime);
        }

        public long getDuration() {
            return Duration.between(startTime, endTime).toMinutes();
        }

        public boolean isWithinSubSlot(Interval target) {
            return this.startTime.isBefore(target.startTime) && this.endTime.isAfter(target.startTime);
        }

        public Interval trimSlotLeft(Interval target) {
            return new Interval(target.startTime, this.endTime);
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "startTime=" + startTime +
                    ", endTime=" + endTime +
                    '}';
        }
    }

    class IntervalComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.startTime.compareTo(o2.startTime);
        }
    }


    public void calculateAverage() {

    }
}
