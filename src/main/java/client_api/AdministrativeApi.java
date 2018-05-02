package client_api;

import data_loader.data_access_object.PhoneDao;
import data_models.Customer;
import data_models.Employee;
import data_models.Phone;
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

    // save a new employee
    @Override
    public boolean createEmployee(Employee employee) {
        return administrativeManager.createEmployee(employee);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    // give employee object to frontend
    @Override
    public Employee getNewEmployee() {
        return new Employee();
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

    @Override
    public Phone getNewPhone(UUID personId) {
        return new Phone(personId);
    }

    @Override
    public boolean createPhone(Phone phone) {
        return PhoneDao.createPhone(phone);
    }

    @Override
    public boolean updatePhone(Phone phone) {
        return PhoneDao.updatePhone(phone);
    }
    @Override
    public boolean deletePhone(Phone phone){
        return PhoneDao.deletePhoneByPhoneId(phone.getPhoneId());
    }
}
