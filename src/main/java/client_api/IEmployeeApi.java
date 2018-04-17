package client_api;

import data_models.Employee;

import java.util.List;
import java.util.UUID;

public interface IEmployeeApi {

    public Employee getEmployee(UUID employeeId);

    public List<Employee> getEmployees();

    public Boolean setEmployee();

}