package client_api;

import data_models.Customer;
import data_models.Employee;
import manager.AdministrativeManager;

import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@WebService(endpointInterface = "client_api.IAdministrativeApi")
public class AdministrativeApi implements IAdministrativeApi {
    private AdministrativeManager administrativeManager;

    public AdministrativeApi() {
        administrativeManager = new AdministrativeManager();
    }

    @Override
    public Employee getEmployeeById(UUID uuid) {
        return administrativeManager.getEmployeeById(uuid);
    }

    @Override
    public List<Employee> getEmployees() {
        return administrativeManager.getAllEmployees();
    }

    @Override
    public boolean createEmployee(Employee employee) {
        return administrativeManager.createEmployee(employee);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    @Override
    public Customer getCustomer(UUID customerId) {
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        return null;
    }

    @Override
    public boolean createCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }
}
