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

    public List<Employee> getAllEmployees(){

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
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
        employees.remove(employee.getEmployeeId());
        try {
            stmt = con.createStatement();
            stmt.execute("DELETE FROM Employee WHERE employeeId = '" + employee.getEmployeeId() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
