package client_api;

import data_models.Employee;
import manager.AdministrativeManager;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.UUID;

@WebService(endpointInterface = "client_api.IEmployeeApi")
public class EmployeeApi implements IEmployeeApi {
    private AdministrativeManager administrativeManager;


    public EmployeeApi() {
        administrativeManager = new AdministrativeManager();
    }

    @Override
    public Employee getEmployee(UUID employeeId) {
        return null;
    }

    @Override
    public ArrayList<Employee> getEmployees() {
        return administrativeManager.getAllEmployies();
    }

    @Override
    public boolean setEmployee() {
        return false;
    }
}
