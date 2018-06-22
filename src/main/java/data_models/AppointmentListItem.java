package data_models;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListItem {

    private Employee employee;
    private List<Appointment> appointmentList;

    public AppointmentListItem(Employee employee){
        this.employee = employee;
        this.appointmentList = new ArrayList<>();
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public void addAppointment(Appointment appointment){
        this.appointmentList.add(appointment);
    }
}
