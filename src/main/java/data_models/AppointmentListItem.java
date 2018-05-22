package data_models;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListItem {

    private String employeeId, name, firstname;
    private List<Appointment> appointmentList;

    public AppointmentListItem(String employeeId, String name, String firstname){
        this.employeeId = employeeId;
        this.name = name;
        this.firstname = firstname;
        this.appointmentList = new ArrayList<>();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
