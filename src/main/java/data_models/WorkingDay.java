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
