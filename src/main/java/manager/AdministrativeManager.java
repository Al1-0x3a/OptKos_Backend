package manager;

import data_models.Employee;

import java.util.ArrayList;
import java.util.UUID;

public class AdministrativeManager {
    private ArrayList<Employee> tmpList;

    public AdministrativeManager() {
        tmpList = new ArrayList<>();
        Employee employee = new Employee();
        employee.employeeId = UUID.randomUUID();
        employee.setFirstname("Adolf");
        employee.setLastname("Witler");
        tmpList.add(employee);
    }

    public ArrayList<Employee> getAllEmployies() {
        return tmpList;
    }
}
