package data_models;

import java.time.LocalTime;
import java.util.UUID;

public class WorkingDay {

    private UUID workingDayId;
    private LocalTime startWorkingTime;
    private LocalTime endWorkingTime;
    private LocalTime startBreakTime;
    private LocalTime endBreakTime;
    private String day;

    public WorkingDay(UUID workingDayId, LocalTime startWorkingTime, LocalTime endWorkingTime,
    LocalTime startBreakTime, LocalTime endBreakTime, String day) {
        this.workingDayId = workingDayId;
        this.startWorkingTime = startWorkingTime;
        this.endWorkingTime = endWorkingTime;
        this.startBreakTime = startBreakTime;
        this.endBreakTime = endBreakTime;
        this.day = day;
    }

    public WorkingDay() {
    }


    public LocalTime getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(LocalTime startWorkingTime) {
        this.startWorkingTime = startWorkingTime;
    }

    public LocalTime getEndWorkingTime() {
        return endWorkingTime;
    }

    public void setEndWorkingTime(LocalTime endWorkingTime) {
        this.endWorkingTime = endWorkingTime;
    }

    public LocalTime getStartBreakTime() {
        return startBreakTime;
    }

    public void setStartBreakTime(LocalTime startBreakTime) {
        this.startBreakTime = startBreakTime;
    }

    public LocalTime getEndBreakTime() {
        return endBreakTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setEndBreakTime(LocalTime endBreakTime) {
        this.endBreakTime = endBreakTime;
    }

    public UUID getWorkingDayId() {
        return workingDayId;
    }

    public void setWorkingDayId(UUID workingDayId) {
        this.workingDayId = workingDayId;
    }
}
