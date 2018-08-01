/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package manager;

import data_loader.SqlConnection;
import data_loader.data_access_object.AppointmentDao;
import data_models.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class AppointmentManager {
    private static LocalTime startDay = LocalTime.of(0, 0,0);
    private static LocalTime endDay = LocalTime.of(23, 55,0);

    private static final int SCALE = 5;

    public boolean isFree(Appointment appointment, Employee employee) {
        Map<Employee, List<Interval>> takenIntervals = generateIntervals(appointment.getStartTime().
                format(DateTimeFormatter.ISO_DATE), appointment.getAppointmentId());
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
        Optional<String> uuid = Optional.ofNullable(appointmentSuggestion.getAppointmentId());
        LocalDate day = appointmentSuggestion.getStartTime().toLocalDate();
        long duration = appointmentSuggestion.getService().getDurationAverage().toMinutes();
        Map<Employee, List<Interval>> employeeGaps = invert(generateIntervals(day.format(DateTimeFormatter.ISO_DATE), uuid.orElse("")));
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

    public Map<Employee, List<Interval>> generateIntervals(String date, String uuid) {
        List<AppointmentListItem> tmp = AppointmentDao.getAppointmentsByCalendarWeek(date);

        HashMap<Employee, List<Interval>> result = new HashMap<>();
        LocalDate localDate = LocalDate.parse(date);
        DayOfWeek day = localDate.getDayOfWeek();

        for (AppointmentListItem item: tmp) {
            List<Interval> intervals = new ArrayList<>();

            WorkingDay currentWorkingDay = item.getEmployee().getWorkingDays().stream().filter(w -> w.getDay().
                    equals(day.getDisplayName(TextStyle.FULL, Locale.GERMAN))).findFirst().get();

            if (currentWorkingDay.getStartWorkingTime().equals(currentWorkingDay.getEndWorkingTime())) {
                intervals.add(new Interval(startDay, endDay));
                continue;
            }
            intervals = item.getAppointmentList().stream().
                    filter(Objects::nonNull).
                    filter(a -> !a.getAppointmentId().equals(uuid)).
                    filter(a -> a.getStartTime().getDayOfWeek().equals(day)).
                    map(yikes -> new Interval(yikes.getStartTime().toLocalTime(), yikes.getEndTime().toLocalTime())).
                    collect(Collectors.toList());

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
            int scaledMinuteStart = startTime.getMinute() / SCALE * SCALE;
            int scaledMinuteEnd = (endTime.getMinute() + (SCALE - 1)) / SCALE * SCALE;
            this.startTime = LocalTime.of(startTime.getHour(), scaledMinuteStart, 0, 0);
            if (scaledMinuteEnd == 60) {
                this.endTime = LocalTime.of(endTime.getHour() + 1, 0, 0, 0);
            } else {
                this.endTime = LocalTime.of(endTime.getHour(), scaledMinuteEnd, 0, 0);
            }

        }

        public boolean isWithin(Interval interval) {
            return this.startTime.isAfter(interval.startTime) && this.endTime.isBefore(interval.endTime);
        }

        public long getDuration() {
            return Duration.between(startTime, endTime).toMinutes();
        }

        public boolean isWithinSubSlot(Interval target) {
            return this.startTime.isBefore(target.startTime) && this.endTime.isAfter(target.startTime) ||
                    this.startTime.equals(target.startTime) && this.endTime.isAfter(target.startTime);
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
        try {
            PreparedStatement preparedStatement = SqlConnection.getConnection().prepareStatement(
                    "UPDATE OPTKOS.SERVICE s " +
                            "SET s.DURATIONAVERAGE = (" +
                            "SELECT AVG(TIMESTAMPDIFF(4, CHAR(a.INDEEDTIMEEND - a.INDEEDTIMESTART))) AS avg " +
                            "FROM OPTKOS.APOINTMENT a " +
                            "WHERE a.SERVICEID = s.SERVICEID)");
            preparedStatement.executeUpdate();

            preparedStatement = SqlConnection.getConnection().prepareStatement(
                    "UPDATE OPTKOS.SERVICEEMPLOYEEDURATION s " +
                            "SET s.DURATIONAVERAGE = (" +
                            "SELECT AVG(TIMESTAMPDIFF(4, CHAR(a.INDEEDTIMEEND - a.INDEEDTIMESTART))) AS avg " +
                            "FROM OPTKOS.APOINTMENT a " +
                            "WHERE a.SERVICEID = s.SERVICEID " +
                            "AND a.EMPLOYEEID = s.EMPLOYEEID)");
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Failed to update average durations");
            e.printStackTrace();
        }
        System.out.println("Successfully updated average durations");
    }
}
