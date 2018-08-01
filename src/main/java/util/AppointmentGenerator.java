/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package util;

import data_loader.SqlConnection;
import data_loader.data_access_object.CustomerDao;
import data_loader.data_access_object.EmployeeDao;
import data_loader.data_access_object.ServiceDao;
import data_models.*;
import manager.AppointmentManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class AppointmentGenerator {
    private static final Connection con = SqlConnection.getConnection();

    private static final String APPOINTMENT_TYPE = "26f35e62-ba3d-4210-b5db-633668509e02";
    private static final long MIN_DATE = LocalDate.of(2016, 1, 1).toEpochDay();
    private static final long MAX_DATE = LocalDate.of(2018, 12, 31).toEpochDay();

    private static final int WORKING_DAY_START = 8;
    private static final int WORKING_DAY_END = 19;
    private static final int OFFSET = 2;

    private static final LocalDateTime CURRENT_DATE_TIME = LocalDateTime.parse("2018-06-26T07:00:00");
    private static final int AMOUNT = 5000;

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

            LocalTime endTime = startTime.plus(Duration.ofMinutes(minuteOffset));
            if (endTime.isBefore(startTime)) continue;

            Appointment appointment = new Appointment(appointmentId, LocalDateTime.of(day, endTime),
                    LocalDateTime.of(day, startTime), employee.getEmployeeId());
            appointment.setCustomer(customer);
            appointment.setService(service);

            if (appointment.getEndTime().isBefore(CURRENT_DATE_TIME)) {
                int median = (int) appointment.getService().getDurationPlanned().toMinutes();
                int standardDeviation = median / 4;
                double val = random().nextGaussian() * standardDeviation + median;
                long actualEnd = Math.round(val);
                if (actualEnd < 5) actualEnd = 5;
                appointment.setStartTimeActual(appointment.getStartTime());
                appointment.setEndTimeActual(appointment.getStartTime().plus(Duration.ofMinutes(actualEnd)));
            }

            if (manager.isFree(appointment, employee)) {
                try {
                    try (PreparedStatement appointmentStatement = con.prepareStatement("INSERT INTO OPTKOS.APOINTMENT " +
                            "(APOINTMENTID, PLANTIMESTART, PLANTIMEEND, INDEEDTIMESTART, INDEEDTIMEEND, EMPLOYEEID, " +
                            "CUSTOMERID, APOINTMENTTYPEID, SERVICEID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                        appointmentStatement.setString(1, appointment.getAppointmentId());
                        appointmentStatement.setTimestamp(2, Timestamp.valueOf(appointment.getStartTime()));
                        appointmentStatement.setTimestamp(3, Timestamp.valueOf(appointment.getEndTime()));
                        appointmentStatement.setTimestamp(4, appointment.getStartTimeActual() == null ? null : Timestamp.valueOf(appointment.getStartTimeActual()));
                        appointmentStatement.setTimestamp(5, appointment.getEndTimeActual() == null ? null : Timestamp.valueOf(appointment.getEndTimeActual()));
                        appointmentStatement.setString(6, appointment.getEmployeeid());
                        appointmentStatement.setString(7, appointment.getCustomer().getCustomerId());
                        appointmentStatement.setString(8, APPOINTMENT_TYPE);
                        appointmentStatement.setString(9, appointment.getService().getServiceId());
                        appointmentStatement.execute();
                    }

//                    BufferedWriter writer = new BufferedWriter(new FileWriter("appointment_generator.txt", true));
//                    writer.write(buildQueryString(appointment) + "\n");
//                    writer.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    break;
//                } catch (IOException e) {
//                    e.printStackTrace();
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

    private static String buildQueryString(Appointment appointment) {
        String[] values = {
                appointment.getAppointmentId(),
                Timestamp.valueOf(appointment.getStartTime()).toString(),
                Timestamp.valueOf(appointment.getEndTime()).toString(),
                appointment.getStartTimeActual() == null ? null : Timestamp.valueOf(appointment.getStartTimeActual()).toString(),
                appointment.getEndTimeActual() == null ? null : Timestamp.valueOf(appointment.getEndTimeActual()).toString(),
                appointment.getEmployeeid(),
                appointment.getCustomer().getCustomerId(),
                APPOINTMENT_TYPE,
                appointment.getService().getServiceId()
        };
        String query ="INSERT INTO OPTKOS.APOINTMENT" +
                " (APOINTMENTID, PLANTIMESTART, PLANTIMEEND, INDEEDTIMESTART, INDEEDTIMEEND, EMPLOYEEID," +
                " CUSTOMERID, APOINTMENTTYPEID, SERVICEID) VALUES ('?', '?', '?', '?', '?', '?', '?', '?', '?')";
        for (String value : values) {
            query = query.replaceFirst("\\?", Optional.ofNullable(value).orElse("wow"));
        }
        query = query.replace("'wow'", "NULL");
        return query;
    }
}
