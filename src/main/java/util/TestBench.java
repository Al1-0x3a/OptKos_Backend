package util;

import data_loader.data_access_object.EmployeeDao;
import data_models.Appointment;
import data_models.Employee;
import manager.AppointmentManagerAufCrack;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TestBench {
    public static void main(String[] args) {
        AppointmentManagerAufCrack crack = new AppointmentManagerAufCrack();
        List<Employee> employees = EmployeeDao.getAllEmployeesFromDb();
        Employee employee = employees.get(0);
        Appointment app = new Appointment(UUID.randomUUID().toString(), LocalDateTime.now().plus(Duration.ofMinutes(30)), LocalDateTime.now(), employee.getEmployeeId());
        System.out.println(crack.isFree(app, employee));
    }
}
