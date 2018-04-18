import data_loader.SqlConnection;
import data_loader.data_access_object.EmployeeDao;
import data_models.Employee;

import java.sql.Connection;
import java.util.List;

import client_api.AppointmentApi;
import client_api.EmployeeApi;
import manager.AdministrativeManager;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main (String[] args){
        System.out.println("Launching employee endpoint");
        Endpoint.publish("http://localhost:1337/EmployeeApi", new EmployeeApi());
        System.out.println("Launching appointment endpoint");
        Endpoint.publish("http://localhost:1338/AppointmentApi", new AppointmentApi());

    }
}
