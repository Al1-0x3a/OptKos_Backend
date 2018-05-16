package client_api;

import data_loader.data_access_object.*;
import data_models.*;
import manager.AdministrativeManager;

import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAdministrativeApi")
public class AdministrativeApi implements IAdministrativeApi {
    private final AdministrativeManager administrativeManager;

    public AdministrativeApi() {
        administrativeManager = new AdministrativeManager();
    }

    @Override
    public Employee getEmployeeById(String uuid) {
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
        return administrativeManager.updateEmployee(employee);
    }

    // give employee object to frontend
    @Override
    public Employee getNewEmployee() {
        return new Employee(UUID.randomUUID().toString());
    }

    @Override
    public Customer getCustomer(String customerId) {
        return administrativeManager.getCustomerById(customerId);
    }

    @Override
    public List<Customer> getCustomers() {
        return administrativeManager.getAllCustomers();
    }

    @Override
    public boolean createCustomer(Customer customer) {
        return CustomerDao.createNewCustomer(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer getNewCustomer() {
        return new Customer(UUID.randomUUID().toString());
    }

    @Override
    public Phone getNewPhone(String personId) {
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
    public boolean deletePhone(Phone phone) {
        return PhoneDao.deletePhoneByPhoneId(phone.getPhoneId());
    }

    @Override
    public Email getNewEmail(String personId) {
        return new Email(personId);
    }

    @Override
    public boolean createEmail(Email email) {
        return EmailDao.createEmail(email);
    }

    @Override
    public boolean updateEmail(Email email) {
        return EmailDao.updateEmail(email);
    }

    @Override
    public boolean deleteEmail(Email email) {
        return EmailDao.deleteEmailByEmailId(email.getEmailId());
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        return EmployeeDao.deleteEmployee(employee);
    }

    @Override
    public List<Service> getServices() {
        return ServiceDao.getAllServicesFromDb();
    }

    @Override
    public Service getServiceById(String uuid) {
        return ServiceDao.getServiceById(uuid);
    }

    @Override
    public boolean createService(Service service) {
        return ServiceDao.createService(service);
    }

    @Override
    public boolean updateService(Service service) {
        return ServiceDao.updateService(service);
    }

    @Override
    public boolean deleteService(Service service) {
        return ServiceDao.deleteServiceByServiceId(service.getServiceId());
    }

    @Override
    public Service getNewService() {
        return new Service();
    }

    @Override
    public List<CustomerCategory> getAllCustomerCategories() {
        return CustomerCategoryDao.getAllCustomerCategoriesFromDb();
    }

    @Override
    public CustomerCategory getCustomerCategoryById(String uuid) {
        return CustomerCategoryDao.getCustomerCategoryByIdFromDb(uuid);
    }

    @Override
    public CustomerCategory getNewCustomerCategory() {
        return new CustomerCategory();
    }

    @Override
    public boolean createCustomerCategory(CustomerCategory customerCategory) {
        return CustomerCategoryDao.createCustomerCategory(customerCategory);
    }

    @Override
    public boolean updateCustomerCategory(CustomerCategory customerCategory) {
        return CustomerCategoryDao.updateCustomerCategory(customerCategory);
    }

    @Override
    public boolean deleteCustomerCategory(CustomerCategory customerCategory) {
        return CustomerCategoryDao.deleteCustomerCategory(customerCategory);
    }

    @Override
    public List<Position> getAllPositionFromDb() {
        return PositionDao.getAllPositionsFromDb();
    }
}
