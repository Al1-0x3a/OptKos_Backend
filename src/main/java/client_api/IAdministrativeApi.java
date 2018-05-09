package client_api;

import data_models.Customer;
import data_models.Email;
import data_models.Employee;
import data_models.Phone;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IAdministrativeApi {
    @WebMethod
    Employee getEmployeeById(String uuid);
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
}
