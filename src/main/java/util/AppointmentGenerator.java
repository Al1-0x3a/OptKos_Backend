package util;

import data_loader.SqlConnection;
import data_loader.data_access_object.CustomerDao;
import data_loader.data_access_object.EmployeeDao;
import data_loader.data_access_object.ServiceDao;
import data_models.*;
import manager.AppointmentManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class AppointmentGenerator {
    private static final Connection con = SqlConnection.getConnection();

    private static final String APPOINTMENT_TYPE = "26f35e62-ba3d-4210-b5db-633668509e02";
    private static final long MIN_DATE = LocalDate.of(2016, 1, 1).toEpochDay();
    private static final long MAX_DATE = LocalDate.of(2018, 12, 31).toEpochDay();

    private static final int WORKING_DAY_START = 8;
    private static final int WORKING_DAY_END = 20;
    private static final int OFFSET = 2;

    private static final int AMOUNT = 1000;

    public static void main(String[] args) {
        AppointmentManager manager = new AppointmentManager();

        List<Employee> employees = EmployeeDao.getAllEmployeesFromDb();
        List<Customer> customers = CustomerDao.getAllCustomersFromDb();
        List<Service> services = ServiceDao.getAllServicesFromDb();

        long start = System.currentTimeMillis();

        int counter = 0;
        while (counter < AMOUNT) {
            Employee employee = employees.get(random().nextInt(employees.size()));
            Customer customer = customers.get(random().nextInt(customers.size()));
            Service service = services.get(random().nextInt(services.size()));
            String appointmentId = UUID.randomUUID().toString();

            LocalDate day = LocalDate.ofEpochDay(random().nextLong(MIN_DATE, MAX_DATE));
            int hourStart = random().nextInt(WORKING_DAY_START, WORKING_DAY_END - OFFSET);
            int minuteStart = random().nextInt(60);
            LocalTime startTime = LocalTime.of(hourStart, minuteStart, 0);

            long minuteOffset = 0;
            minuteOffset += service.getDurationAverage().toMinutes();

            LocalTime endTime;
            try {
                endTime = startTime.plus(Duration.ofMinutes(minuteOffset));
            } catch (Exception e) {
                System.err.println("EndTime too large");
                continue;
            }

            Appointment appointment = new Appointment(appointmentId, LocalDateTime.of(day, endTime),
                    LocalDateTime.of(day, startTime), employee.getEmployeeId());
            appointment.setStartTimeActual(appointment.getStartTime());
            appointment.setEndTimeActual(appointment.getEndTime());
            appointment.setCustomer(customer);
            appointment.setService(service);

            if (manager.isFree(appointment, employee)) {
                try {
                    try (PreparedStatement appointmentStatement = con.prepareStatement("INSERT INTO OPTKOS.APOINTMENT " +
                            "(APOINTMENTID, PLANTIMESTART, PLANTIMEEND, INDEEDTIMESTART, INDEEDTIMEEND, EMPLOYEEID, " +
                            "CUSTOMERID, APOINTMENTTYPEID, SERVICEID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                        appointmentStatement.setString(1, appointment.getAppointmentId());
                        appointmentStatement.setTimestamp(2, Timestamp.valueOf(appointment.getStartTime()));
                        appointmentStatement.setTimestamp(3, Timestamp.valueOf(appointment.getEndTime()));
                        appointmentStatement.setTimestamp(4, Timestamp.valueOf(appointment.getStartTime()));
                        appointmentStatement.setTimestamp(5, Timestamp.valueOf(appointment.getEndTime()));
                        appointmentStatement.setString(6, appointment.getEmployeeid());
                        appointmentStatement.setString(7, appointment.getCustomer().getCustomerId());
                        appointmentStatement.setString(8, APPOINTMENT_TYPE);
                        appointmentStatement.setString(9, appointment.getService().getServiceId());
                        appointmentStatement.execute();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
                }
                System.out.println("Generated appointment " + counter);
                counter++;
            } else {
                System.out.println("Collision");
            }
        }

        long end = System.currentTimeMillis();
        System.out.printf("Generated %d appointments in %d ms%n", counter, (end - start));
    }

    private static ThreadLocalRandom random() {
        return ThreadLocalRandom.current();
    }
}
