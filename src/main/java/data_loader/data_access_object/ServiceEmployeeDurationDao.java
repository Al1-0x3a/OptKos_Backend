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
        try{
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.SERVICEEMPLOYEEDURATION WHERE EMPLOYEEID=?" +
                    "AND SERVICEID=?") ;
            preparedStmt.setString(1, employeeId);
            preparedStmt.setString(2, serviceId);

            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    sed = new ServiceEmployeeDuration(Duration.ofMinutes(rs.getInt("DURATIONPLANNED")),
                            Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                            rs.getString("EMPLOYEEID"), rs.getString("SERVICEID"));
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
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.SERVICEEMPLOYEEDURATION");
            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()) {
                    sedList.add(new ServiceEmployeeDuration(Duration.ofMinutes(((long) rs.getInt("DURATIONPLANNED"))),
                            Duration.ofMinutes(((long) rs.getInt("DURATIONAVERAGE"))),
                            rs.getString("EMPLOYEEID"), rs.getString("SERVICEID")));
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
}
