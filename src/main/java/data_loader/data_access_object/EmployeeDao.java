package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Employee;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static PreparedStatement preparedStmt2;
    private static List<Employee> employeeList;


    public EmployeeDao(){
        employeeList = new ArrayList<Employee>();

    }

    public static List<Employee> getEmployeeList() {
        return employeeList;
    }

    public static List<Employee> getAllEmployeesFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PERSON p, OPTKOS.EMPLOYEE e WHERE p.PERSONID = e.PERSONID";
            ResultSet rs = stmt.executeQuery(query);

            employeeList = new ArrayList<>();
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


                employeeList.add(emil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public static void deleteEmployee(Employee employee) {
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMPLOYEE WHERE employeeId =?");
            preparedStmt.setString(1, employee.getEmployeeId().toString());

            preparedStmt2 = con.prepareStatement("DELETE FROM OPTKOS.PERSON WHERE PERSONID =?");
            preparedStmt2.setString(1, employee.getPersonId().toString());

            preparedStmt.execute();
            preparedStmt2.execute();

            AddressDao.deleteAddressByPersonId(employee.getPersonId());
            EmailDao.deleteEmailByPersonId(employee.getPersonId());

            employeeList.remove(employee.getEmployeeId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean createNewEmployee(Employee employee){
        try {
            stmt = con.createStatement();
            String query = "INSERT INTO PERSON( PERSONID, SURNAME, FIRSTNAME, SALUTATION, GENDER)" +
                    "VALUES ('" + employee.getPersonId().toString() + "', '" + employee.getLastname() + "', '" +
                    employee.getFirstname() + "', '" + employee.getSalutation() + "', '" +
                    employee.getGender() + "');" +

                    "INSERT into EMPLOYEE(EMPLOYEEID, PERSONID)" +
                    "VALUES ('" + employee.getPersonId().toString() + "', '" + employee.getPersonId().toString() + "');";

            stmt.execute(query);

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
            employeeList.add(employee);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Employee getEmployeeById(UUID empolyeeId){
        Employee emil = null;
        for(int i = 0; i< employeeList.size(); i++){
            if(employeeList != null && employeeList.get(i).getEmployeeId() == empolyeeId) {
                emil = employeeList.get(i);
                break;
            }
        }

        if( emil == null){
            emil = getEmployeeByIdFromDb(empolyeeId);
            if(emil !=null)
                employeeList.add(emil);
        }
        return emil;
    }




    public static Employee getEmployeeByIdFromDb(UUID employeeId){

        Employee emil = new Employee();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PERSON p, OPTKOS.EMPLOYEE e WHERE p.PERSONID = e.PERSONID AND e.EMPLOYEEID=" + employeeId.toString() + ";";
            ResultSet rs = stmt.executeQuery(query);

            // Person
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


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emil;
    }
}
