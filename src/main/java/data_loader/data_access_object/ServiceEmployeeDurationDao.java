/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Service;
import data_models.ServiceEmployeeDuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceEmployeeDurationDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static List<ServiceEmployeeDuration> getAllServiceEmployeeDurations(){
        List<ServiceEmployeeDuration> sedList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM ((OPTKOS.SERVICEEMPLOYEEDURATION sed " +
                    "INNER JOIN OPTKOS.EMPLOYEE e ON sed.EMPLOYEEID = e.EMPLOYEEID) " +
                    "INNER JOIN OPTKOS.PERSON p ON p.PERSONID = e.PERSONID)");
            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()) {
                    sedList.add(new ServiceEmployeeDuration(Duration.ofMinutes(((long) rs.getInt("DURATIONPLANNED"))),
                            Duration.ofMinutes(((long) rs.getInt("DURATIONAVERAGE"))),
                            rs.getString("EMPLOYEEID"), rs.getString("SERVICEID"),
                            rs.getString("LASTNAME"), rs.getString("FIRSTNAME")));
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sedList;
    }

    public static void setDurationsToService(List<Service> serviceList){
        List<ServiceEmployeeDuration> sedList = ServiceEmployeeDurationDao.getAllServiceEmployeeDurations();
        for (Service service :
                serviceList) {
            List<ServiceEmployeeDuration> filteredList = sedList.stream().filter(s -> s.getServiceId()
                    .equals(service.getServiceId())).collect(Collectors.toList());
            sedList.removeAll(filteredList);
            service.setSedList(filteredList);
        }
    }
    public static void deleteSedListbyEmployeeID(String EmployeeID){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.SERVICEEMPLOYEEDURATION WHERE EMPLOYEEID=?");
            preparedStmt.setString(1, EmployeeID);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ServiceEmployeeDuration> getSedListWithOnlyEmployees(String serviceId){
        List<ServiceEmployeeDuration> sedList = new ArrayList<>();
        try{
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.EMPLOYEE e LEFT JOIN OPTKOS.PERSON p " +
                    "ON e.PERSONID=p.PERSONID") ;

            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    ServiceEmployeeDuration sed = new ServiceEmployeeDuration(Duration.ofMinutes(0),
                            Duration.ofMinutes(0),
                            rs.getString("EMPLOYEEID"), serviceId, rs.getString("LASTNAME"),
                            rs.getString("FIRSTNAME"));
                    sedList.add(sed);
                }
            }
            preparedStmt.close();
        }catch(SQLException e){
            System.err.println("Error while creating sedList with only Employees...");
            e.printStackTrace();
        }
        return sedList;
    }
}
