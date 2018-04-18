package client_api;

import data_models.Employee;
import manager.AdministrativeManager;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "client_api.IEmployeeApi")
public class EmployeeApi implements IEmployeeApi {
    private AdministrativeManager administrativeManager;


    public EmployeeApi() {
        administrativeManager = new AdministrativeManager();
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
}
