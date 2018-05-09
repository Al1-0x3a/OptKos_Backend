package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static PreparedStatement preparedStmt2;
    private static List<Employee> employeeList;

    public EmployeeDao() {
        employeeList = new ArrayList<>();
    }

    public static List<Employee> getEmployeeList() {
        if (employeeList == null) {
            employeeList = getAllEmployeesFromDb();
        }
        return employeeList;
    }

    public static List<Employee> getAllEmployeesFromDb() {
        employeeList = new ArrayList<>();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PERSON p, OPTKOS.EMPLOYEE e WHERE p.PERSONID = e.PERSONID";
            ResultSet rs = stmt.executeQuery(query);

            employeeList = new ArrayList<>();
            while (rs.next()) {
                // Person
                Employee employee = new Employee(rs.getString("PERSONID"));
                // employee.setPersonId(UUID.fromString(rs.getString("PERSONID")));
                employee.setFirstname(rs.getString("FIRSTNAME"));
                employee.setLastname(rs.getString("LASTNAME"));
                employee.setTitle(Person.TITLE.valueOf(rs.getString("TITLE")));
                employee.setSalutation(Person.SALUTATION.valueOf(rs.getString("SALUTATION")));
                employee.setGender(Person.GENDER.valueOf(rs.getString("GENDER")));
                // Employee
                employee.setEmployeeId(rs.getString("EMPLOYEEID"));
                employee.setIsDeleted(rs.getString("ISDELETED").charAt(0));
                employee.setPositionId(rs.getString("POSITIONID"));
                // other objects
                employee.setPhoneList(PhoneDao.getListByPersonId(employee.getPersonId()));
                employee.setEmailList(EmailDao.getEmailListByPersonId(employee.getPersonId()));
                employee.setAddress(AddressDao.getAddressByPersonId(employee.getPersonId()));
                employee.setWorkingDays(WorkingWeekDao.getWorkingDays(employee.getEmployeeId(), employee.getWorkingDays()));


                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public static boolean deleteEmployee(Employee employee) {
        boolean b = false;
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMPLOYEE WHERE employeeId =?");
            preparedStmt.setString(1, employee.getEmployeeId().toString());

            preparedStmt2 = con.prepareStatement("DELETE FROM OPTKOS.PERSON WHERE PERSONID =?");
            preparedStmt2.setString(1, employee.getPersonId().toString());

            AddressDao.deleteAddressByPersonId(employee.getPersonId());
            EmailDao.deleteEmailByPersonId(employee.getPersonId());
            PhoneDao.deleteAllPhoneByPersonId(employee.getPersonId());
            WorkingWeekDao.deleteWorkingDaysByEmployeeId(employee.getEmployeeId());

            preparedStmt.executeUpdate();
            preparedStmt2.executeUpdate();


            employeeList.removeIf(o -> o.getEmployeeId() == employee.getEmployeeId());
            b = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean createNewEmployee(Employee employee) {
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.PERSON (PERSONID, LASTNAME, FIRSTNAME, TITLE, SALUTATION, GENDER) VALUES(?,?,?,?,?,?)");


            preparedStmt.setString(1, employee.getPersonId().toString());
            preparedStmt.setString(2, employee.getLastname());
            preparedStmt.setString(3, employee.getFirstname());
            preparedStmt.setString(4, employee.getTitle().name());
            preparedStmt.setString(5, employee.getSalutation().name());
            preparedStmt.setString(6, employee.getGender().name());

            preparedStmt2 = con.prepareStatement("INSERT INTO OPTKOS.EMPLOYEE(EMPLOYEEID,PERSONID, ISDELETED, POSITIONID) VALUES(?,?,?,?)");
            // preparedStmt2 = con.prepareStatement("INSERT INTO OPTKOS.EMPLOYEE(EMPLOYEEID,PERSONID,POSITIONID) VALUES(?,?,?)");

            preparedStmt2.setString(1, employee.getEmployeeId().toString());
            preparedStmt2.setString(2, employee.getPersonId().toString());
            preparedStmt2.setString(3, "0");
            preparedStmt2.setString(4, "8398cd47-ab14-4fa9-810b-69383a6c4285");
            //preparedStmt2.setString(3, employee.getPositionId().toString());

            preparedStmt.execute();
            preparedStmt2.execute();

            AddressDao.createNewAddress(employee.getAddress(), employee.getPersonId());
            if(employee.getPhoneList().size()!=0) {
                for(int i = 0; i<employee.getPhoneList().size(); i++) {
                    // CAUTION!! Dirty Hack
                    employee.getPhoneList().get(i).setPersonId(employee.getPersonId());

                    PhoneDao.createPhone(employee.getPhoneList().get(i));
                }
            }
            if(employee.getEmailList().size() != 0) {
                for(int i = 0; i<employee.getEmailList().size(); i++) {
                    // CAUTION!! Dirty Hack
                    employee.getEmailList().get(i).setPersonId(employee.getPersonId());

                    EmailDao.createEmail(employee.getEmailList().get(i));
                }
            }

            WorkingWeekDao.setWorkingWeek(employee.getWorkingDays(), employee.getEmployeeId());

            employeeList.add(employee);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Employee getEmployeeById(String empolyeeId) {
        Employee employee = null;
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList != null && employeeList.get(i).getEmployeeId() == empolyeeId) {
                employee = employeeList.get(i);
                break;
            }
        }

        if (employee == null) {
            employee = getEmployeeByIdFromDb(empolyeeId);
            if (employee != null)
                employeeList.add(employee);
        }
        return employee;
    }


    public static Employee getEmployeeByIdFromDb(String employeeId) {

        Employee employee = new Employee();
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PERSON p, OPTKOS.EMPLOYEE e WHERE p.PERSONID = e.PERSONID AND " +
                    "e.EMPLOYEEID=" + employeeId.toString() + ";";
            ResultSet rs = stmt.executeQuery(query);

            // Person
            employee.setPersonId(rs.getString("PERSONID"));
            employee.setFirstname(rs.getString("FIRSTNAME"));
            employee.setLastname(rs.getString("LASTNAME"));
            System.out.println(employee.getFirstname() + " " + employee.getLastname());
            employee.setTitle(Person.TITLE.valueOf(rs.getString("TITEL")));
            employee.setSalutation(Person.SALUTATION.valueOf(rs.getString("SALUTATION")));
            employee.setGender(Person.GENDER.valueOf(rs.getString("GENDER")));
            // Employee
            employee.setEmployeeId(rs.getString("EMPLOYEEID"));
            employee.setIsDeleted(rs.getString("ISDELETED").charAt(0));
            employee.setPositionId(rs.getString("POSITIONID"));
            // other objects
            employee.setPhoneList(PhoneDao.getListByPersonId(employee.getPersonId()));
            employee.setEmailList(EmailDao.getEmailListByPersonId(employee.getPersonId()));
            employee.setAddress(AddressDao.getAddressByPersonId(employee.getPersonId()));
            employee.setWorkingDays(WorkingWeekDao.getWorkingDays(employee.getEmployeeId(), employee.getWorkingDays()));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static boolean updateEmployee(Employee employee){
        boolean result;
        try {
            // Person
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.PERSON SET LASTNAME=?,FIRSTNAME=?,TITLE=?," +
                    "SALUTATION=?,GENDER=? WHERE PERSONID=?");
            preparedStmt.setString(1, employee.getLastname());
            preparedStmt.setString(2, employee.getFirstname());
            preparedStmt.setString(3, employee.getTitle().name());
            preparedStmt.setString(4, employee.getSalutation().name());
            preparedStmt.setString(5, employee.getGender().name());
            preparedStmt.setString(6, employee.getPersonId());

            // Employee
            preparedStmt2 = con.prepareStatement("UPDATE OPTKOS.EMPLOYEE SET POSITIONID=?, EMPLOYEEID=? WHERE PERSONID=?");
            preparedStmt2.setString(1, employee.getPositionId());
            preparedStmt2.setString(2, employee.getEmployeeId());
            preparedStmt2.setString(3, employee.getPersonId());

            boolean result1 = preparedStmt.executeUpdate() != 0;
            boolean result2 = preparedStmt2.executeUpdate() != 0;
            result = result1 && result2;

            // other
            EmailDao.deleteEmailByPersonId(employee.getEmailList().get(0).getPersonId());
            for (Email e :
                    employee.getEmailList()) {
                EmailDao.createEmail(e);
            }

            PhoneDao.deleteAllPhoneByPersonId(employee.getPhoneList().get(0).getPersonId());
            for (Phone p :
                    employee.getPhoneList()) {
               PhoneDao.createPhone(p);
            }

            AddressDao.updateAddress(employee.getAddress());

            for (WorkingDay w :
                    employee.getWorkingDays()) {
               WorkingWeekDao.updateWorkingDay(w);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

}


// TODO:java class properties an methode übergeben um spezielle sachen zu suchen/ändern
