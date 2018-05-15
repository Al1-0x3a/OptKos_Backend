package manager;

import data_loader.data_access_object.CustomerDao;
import data_loader.data_access_object.EmployeeDao;
import data_models.Customer;
import data_models.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class AdministrativeManager {

    public Employee getEmployeeById(String uuid) {
        List<Employee> tmpList = EmployeeDao.getAllEmployeesFromDb().stream().
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
        return EmployeeDao.getAllEmployeesFromDb();
    }

    public boolean createEmployee(Employee employee) {
        return EmployeeDao.createNewEmployee(employee);
    }

    public boolean updateEmployee(Employee employee){
        return EmployeeDao.updateEmployee(employee);
    }

    public Customer getCustomerById(String uuid) { return CustomerDao.getCustomerByIdFromDb(uuid); }

    public List<Customer> getAllCustomers() { return CustomerDao.getAllCustomFromDb();
    }
}
