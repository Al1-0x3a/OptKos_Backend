package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppointmentDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private AppointmentDao() {
    }

    public static List<Appointment> getAllAppointmentsFromDb() {
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.APOINTMENT");
            try (ResultSet rs = preparedStmt.executeQuery()) {

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
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.APOINTMENT a WHERE a.APOINTMENTID=?");
            preparedStmt.setString(1, appointmentId);
            ResultSet rs = preparedStmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

}
