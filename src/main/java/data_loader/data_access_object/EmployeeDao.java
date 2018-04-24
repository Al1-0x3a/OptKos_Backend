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
        employeeList = new ArrayList<>();


    }

    public static List<Employee> getEmployeeList() {
        if( employeeList == null){
            employeeList = getAllEmployeesFromDb();
        }
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
                Employee employee = new Employee();
                employee.setPersonId(UUID.fromString(rs.getString("PERSONID")));
                employee.setFirstname(rs.getString("FIRSTNAME"));
                employee.setLastname(rs.getString("LASTNAME"));
                employee.setTitle(rs.getString("TITEL"));
                employee.setSalutation(rs.getString("SALUTATION"));
                employee.setGender(rs.getString("GENDER").charAt(0));
                // Employee
                employee.setEmployeeId(UUID.fromString(rs.getString("EMPLOYEEID")));
                employee.setIsDeleted(rs.getString("ISDELETED").charAt(0));
                employee.setPositionId(UUID.fromString(rs.getString("POSITIONID")));
                // other objects
                employee.setPhoneList(PhoneDao.getListByPersonId(employee.getPersonId()));
                employee.setEmailList(EmailDao.getEmailListByPersonId(employee.getPersonId()));
                employee.setAddress(AddressDao.getAddressByPersonId(employee.getPersonId()));
                employee.setWorkingWeek(WorkingWeekDao.getWorkingWeek(employee.getEmployeeId()));
                employee.setPositionId(UUID.fromString(rs.getString("POSITIONID")));


                employeeList.add(employee);
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
            PhoneDao.deletePhoneByPersonId(employee.getPersonId());
            WorkingWeekDao.deleteWorkingDaysByEmployeeId(employee.getEmployeeId());

            employeeList.removeIf(o -> o.getEmployeeId() == employee.getEmployeeId());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean createNewEmployee(Employee employee){
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.PERSON (PERSONID, LASTNAME, FIRSTNAME, TITLE, SALUTATION, GENDER) VALUES(?,?,?,?,?,?)");


            preparedStmt.setString(1 , employee.getPersonId().toString());
            preparedStmt.setString(2, employee.getLastname());
            preparedStmt.setString(3,  employee.getFirstname());
            preparedStmt.setString(4,  employee.getTitle().toString());
            preparedStmt.setString(5,  employee.getSalutation());
            preparedStmt.setString(6,  String.valueOf(employee.getGender()));

            preparedStmt2 = con.prepareStatement("INSERT INTO OPTKOS.EMPLOYEE(EMPLOYEEID,PERSONID,POSITIONID) VALUES(?,?,?)");

            preparedStmt2.setString(1, employee.getEmployeeId().toString());
            preparedStmt2.setString(2, employee.getPersonId().toString());
            preparedStmt2.setString(3, employee.getPositionId().toString());

            preparedStmt.execute();
            preparedStmt2.execute();

            AddressDao.createNewAddress(employee.getAddress());
            PhoneDao.createPhone(employee.getPhoneList().get(0));
            EmailDao.createEmail(employee.getEmailList().get(0));
            WorkingWeekDao.setWorkingWeek(employee.getWorkingWeek(), employee.getEmployeeId());

            employeeList.add(employee);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Employee getEmployeeById(UUID empolyeeId){
        Employee employee = null;
        for(int i = 0; i< employeeList.size(); i++){
            if(employeeList != null && employeeList.get(i).getEmployeeId() == empolyeeId) {
                employee = employeeList.get(i);
                break;
            }
        }

        if( employee == null){
            employee = getEmployeeByIdFromDb(empolyeeId);
            if(employee !=null)
                employeeList.add(employee);
        }
        return employee;
    }




    public static Employee getEmployeeByIdFromDb(UUID employeeId){

        Employee employee = new Employee();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PERSON p, OPTKOS.EMPLOYEE e WHERE p.PERSONID = e.PERSONID AND " +
                    "e.EMPLOYEEID=" + employeeId.toString() + ";";
            ResultSet rs = stmt.executeQuery(query);

            // Person
            employee.setPersonId(UUID.fromString(rs.getString("PERSONID")));
            employee.setFirstname(rs.getString("FIRSTNAME"));
            employee.setLastname(rs.getString("LASTNAME"));
            employee.setTitle(rs.getString("TITEL"));
            employee.setSalutation(rs.getString("SALUTATION"));
            employee.setGender(rs.getString("GENDER").charAt(0));
            // Employee
            employee.setEmployeeId(UUID.fromString(rs.getString("EMPLOYEEID")));
            employee.setIsDeleted(rs.getString("ISDELETED").charAt(0));
            employee.setPositionId(UUID.fromString(rs.getString("POSITIONID")));
            // other objects
            employee.setPhoneList(PhoneDao.getListByPersonId(employee.getPersonId()));
            employee.setEmailList(EmailDao.getEmailListByPersonId(employee.getPersonId()));
            employee.setAddress(AddressDao.getAddressByPersonId(employee.getPersonId()));
            employee.setWorkingWeek(WorkingWeekDao.getWorkingWeek(employee.getEmployeeId()));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}


// TODO:java class properties an methode übergeben um spezielle sachen zu suchen/ändern
