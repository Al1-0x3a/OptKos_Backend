package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Appointment;
import data_models.AppointmentListItem;
import data_models.Customer;
import data_models.Service;
import data_models.Employee;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private AppointmentDao() {
    }

    public static List<Appointment> getAllAppointmentsFromDb() {
        List<Appointment> appointmentList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.APOINTMENT");

            /*Get All Customers*/
            List<Customer> customerList = CustomerDao.getAllCustomersFromDb();

            try (ResultSet rs = preparedStmt.executeQuery()) {

                while (rs.next()) {
                    Appointment appointment = new Appointment(rs.getString("APOINTMENTID"),
                            rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                            rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                            rs.getString("EMPLOYEEID"));

                    /*Search for matching customer and set it in appointment*/
                    String customerId = rs.getString("CUSTOMERID");
                    for (Customer customer :
                            customerList) {
                        if(customer.getCustomerId().equals(customerId)) {
                            appointment.setCustomer(customer);
                            break;
                        }
                    }
                    /*Add Service to appointment*/
                    Service service = new Service(rs.getString("SERVICEID"),
                            rs.getString("NAME"), rs.getString("DESCRIPTION"),
                            rs.getBigDecimal("PRICE"), Duration.ofMinutes(
                            rs.getInt("DURTATIONPLANNED")),
                            Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                            rs.getString("ISDELETED"));

                    appointment.setService(service);
                    if(rs.getTimestamp("INDEEDTIMEEND") != null){
                        appointment.setEndTimeActual(rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime());
                    }
                    if(rs.getTimestamp("INDEEDTIMESTART") != null){
                        appointment.setStartTimeActual(rs.getTimestamp("INDEEDTIMESTART")
                                .toLocalDateTime());
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
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.APOINTMENT a, OPTKOS.SERVICE s" +
                    " WHERE a.APOINTMENTID=? AND s.SERVICEID=a.SERVICEID");
            preparedStmt.setString(1, appointmentId);
            try(ResultSet rs = preparedStmt.executeQuery()) {

                appointment = new Appointment(rs.getString("APPOINTMENTID"),
                        rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                        rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                        rs.getString("EMPLOYEEID"));

                appointment.setCustomer(CustomerDao.getCustomerById(rs.getString("CUSTOMERID")));

                /*Add Service to appointment*/
                Service service = new Service(rs.getString("SERVICEID"),
                        rs.getString("NAME"), rs.getString("DESCRIPTION"),
                        rs.getBigDecimal("PRICE"), Duration.ofMinutes(
                        rs.getInt("DURTATIONPLANNED")),
                        Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                        rs.getString("ISDELETED"));

                appointment.setService(service);
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

    // only use for generator
    static List<Employee> employees = EmployeeDao.getAllEmployeesFromDb();
    static List<Customer> customerList = CustomerDao.getAllCustomersFromDb();

    public static List<AppointmentListItem> getAppointmentsByCalendarWeek(String ldt){
        // List<Employee> employees = EmployeeDao.getAllEmployeesFromDb();
        // List<Customer> customerList = CustomerDao.getAllCustomersFromDb();

        List<AppointmentListItem> appointmentList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.EMPLOYEE e JOIN OPTKOS.PERSON p ON" +
                    " e.PERSONID = p.PERSONID LEFT JOIN(SELECT * FROM OPTKOS.APOINTMENT a " +
                    "WHERE a.PLANTIMESTART>=? AND a.PLANTIMEEND<?) a ON " +
                    "e.EMPLOYEEID = a.EMPLOYEEID LEFT JOIN OPTKOS.SERVICE s ON a.SERVICEID=s.SERVICEID " +
                    "ORDER BY e.EMPLOYEEID");


            LocalDate localDate = LocalDate.parse(ldt);
            int weekNumber = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            /*Get the first and last day of Calendarweek*/
            LocalDate date = LocalDate.of(2018, Month.JANUARY, 10);
            LocalDate dayInWeek = date.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber);
            LocalDate startDay = dayInWeek.with(DayOfWeek.MONDAY);
            LocalDate endDay = dayInWeek.with(DayOfWeek.SUNDAY);
            preparedStmt.setTimestamp(1, Timestamp.valueOf(startDay.atStartOfDay()));
            preparedStmt.setTimestamp(2, Timestamp.valueOf(endDay.atStartOfDay()));
            AppointmentListItem ali;
            try (ResultSet rs = preparedStmt.executeQuery()) {
                /*Get All Customers*/

                ali = null;
                String tmpEmployeeId = "";

                while (rs.next()) {
                    String tmp = rs.getString("EMPLOYEEID");
                    if (!tmp.equals(tmpEmployeeId)) {
                        if (ali != null) {
                            appointmentList.add(ali);
                        }
                        tmpEmployeeId = rs.getString("EMPLOYEEID");
                        ali = new AppointmentListItem(employees.stream().filter(e -> e.getEmployeeId().equals(tmp))
                                .findFirst().get());
                    }
                    Appointment appointment = null;
                    if (rs.getString("APOINTMENTID") != null) {
                        /*Build Appointment*/
                        appointment = new Appointment(rs.getString("APOINTMENTID"),
                                rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                                rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                                rs.getString("EMPLOYEEID"));

                        /*Search for matching customer and set it in appointment*/
                        String customerid = rs.getString("CUSTOMERID");
                        for (Customer customer :
                                customerList) {
                            if (customer.getCustomerId().equals(customerid)) {
                                appointment.setCustomer(customer);
                                break;
                            }
                        }
                        /*Add Service to appointment*/
                        Service service = new Service(rs.getString("SERVICEID"),
                                rs.getString("NAME"), rs.getString("DESCRIPTION"),
                                rs.getBigDecimal("PRICE"), Duration.ofMinutes(
                                rs.getInt("DURTATIONPLANNED")),
                                Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                                rs.getString("ISDELETED"));

                        appointment.setService(service);
                    }
                    /*Check if indeedtime exists and add it*/
                    if (rs.getTimestamp("INDEEDTIMEEND") != null) {
                        appointment.setEndTimeActual(rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime());
                    }
                    if (rs.getTimestamp("INDEEDTIMESTART") != null) {
                        appointment.setStartTimeActual(rs.getTimestamp("INDEEDTIMESTART")
                                .toLocalDateTime());
                    }

                    ali.addAppointment(appointment);
                }
                preparedStmt.close();
            }
            appointmentList.add(ali);
            return appointmentList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateAppointment(Appointment appointment){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.APOINTMENT SET PLANTIMESTART=?, PLANTIMEEND=?, " +
                    "INDEEDTIMESTART=?, INDEEDTIMEEND=?, EMPLOYEEID=?, CUSTOMERID=?, APOINTMENTTYPEID=?, SERVICEID=? " +
                    "WHERE APOINTMENTID=?");
            preparedStmt.setTimestamp(1, Timestamp.valueOf(appointment.getStartTime()));
            preparedStmt.setTimestamp(2, Timestamp.valueOf(appointment.getEndTime()));
            preparedStmt.setTimestamp(3, appointment.getStartTimeActual() == null ?
                    null : Timestamp.valueOf(appointment.getStartTimeActual()));
            preparedStmt.setTimestamp(4, appointment.getEndTimeActual() == null ?
                    null : Timestamp.valueOf(appointment.getEndTimeActual()));
            preparedStmt.setString(5, appointment.getEmployeeid());
            preparedStmt.setString(6, appointment.getCustomer().getCustomerId());
            preparedStmt.setString(7, appointment.getAppointmentType().getAppointmentTypeId());
            preparedStmt.setString(8, appointment.getService().getServiceId());
            preparedStmt.setString(9, appointment.getAppointmentId());

            return preparedStmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean createAppointment(Appointment appointment){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.APOINTMENT (APOINTMENTID, PLANTIMESTART," +
                    " PLANTIMEEND, INDEEDTIMESTART, INDEEDTIMEEND, EMPLOYEEID, CUSTOMERID," +
                    " APOINTMENTTYPEID, SERVICEID) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStmt.setString(1, appointment.getAppointmentId());
            preparedStmt.setTimestamp(2, Timestamp.valueOf(appointment.getStartTime()));
            preparedStmt.setTimestamp(3, Timestamp.valueOf(appointment.getEndTime()));
            preparedStmt.setTimestamp(4, appointment.getStartTimeActual() == null ?
                    null : Timestamp.valueOf(appointment.getStartTimeActual()));
            preparedStmt.setTimestamp(5, appointment.getEndTimeActual() == null ?
                    null : Timestamp.valueOf(appointment.getEndTimeActual()));
            preparedStmt.setString(6, appointment.getEmployeeid());
            preparedStmt.setString(7, appointment.getCustomer().getCustomerId());
            preparedStmt.setString(8, appointment.getAppointmentType().getAppointmentTypeId());
            preparedStmt.setString(9, appointment.getService().getServiceId());


            return preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Appointment> getAllAppointmentsInTimespan(LocalDate start, LocalDate end){
        List<Appointment> appointments = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT a.* FROM OPTKOS.APOINTMENT a, OPTKOS.EMPLOYEE e WHERE PLANTIMESTART>? " +
                    "AND PLANTIMEEND<? AND a.EMPLOYEEID=e.EMPLOYEEID");
            preparedStmt.setTimestamp(1, Timestamp.valueOf(start.atStartOfDay()));
            preparedStmt.setTimestamp(2, Timestamp.valueOf(end.atStartOfDay()));

            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    appointments.add(new Appointment(rs.getString("APOINTMENTID"),
                            rs.getTimestamp("PLANTIMEEND").toLocalDateTime(),
                            rs.getTimestamp("PLANTIMESTART").toLocalDateTime(),
                            rs.getString("EMPLOYEEID"),
                            rs.getTimestamp("INDEEDTIMEEND").toLocalDateTime(),
                            rs.getTimestamp("INDEEDTIMESTART").toLocalDateTime()));
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR while getting all appointments in a timespan");
            e.printStackTrace();
        }
        return appointments;
    }
}
