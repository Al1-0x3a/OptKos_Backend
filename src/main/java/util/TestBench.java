package util;

import data_loader.data_access_object.EmployeeDao;
import data_loader.data_access_object.ServiceDao;
import data_models.Employee;
import data_models.Service;
import manager.AppointmentManager;

public class TestBench {
    public static void main(String[] args) {
        AppointmentManager manager = new AppointmentManager();
        Employee employee = EmployeeDao.getAllEmployeesFromDb().stream().findFirst().get();
        Service service = ServiceDao.getAllServicesFromDb().stream().findFirst().get();
        manager.calculateServiceDuration(employee, service);
    }
}
