package client_api;

import data_models.Employee;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@WebService
public interface IEmployeeApi {
    @WebMethod
    Employee getEmployee(UUID employeeId);
    @WebMethod
    List<Employee> getEmployees();
    @WebMethod
    boolean setEmployee(Employee employee);

}