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
    public Employee getEmployee(int employeeId) {
        return null;
    }

    @Override
    public List<Employee> getEmployees() {
        return administrativeManager.getAllEmployees();
    }

    @Override
    public boolean setEmployee() {
        return false;
    }
}
