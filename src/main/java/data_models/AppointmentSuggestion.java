package data_models;

import java.time.LocalDateTime;

public class AppointmentSuggestion {
    public enum Strategy {FIRST_SLOT, SLOT_IN_RANGE}

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Service service;
    private Employee employee;

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
}
