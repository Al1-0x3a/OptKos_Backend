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
    LocalTime startBreakTime, LocalTime endBreakTime) {
        this.workingDayId = workingDayId;
        this.startWorkingTime = startWorkingTime;
        this.endWorkingTime = endWorkingTime;
        this.startBreakTime = startBreakTime;
        this.endBreakTime = endBreakTime;
    }

    public WorkingDay(){
        this.workingDayId = UUID.randomUUID();
    }

    public WorkingDay(String dayName) {
        this.workingDayId = UUID.randomUUID();
        this.day = dayName;
    }



    public LocalTime getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(LocalTime startWorkingTime) {
        if( startWorkingTime == null){
            this.startWorkingTime = LocalTime.of(00,00);
        }else
        this.startWorkingTime = startWorkingTime;
    }

    public LocalTime getEndWorkingTime() {
        return endWorkingTime;
    }

    public void setEndWorkingTime(LocalTime endWorkingTime) {
        if( endWorkingTime == null){
            this.endWorkingTime = LocalTime.of(00,00);
        }else
        this.endWorkingTime = endWorkingTime;
    }

    public LocalTime getStartBreakTime() {
        return startBreakTime;
    }

    public void setStartBreakTime(LocalTime startBreakTime) {
        if( startBreakTime == null){
            this.startBreakTime = LocalTime.of(00,00);
        }else
        this.startBreakTime = startBreakTime;
    }

    public LocalTime getEndBreakTime() {
        return endBreakTime;
    }

    public String getDay() {
        return day;
    }

    public void setEndBreakTime(LocalTime endBreakTime) {
        if( endBreakTime == null){
            this.endBreakTime = LocalTime.of(00,00);
        }else
        this.endBreakTime = endBreakTime;
    }

    public UUID getWorkingDayId() {
        return workingDayId;
    }

    public void setWorkingDayId(UUID workingDayId) {
        this.workingDayId = workingDayId;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
