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

    public static ServiceEmployeeDuration getServiceEmployeeDuration(String employeeId, String serviceId){
        ServiceEmployeeDuration sed = null;
        //TODO name dazu
        try{
            preparedStmt = con.prepareStatement("SELECT * FROM ((OPTKOS.SERVICEEMPLOYEEDURATION sed " +
                    "INNER JOIN OPTKOS.EMPLOYEE e ON sed.EMPLOYEEID = e.EMPLOYEEID) " +
                    "INNER JOIN OPTKOS.PERSON p ON p.PERSONID = e.PERSONID) WHERE sed.EMPLOYEEID=? " +
                    "AND sed.SERVICEID=?");
            preparedStmt.setString(1, employeeId);
            preparedStmt.setString(2, serviceId);

            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    sed = new ServiceEmployeeDuration(Duration.ofMinutes(rs.getInt("DURATIONPLANNED")),
                            Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                            rs.getString("EMPLOYEEID"), rs.getString("SERVICEID"),
                            rs.getString("LASTNAME"), rs.getString("FIRSTNAME"));
                }
            }
            preparedStmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sed;
    }

    public static boolean createServiceEmployeeDuration(ServiceEmployeeDuration sed){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.SERVICEEMPLOYEEDURATION(DURATIONPLANNED," +
                    " SERVICEID, DURATIONAVERAGE, EMPLOYEEID) VALUES (DURATIONPLANNED=?, SERVICEID=?," +
                    " DURATIONAVERAGE=?, EMPLOYEEID=?)");
            preparedStmt.setInt(1, (int)sed.getDurationPlanned().toMinutes());
            preparedStmt.setString(2, sed.getServiceId());
            preparedStmt.setInt(3, (int)sed.getDurationAverage().toMinutes());
            preparedStmt.setString(4, sed.getEmployeeId());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updateServiceEmployeeDuration(ServiceEmployeeDuration sed){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.SERVICEEMPLOYEEDURATION SET DURATIONPLANNED=? " +
                    "WHERE SERVICEID=? AND EMPLOYEEID=?");
            preparedStmt.setInt(1, ((int) sed.getDurationPlanned().toMinutes()));
            preparedStmt.setString(2, sed.getServiceId());
            preparedStmt.setString(3, sed.getEmployeeId());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

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
            preparedStmt = con.prepareStatement("DELTE FROM OPTKOS.SERVICEEMPLOYEEDURATION WHERE EMPLOYEEID=?");
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
