package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

            while (rs.next()){
                // Person
                Employee emil = new Employee();
                emil.setPersonId(rs.getInt("PERSONID"));
                emil.setFirstname(rs.getString("FIRSTNAME"));
                emil.setSurname(rs.getString("SURNAME"));
                emil.setTitle(rs.getString("TITEL"));
                emil.setSalutation(rs.getString("SALUTATION"));
                emil.setGender(rs.getString("GENDER").charAt(0));
                // Employee
                emil.setEmployeeId(rs.getInt("EMPLOYEEID"));
                emil.setIsDeleted(rs.getString("ISDELETED").charAt(0));
                emil.setPositionId(rs.getInt("POSITIONID"));
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

    // TODO: employee has to get personid from another query of just created person
    public void createNewEmployee(Employee employee){
        try {
            stmt = con.createStatement();
            String query = "INSERT INTO PERSON( SURNAME, FIRSTNAME, SALUTATION, GENDER)" +
                    "VALUES ('" + employee.getSurname() + "', '" + employee.getFirstname() + "', '" +
                    employee.getSalutation() + "', '" + employee.getGender() + "');" +

                    "INSERT into EMPLOYEE(PERSONID, ISDELETED, POSITIONID)" +
                    "VALUES (1, '" + employee.getIsDeleted() + "', " + employee.getPositionId() + ");";

            employees.add(employee);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
