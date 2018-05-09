package manager;

import data_loader.data_access_object.EmployeeDao;
import data_models.Employee;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdministrativeManager {
    private EmployeeDao employeeDao;

    public AdministrativeManager() {
        employeeDao = new EmployeeDao();
    }

    public Employee getEmployeeById(String uuid) {
        List<Employee> tmpList = employeeDao.getAllEmployeesFromDb().stream().
                filter(employee -> employee.getEmployeeId().equals(uuid)).collect(Collectors.toList());
        if (tmpList.isEmpty()) {
            System.err.println("No employee with UUID " + uuid);
            return null;
        }
        if (tmpList.size() > 1) {
            System.err.println("Two employees with same UUID. You probably fucked something up.");
            return null;
        }
        return tmpList.get(0);
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployeesFromDb();
    }

    public boolean createEmployee(Employee employee) {
        return employeeDao.createNewEmployee(employee);
    }

    public boolean updateEmployee(Employee employee){
        return EmployeeDao.updateEmployee(employee);
    }
}
