package client_api;

import data_models.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAdministrativeApi {
    @WebMethod
    List<Employee> getEmployees();
    @WebMethod
    boolean createEmployee(Employee employee);
    @WebMethod
    boolean updateEmployee(Employee employee);
    @WebMethod
    Employee getNewEmployee();
    @WebMethod
    Customer getCustomer(String customerId);
    @WebMethod
    List<Customer> getCustomers();
    @WebMethod
    boolean createCustomer(Customer customer);
    @WebMethod
    boolean updateCustomer(Customer customer);
    @WebMethod
    Customer getNewCustomer();
    @WebMethod
    Phone getNewPhone(String personId);
    @WebMethod
    boolean createPhone(Phone phone);
    @WebMethod
    boolean updatePhone(Phone phone);
    @WebMethod
    boolean deletePhone(Phone phone);
    @WebMethod
    Email getNewEmail(String personId);
    @WebMethod
    boolean createEmail(Email email);
    @WebMethod
    boolean updateEmail(Email email);
    @WebMethod
    boolean deleteEmail(Email email);
    @WebMethod
    boolean deleteEmployee(Employee employee);
    @WebMethod
    List<Service> getServices();
    @WebMethod
    Service getServiceById(String uuid);
    @WebMethod
    boolean createService(Service service);
    @WebMethod
    boolean updateService(Service service);
    @WebMethod
    boolean deleteService(Service service);
    @WebMethod
    Service getNewService();
    @WebMethod
    CustomerCategory getCustomerCategoryById(String uuid);
    @WebMethod
    List<CustomerCategory> getAllCustomerCategories();
    @WebMethod
    CustomerCategory getNewCustomerCategory();
    @WebMethod
    boolean createCustomerCategory(CustomerCategory customerCategory);
    @WebMethod
    boolean updateCustomerCategory(CustomerCategory customerCategory);
    @WebMethod
    boolean deleteCustomerCategory(CustomerCategory customerCategory);
    @WebMethod
    List<Position> getAllPositionFromDb();
    @WebMethod
    boolean createServiceDuration(String employeeId, String serviceId, int duration);
    @WebMethod
    List<ServiceEmployeeDuration> getServiceDuration(String employeeId, String serviceId);
    @WebMethod
    boolean deleteServiceDuration(String serviceDurationId);
    @WebMethod
    Customer getCustomerByPhoneNumber(String phoneNumber);
    @WebMethod
    boolean login(String username, String password);
    @WebMethod
    void registerUser(String username, String password);
    Colour getNewColour();
    @WebMethod
    List<Colour> getColours();
    @WebMethod
    void createColour(Colour colour);
    @WebMethod
    void updateColour(Colour colour);
    @WebMethod
    void deleteColour(Colour colour);
    @WebMethod
    ColourMixture getNewColourMixture();
    @WebMethod
    List<ColourMixture> getAllColourMixtures();
    @WebMethod
    void createColourMixture(ColourMixture colourMixture);
    @WebMethod
    void updateColourMixture(ColourMixture colourMixture);
    @WebMethod
    void deleteColourMixture(ColourMixture colourMixture);
    @WebMethod
    CustomerColour getNewCustomerColour();
    @WebMethod
    List<CustomerColour> getAllCustomerColours();
    @WebMethod
    void createCustomerColour(CustomerColour customerColour);
    @WebMethod
    void updateCustomerColour(CustomerColour customerColour);
    @WebMethod
    void deleteCustomerColour(CustomerColour customerColour);
    @WebMethod
    void addNewMixtures(List<ColourMixture> cmList);
}
