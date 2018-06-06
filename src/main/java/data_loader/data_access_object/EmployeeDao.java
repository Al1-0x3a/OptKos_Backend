package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;
    private static PreparedStatement preparedStmt2;

    public static List<Employee> getAllEmployeesFromDb() {
        long start = System.nanoTime();
        List<Employee> employeeList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.PERSON p, OPTKOS.EMPLOYEE em, " +
                    "OPTKOS.ADDRESS a, OPTKOS.POSITION pos " +
                    "WHERE p.PERSONID = em.PERSONID AND a.PERSONID = p.PERSONID AND pos.POSITIONID = em.POSITIONID");

            try (ResultSet rs = preparedStmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = buildEmployee(rs);

                    // Position
                    Position position = new Position();
                    position.setName(rs.getString("NAME"));
                    position.setDescription(rs.getString("DESCRIPTION"));
                    position.setNote(rs.getString("ANNOTATION"));

                    employee.setPosition(position);

                    // Address
                    Address address = AddressDao.buildAddress(rs);

                    employee.setAddress(address);

                    employeeList.add(employee);
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Phone
        List<Phone> phoneList = PhoneDao.getAllPhonesFromDb();
        for(Employee e:employeeList){

            List<Phone> filteredList = phoneList.stream().filter(p -> p.getPersonId().equals( e.getPersonId()))
                    .collect(Collectors.toList());
            phoneList.removeAll(filteredList);
            e.getPhoneList().addAll(filteredList);
        }

        //Email
        List<Email> emailList = EmailDao.getAllEmailsFromDb();
        for(Employee e:employeeList){

            List<Email> filteredList = emailList.stream().filter(p -> p.getPersonId().equals( e.getPersonId()))
                    .collect(Collectors.toList());
            emailList.removeAll(filteredList);
            e.getEmailList().addAll(filteredList);
        }

        //WorkingDay
        List<WorkingDay> workingDayList = WorkingWeekDao.getAllWorkingDaysFromDb();
        for(Employee e:employeeList){
            WorkingDay[] workingDays = new WorkingDay[7];
            int index = 0;
            for(int i = 0; i< workingDayList.size(); i++){
                if(workingDayList.get(i).getEmployeeId().equals(e.getEmployeeId())) {
                    index = WorkingWeekDao.getDayIndex(workingDayList.get(i).getDay());
                    workingDays[index] = workingDayList.get(i);
                }
            }
            e.setWorkingDays(Arrays.asList(workingDays));
        }

        System.out.println("Get all Employees from Db: " + (System.nanoTime() - start)/1e6 + " ms");
        return employeeList;
    }

    public static boolean deleteEmployee(Employee employee) {
        boolean b = false;
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMPLOYEE WHERE employeeId =?");
            preparedStmt.setString(1, employee.getEmployeeId());

            preparedStmt2 = con.prepareStatement("DELETE FROM OPTKOS.PERSON WHERE PERSONID =?");
            preparedStmt2.setString(1, employee.getPersonId());

            AddressDao.deleteAddressByPersonId(employee.getPersonId());
            EmailDao.deleteEmailByPersonId(employee.getPersonId());
            PhoneDao.deleteAllPhoneByPersonId(employee.getPersonId());
            WorkingWeekDao.deleteWorkingDaysByEmployeeId(employee.getEmployeeId());

            preparedStmt.executeUpdate();
            preparedStmt2.executeUpdate();

            b = true;
            preparedStmt.close();
            preparedStmt2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean createNewEmployee(Employee employee) {
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.PERSON (PERSONID, LASTNAME, FIRSTNAME, TITLE, SALUTATION, GENDER) " +
                            "VALUES(?,?,?,?,?,?)");


            preparedStmt.setString(1, employee.getPersonId());
            preparedStmt.setString(2, employee.getLastname());
            preparedStmt.setString(3, employee.getFirstname());
            preparedStmt.setString(4, employee.getTitle().name());
            preparedStmt.setString(5, employee.getSalutation().name());
            preparedStmt.setString(6, employee.getGender().name());

            preparedStmt2 = con.prepareStatement("INSERT INTO OPTKOS.EMPLOYEE(EMPLOYEEID,PERSONID, ISDELETED," +
                    " POSITIONID, COLOUR) VALUES(?,?,?,?,?)");
            preparedStmt2.setString(1, employee.getEmployeeId());
            preparedStmt2.setString(2, employee.getPersonId());
            preparedStmt2.setString(3, "0");
            preparedStmt2.setString(4, "8398cd47-ab14-4fa9-810b-69383a6c4285");
            preparedStmt2.setString(5, employee.getColour());

            preparedStmt.execute();
            preparedStmt2.execute();
            preparedStmt.close();
            preparedStmt2.close();

            AddressDao.createNewAddress(employee.getAddress(), employee.getPersonId());
            if (!employee.getPhoneList().isEmpty()) {
                for (int i = 0; i < employee.getPhoneList().size(); i++) {
                    employee.getPhoneList().get(i).setPersonId(employee.getPersonId());

                    PhoneDao.createPhone(employee.getPhoneList().get(i));
                }
            }
            if (!employee.getEmailList().isEmpty()) {
                for (int i = 0; i < employee.getEmailList().size(); i++) {
                    employee.getEmailList().get(i).setPersonId(employee.getPersonId());

                    EmailDao.createEmail(employee.getEmailList().get(i));
                }
            }

            WorkingWeekDao.setWorkingWeek(employee.getWorkingDays(), employee.getEmployeeId());


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
            preparedStmt2 = con.prepareStatement("UPDATE OPTKOS.EMPLOYEE SET POSITIONID=?, EMPLOYEEID=?, " +
                    "COLOUR=? WHERE PERSONID=?");
            preparedStmt2.setString(1, employee.getPosition().getPositionId());
            preparedStmt2.setString(2, employee.getEmployeeId());
            preparedStmt2.setString(3,employee.getColour());
            preparedStmt2.setString(4, employee.getPersonId());

            boolean result1 = preparedStmt.executeUpdate() != 0;
            boolean result2 = preparedStmt2.executeUpdate() != 0;
            preparedStmt.close();
            preparedStmt2.close();
            result = result1 && result2;

            // other
            EmailDao.deleteEmailByPersonId(employee.getPersonId());
            for (Email e :
                    employee.getEmailList()) {
                EmailDao.createEmail(e);
            }
            PhoneDao.deleteAllPhoneByPersonId(employee.getPersonId());
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

    public static Employee buildEmployee(ResultSet rs){
        Employee employee = null;
        try {
            employee = new Employee(rs.getString("PERSONID"));
            employee.setFirstname(rs.getString("FIRSTNAME"));
            employee.setLastname(rs.getString("LASTNAME"));
            employee.setTitle(Person.TITLE.valueOf(rs.getString("TITLE")));
            employee.setSalutation(Person.SALUTATION.valueOf(rs.getString("SALUTATION")));
            employee.setGender(Person.GENDER.valueOf(rs.getString("GENDER")));

            // Employee
            employee.setEmployeeId(rs.getString("EMPLOYEEID"));
            employee.setIsDeleted(rs.getString("ISDELETED").charAt(0));
            employee.setColour(rs.getString("COLOUR"));
            // employee.setPositionId(rs.getString("POSITIONID"));
        } catch (SQLException e) {
            System.err.println("Error while building Employee");
            e.printStackTrace();
            return null;
        }
        return employee;
    }
}


