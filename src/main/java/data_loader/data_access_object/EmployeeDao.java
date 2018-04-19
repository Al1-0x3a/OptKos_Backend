package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Employee;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeDao {
    private Connection con = SqlConnection.getConnection();
    private Statement stmt;
    private PreparedStatement preparedStmt;
    private PreparedStatement preparedStmt2;
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

            employees = new ArrayList<>();
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


                employees.add(emil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void deleteEmployee(Employee employee) {
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMPLOYEE WHERE employeeId =?");
            preparedStmt.setString(1, employee.getEmployeeId().toString());

            preparedStmt2 = con.prepareStatement("DELETE FROM OPTKOS.PERSON WHERE PERSONID =?");
            preparedStmt2.setString(1, employee.getPersonId().toString());

            preparedStmt.execute();
            preparedStmt2.execute();

            AddressDao.deleteAddressByPersonId(employee.getPersonId());
            EmailDao.deleteEmailByPersonId(employee.getPersonId());

            employees.remove(employee.getEmployeeId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean createNewEmployee(Employee employee){
        try {
<<<<<<< HEAD
=======
            stmt = con.createStatement();
            String query = "INSERT INTO PERSON( PERSONID, SURNAME, FIRSTNAME, SALUTATION, GENDER)" +
                    "VALUES ('" + employee.getPersonId().toString() + "', '" + employee.getLastname() + "', '" +
                    employee.getFirstname() + "', '" + employee.getSalutation() + "', '" +
                    employee.getGender() + "');" +

                    "INSERT into EMPLOYEE(EMPLOYEEID, PERSONID)" +
                    "VALUES ('" + employee.getPersonId().toString() + "', '" + employee.getPersonId().toString() + "');";

            stmt.execute(query);
>>>>>>> 0178d52... Removed Null Pointer from sql statement

            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.PERSON (PERSONID, LASTNAME, FIRSTNAME) VALUES(?,?,?)");
            preparedStmt2 = con.prepareStatement("INSERT INTO OPTKOS.EMPLOYEE(EMPLOYEEID,PERSONID) VALUES(?,?)");
            preparedStmt.setString(1 , employee.getPersonId().toString());
            preparedStmt.setString(2, employee.getLastname());
            preparedStmt.setString(3,  employee.getFirstname() );
            preparedStmt2.setString(1, employee.getPersonId().toString());
            preparedStmt2.setString(2, employee.getPersonId().toString());
            preparedStmt.execute();
            preparedStmt2.execute();
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
