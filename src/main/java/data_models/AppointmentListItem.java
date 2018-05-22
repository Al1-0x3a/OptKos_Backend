package data_models;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListItem {

    private String employeeId;
    private List<Appointment> appointmentList;

    public AppointmentListItem(String employeeId){
        this.employeeId = employeeId;
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
}
