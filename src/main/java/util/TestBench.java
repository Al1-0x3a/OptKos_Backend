package util;

import data_loader.data_access_object.EmployeeDao;
import data_loader.data_access_object.ServiceDao;
import data_models.AppointmentSuggestion;
import data_models.Employee;
import data_models.Service;
import manager.AppointmentManager;

import java.time.LocalDateTime;
import java.util.List;

public class TestBench {
    public static void main(String[] args) {
        AppointmentManager crack = new AppointmentManager();
        List<Employee> employees = EmployeeDao.getAllEmployeesFromDb();
        List<Service> services = ServiceDao.getAllServicesFromDb();

        Employee yes = employees.stream().filter(a -> a.getFirstname().equals("Klaudia")).findFirst().get();
        Service fuck = services.stream().filter(s -> s.getDurationAverage().toMinutes() == 45).findFirst().get();

        LocalDateTime wow = LocalDateTime.parse("2018-06-14T09:00:00");
        AppointmentSuggestion wowFuckYes = new AppointmentSuggestion(wow, yes, fuck);
        System.out.println("BENIS");

        List<AppointmentSuggestion> result = crack.findSuggestions(AppointmentSuggestion.Strategy.FIRST_SLOT, wowFuckYes);
        System.out.println("Amazing");
    }
}
