package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeDao {
    private Connection con = SqlConnection.getConnection();
    private Statement stmt;
    public List<Employee> employees;


    public EmployeeDao(){
        employees = new ArrayList<Employee>();

    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Employee> getAllEmployeesFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PERSON p, OPTKOS.EMPLOYEE e WHERE p.PERSONID = e.PERSONID";
            ResultSet rs = stmt.executeQuery(query);

            outer:
            while (rs.next()){
                // Person
                Employee emil = new Employee();
                emil.setPersonId(UUID.fromString(rs.getString("PERSONID")));
                emil.setFirstname(rs.getString("FIRSTNAME"));
                emil.setLastname(rs.getString("LASTNAME"));
                emil.setTitle(rs.getString("TITEL"));
                emil.setSalutation(rs.getString("SALUTATION"));
                emil.setGender(rs.getString("GENDER").charAt(0));
                // Employee
                emil.setEmployeeId(UUID.fromString(rs.getString("EMPLOYEEID")));
                emil.setIsDeleted(rs.getString("ISDELETED").charAt(0));
                emil.setPositionId(UUID.fromString(rs.getString("POSITIONID")));
                // other objects
                emil.setPhoneList(PhoneDao.getListByPersonId(emil.getPersonId()));
                emil.setEmailList(EmailDao.getEmailListByPersonId(emil.getPersonId()));
                emil.setAddress(AddressDao.getAddressByPersonId(emil.getPersonId()));

                for (Employee employee : employees) {
                    if (employee.getEmployeeId() == emil.getEmployeeId())
                        continue outer;
                }
                employees.add(emil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void deleteEmployee(Employee employee) {
        try {
            stmt = con.createStatement();
            stmt.execute("DELETE FROM Employee WHERE employeeId = '" + employee.getEmployeeId() + "';");
            employees.remove(employee.getEmployeeId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean createNewEmployee(Employee employee){
        try {
            stmt = con.createStatement();
            String query = "INSERT INTO PERSON( PERSONID, SURNAME, FIRSTNAME, SALUTATION, GENDER)" +
                    "VALUES ('" + employee.getPersonId().toString() + "', '" + employee.getLastname() + "', '" +
                    employee.getFirstname() + "', '" + employee.getSalutation() + "', '" +
                    employee.getGender() + "');" +

                    "INSERT into EMPLOYEE(EMPLOYEEID, PERSONID, ISDELETED, POSITIONID)" +
                    "VALUES ('" + employee.getEmployeeId().toString() + "', '" + employee.getPersonId().toString() +
                    "', '" + employee.getIsDeleted() + "', " + employee.getPositionId().toString() + ");";

            stmt.execute(query);

            AddressDao.createNewAddress(employee.getAddress());
            PhoneDao.createPhone(employee.getPhoneList().get(0));
            EmailDao.createEmail(employee.getEmailList().get(0));
            employees.add(employee);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
