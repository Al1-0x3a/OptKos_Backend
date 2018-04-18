package client_api;

import data_models.Employee;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface IEmployeeApi {
    @WebMethod
    List<Employee> getEmployees();
    @WebMethod
    boolean createEmployee(Employee employee);
    @WebMethod
    boolean updateEmployee(Employee employee);
}