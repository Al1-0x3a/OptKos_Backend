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

import java.time.LocalDateTime;

public class AppointmentSuggestion {
    public enum Strategy {FIRST_SLOT, SLOT_IN_RANGE}

    private String appointmentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Service service;
    private Employee employee;

    public AppointmentSuggestion(){}

    public AppointmentSuggestion(LocalDateTime startTime, Service service) {
        this.startTime = startTime;
        this.service = service;
    }

    public AppointmentSuggestion(LocalDateTime startTime, Employee employee, Service service) {
        this.startTime = startTime;
        this.employee = employee;
        this.service = service;
    }

    public AppointmentSuggestion(LocalDateTime startTime, LocalDateTime endTime, Service service) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.service = service;
    }

    public AppointmentSuggestion(LocalDateTime startTime, LocalDateTime endTime, Employee employee, Service service) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
        this.service = service;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Service getService() {
        return service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
}
