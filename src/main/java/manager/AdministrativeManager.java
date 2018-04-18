package manager;

import data_loader.data_access_object.EmployeeDao;
import data_models.Employee;

import java.util.List;

public class AdministrativeManager {
    private EmployeeDao employeeDao;

    public AdministrativeManager() {
        employeeDao = new EmployeeDao();
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployeesFromDb();
    }

    public boolean setEmployee(Employee employee) {
        return employeeDao.createNewEmployee(employee);
    }
}
