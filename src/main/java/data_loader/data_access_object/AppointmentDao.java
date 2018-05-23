package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Appointment;
import data_models.AppointmentListItem;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.IsoFields;
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
                            rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                            rs.getString("EMPLOYEEID"),
                            rs.getString("CUSTOMERID"));

                    if(rs.getTimestamp("INDEEDTIMEEND") != null){
                        appointment.setEndTimeActual(rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime());
                    }
                    if(rs.getTimestamp("INDEEDTIMESTART") != null){
                        appointment.setStartTimeActual(rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime());
                    }

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
            try(ResultSet rs = preparedStmt.executeQuery()) {

                appointment = new Appointment(rs.getString("APPOINTMENTID"),
                        rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                        rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                        rs.getString("EMPLOYEEID"),
                        rs.getString("CUSTOMERID"));

                if(rs.getTimestamp("INDEEDTIMEEND") != null){
                    appointment.setEndTimeActual(rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime());
                }
                if(rs.getTimestamp("INDEEDTIMESTART") != null){
                    appointment.setStartTimeActual(rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime());
                }
                appointment.setAppointmentType(AppointmentTypeDao.getAppointmentTypeById(
                        rs.getString("APPOINTMENTTYPEID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

// TODO: Es sollen auch Employees, welche keine Appointments haben dargestellt werden
    public static List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt){
        List<AppointmentListItem> appointmentList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT p.LASTNAME, p.FIRSTNAME, e.EMPLOYEEID, a.* FROM" +
                    " OPTKOS.EMPLOYEE e, OPTKOS.APOINTMENT a, OPTKOS.PERSON p  WHERE e.EMPLOYEEID=a.EMPLOYEEID AND " +
                    "p.PERSONID=e.PERSONID AND a.PLANTIMESTART>=? AND a.PLANTIMEEND<?");

            LocalDate localDate = LocalDate.parse(ldt);
            int weekNumber = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            LocalDate date = LocalDate.of(2018, Month.JANUARY, 10);
            LocalDate dayInWeek = date.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber);
            LocalDate start = dayInWeek.with(DayOfWeek.MONDAY);
            LocalDate end = dayInWeek.with(DayOfWeek.SUNDAY);
            preparedStmt.setTimestamp(1, Timestamp.valueOf(start.atStartOfDay()));
            preparedStmt.setTimestamp(2, Timestamp.valueOf(end.atStartOfDay()));
            ResultSet rs = preparedStmt.executeQuery();

            AppointmentListItem  ali = null;
            String tmpEmployeeId = "";

            while (rs.next()){
                String tmp = rs.getString("EMPLOYEEID");
                if(!tmp.equals(tmpEmployeeId)){
                    if(ali !=null){
                        appointmentList.add(ali);
                    }
                    tmpEmployeeId = rs.getString("EMPLOYEEID");
                    ali = new AppointmentListItem(tmpEmployeeId, rs.getString("LASTNAME"),
                            rs.getString("FIRSTNAME"));
                }
                Appointment appointment = new Appointment(rs.getString("APOINTMENTID"),
                        rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                        rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                        rs.getString("EMPLOYEEID"),
                        rs.getString("CUSTOMERID"));

                if(rs.getTimestamp("INDEEDTIMEEND") != null){
                    appointment.setEndTimeActual(rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime());
                }
                if(rs.getTimestamp("INDEEDTIMESTART") != null){
                    appointment.setStartTimeActual(rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime());
                }
                ali.addAppointment(appointment);
            }
            return appointmentList;



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
