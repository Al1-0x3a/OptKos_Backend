package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentDao {
    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Appointment> appointmentList = new ArrayList<>();

    private AppointmentDao() {
    }

    public static List<Appointment> getAllAppointmentsFromDb() {
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENT";
            try (ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Appointment appointment = new Appointment(rs.getString("APPOINTMENTID"),
                            rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                            rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime(),
                            rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                            rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime());

                    appointment.setEmployee(EmployeeDao.getEmployeeById(
                            rs.getString("EMPLOYEEID")));

                    appointment.setCustomer(CustomerDao.getCustomerById(
                            rs.getString("CUSTOMERID")));

                    appointment.setAppointmentType(AppointmentTypeDao.getAppointmentTypeById(
                            rs.getString("APPOINTMENTTYPEID")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public static Appointment getAppointmentById(String appointmentId){
        Appointment appointment = null;
        for(int i = 0; i< appointmentList.size(); i++){
            if(appointmentList != null && appointmentList.get(i).getAppointmentId() == appointmentId) {
                appointment = appointmentList.get(i);
                break;
            }
        }

        if (appointment == null) {
            appointment = getAppointmentByIdFromDb(appointmentId);
            if (appointment != null)
                appointmentList.add(appointment);
        }
        return appointment;
    }

    public static Appointment getAppointmentByIdFromDb(String appointmentId){
        Appointment appointment = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENT a WHERE a.APOINTMENTID=" + appointmentId.toString() + ";";
            try (ResultSet rs = stmt.executeQuery(query)) {

                appointment = new Appointment(rs.getString("APPOINTMENTID"),
                        rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                        rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime(),
                        rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                        rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime());

                appointment.setEmployee(EmployeeDao.getEmployeeById(
                        rs.getString("EMPLOYEEID")));

                appointment.setCustomer(CustomerDao.getCustomerById(rs.getString("CUSTOMERID")));

                appointment.setAppointmentType(AppointmentTypeDao.getAppointmentTypeById(
                        rs.getString("APPOINTMENTTYPEID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

}
