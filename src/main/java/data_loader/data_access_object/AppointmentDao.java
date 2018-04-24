package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Appointment;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class AppointmentDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Appointment> appointmentList;

    public static List<Appointment> getAllAppointmentsFromDb(){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENT";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                Appointment appointment = new Appointment(UUID.fromString(rs.getString("APPOINTMENTID")),
                        rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                        rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime(),
                        rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                        rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime());

                appointment.setEmployee(EmployeeDao.getEmployeeById(UUID.fromString(
                        rs.getString("EMPLOYEEID"))));

                appointment.setCustomer(CustomerDao.getCustomerById(UUID.fromString(
                        rs.getString("CUSTOMERID"))));

                appointment.setAppointmentType(AppointmentTypeDao.getAppointmentTypeById(
                        UUID.fromString(rs.getString("APPOINTMENTTYPEID"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public static Appointment getAppointmentById(UUID appointmentId){
        Appointment app = null;
        for(int i = 0; i< appointmentList.size(); i++){
            if(appointmentList != null && appointmentList.get(i).getAppointmentId() == appointmentId) {
                app = appointmentList.get(i);
                break;
            }
        }

        if( app == null){
            app = getAppointmentByIdFromDb(appointmentId);
            if(app != null)
                appointmentList.add(app);
        }
        return app;
    }

    public static Appointment getAppointmentByIdFromDb(UUID appointmentId){
        Appointment appointment = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENT a WHERE a.APOINTMENTID=" + appointmentId.toString() + ";";
            ResultSet rs = stmt.executeQuery(query);

            appointment = new Appointment(UUID.fromString(rs.getString("APPOINTMENTID")),
                    rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                    rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime(),
                    rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                    rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime());

            appointment.setEmployee(EmployeeDao.getEmployeeById(UUID.fromString(
                    rs.getString("EMPLOYEEID"))));

            appointment.setCustomer(CustomerDao.getCustomerById(UUID.fromString(rs.getString("CUSTOMERID"))));

            appointment.setAppointmentType(AppointmentTypeDao.getAppointmentTypeById(
                    UUID.fromString(rs.getString("APPOINTMENTTYPEID"))));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

}
