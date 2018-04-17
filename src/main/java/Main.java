import data_loader.SqlConnection;
import data_loader.data_access_object.EmployeeDao;
import data_models.Employee;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main (String[] args){
//        Connection con = SqlConnection.getConnection();
        EmployeeDao e = new EmployeeDao();
        List<Employee> allEmployees = e.getAllEmployeesFromDb();
        System.out.println(allEmployees.get(0));
        System.out.println("Hallo Welt");
    }
}
