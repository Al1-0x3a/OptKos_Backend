package client_api;

import data_loader.data_access_object.*;
import data_models.*;
import manager.AdministrativeManager;
import manager.PhoneManager;

import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ValidExternallyBoundObject")
@WebService(endpointInterface = "client_api.IAdministrativeApi")
public class AdministrativeApi implements IAdministrativeApi {
    private final AdministrativeManager administrativeManager;
    private final PhoneManager phoneManager;

    public AdministrativeApi() {
        administrativeManager = new AdministrativeManager();
        phoneManager = new PhoneManager();
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
        return CustomerDao.updateCustomer(customer);
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

    @Override
    public boolean createServiceDuration(String employeeId, String serviceId, int duration) {
        return ServiceDurationDao.createServiceDuration(employeeId, serviceId, duration);
    }

    @Override
    public List<ServiceEmployeeDuration> getServiceDuration(String employeeId, String serviceId) {
        return ServiceDurationDao.getServiceDuration(employeeId, serviceId);
    }

    @Override
    public boolean deleteServiceDuration(String serviceDurationId) {
        return ServiceDurationDao.deleteServiceDuration(serviceDurationId);
    }

    @Override
    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        return phoneManager.getCustomerByPhoneNumber(phoneNumber);
    }

    @Override
    public Colour getNewColour() {
        return new Colour();
    }

    @Override
    public List<Colour> getColours() {
        return ColourDao.getAllColoursFromDb();
    }

    @Override
    public void createColour(Colour colour) {
        ColourDao.createColour(colour);
    }

    @Override
    public void updateColour(Colour colour) {
        ColourDao.changeColourByColourId(colour);
    }

    @Override
    public void deleteColour(Colour colour) {
        ColourDao.deleteColourByColourId(colour.getColourId());
    }

    @Override
    public ColourMixture getNewColourMixture() {
        return new ColourMixture();
    }

    @Override
    public List<ColourMixture> getAllColourMixtures() {
        return ColourMixtureDao.getAllColourMixturesFromDb();
    }

    @Override
    public void createColourMixture(ColourMixture colourMixture) {
        ColourMixtureDao.createColourMixture(colourMixture);
    }

    @Override
    public void updateColourMixture(ColourMixture colourMixture) {
        ColourMixtureDao.changeCustomerColourByCustomerId(colourMixture);
    }

    @Override
    public void deleteColourMixture(ColourMixture colourMixture) {
        ColourMixtureDao.deleteColourMixtureByColourMixtureId(colourMixture.getColourMixtureId());
    }

    @Override
    public CustomerColour getNewCustomerColour() {
        return new CustomerColour();
    }

    @Override
    public List<CustomerColour> getAllCustomerColours() {
        return CustomerColourDao.getAllCustomerColoursFromDb();
    }

    @Override
    public void createCustomerColour(CustomerColour customerColour) {
        CustomerColourDao.createCustomerColour(customerColour);
    }

    @Override
    public void updateCustomerColour(CustomerColour customerColour) {
        CustomerColourDao.changeCustomerColourByCustomerId(customerColour);
    }

    @Override
    public void deleteCustomerColour(CustomerColour customerColour) {
        CustomerColourDao.deleteCustomerColourByCustomerId(customerColour.getCustomerId());
    }

    @Override
    public void addNewMixtures(List<ColourMixture> cmList) {
        ColourMixtureDao.addNewMixtures(cmList);
    }
}
