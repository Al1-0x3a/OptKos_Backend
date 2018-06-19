package data_models;

import java.time.LocalTime;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;

public class WorkingDay {

    private String workingDayId;
    private LocalTime startWorkingTime;
    private LocalTime endWorkingTime;
    private LocalTime startBreakTime;
    private LocalTime endBreakTime;
    private String employeeId;
    private String day;

    public WorkingDay(String workingDayId, LocalTime startWorkingTime, LocalTime endWorkingTime,
    LocalTime startBreakTime, LocalTime endBreakTime, String employeeId, String day) {
        this.workingDayId = workingDayId;
        this.startWorkingTime = startWorkingTime;
        this.endWorkingTime = endWorkingTime;
        this.startBreakTime = startBreakTime;
        this.endBreakTime = endBreakTime;
        this.employeeId = employeeId;
        this.day = day;
    }

    public WorkingDay(){
        this.workingDayId = UUID.randomUUID().toString();
    }

    public WorkingDay(String dayName) {
        this.workingDayId = UUID.randomUUID().toString();
        this.day = dayName;
    }


    public LocalTime getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(LocalTime startWorkingTime) {
        if (startWorkingTime == null) {
            this.startWorkingTime = LocalTime.of(0, 0);
        } else
            this.startWorkingTime = startWorkingTime;
    }

    public LocalTime getEndWorkingTime() {
        return endWorkingTime;
    }

    public void setEndWorkingTime(LocalTime endWorkingTime) {
        if (endWorkingTime == null) {
            this.endWorkingTime = LocalTime.of(0, 0);
        } else
            this.endWorkingTime = endWorkingTime;
    }

    public LocalTime getStartBreakTime() {
        return startBreakTime;
    }

    public void setStartBreakTime(LocalTime startBreakTime) {
        if (startBreakTime == null) {
            this.startBreakTime = LocalTime.of(0, 0);
        } else
            this.startBreakTime = startBreakTime;
    }

    public LocalTime getEndBreakTime() {
        return endBreakTime;
    }

    public String getDay() {
        return day;
    }

    public void setEndBreakTime(LocalTime endBreakTime) {
        if (endBreakTime == null) {
            this.endBreakTime = LocalTime.of(0, 0);
        } else
            this.endBreakTime = endBreakTime;
    }

    public String getWorkingDayId() {
        return workingDayId;
    }

    public void setWorkingDayId(String workingDayId) {
        this.workingDayId = workingDayId;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public long getWorkingTimeInMinutes(){
        long workingtime = startWorkingTime.until(endWorkingTime, MINUTES);
        long breaktime = startBreakTime.until(endBreakTime, MINUTES);
        return workingtime - breaktime;
    }
}
