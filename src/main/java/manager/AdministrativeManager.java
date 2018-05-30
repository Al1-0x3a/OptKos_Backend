package manager;

import data_loader.data_access_object.*;
import data_models.Customer;
import data_models.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class AdministrativeManager {

    public List<Employee> getAllEmployees() {
        return EmployeeDao.getAllEmployeesFromDb();
    }

    public boolean createEmployee(Employee employee) {
        return EmployeeDao.createNewEmployee(employee);
    }

    public boolean updateEmployee(Employee employee){
        return EmployeeDao.updateEmployee(employee);
    }

    public Customer getCustomerById(String uuid) {
        return CustomerDao.getCustomerById(uuid);
    }

    public List<Customer> getAllCustomers() {
        return CustomerDao.getAllCustomersFromDb();
    }
}
