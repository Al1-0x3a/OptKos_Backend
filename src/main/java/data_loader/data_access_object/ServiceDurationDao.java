package data_loader.data_access_object;

import data_loader.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDurationDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static boolean createServiceDuration(String employeeId, String serviceId,int duration){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.SERVICEEMPLOYEEDURATION(DURATION, " +
                    "SERVICEID, EMPLOYEEID) VALUES (?,?,?)");
            preparedStmt.setInt(1, duration);
            preparedStmt.setString(2, serviceId);
            preparedStmt.setString(3, employeeId);

            preparedStmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static List<Integer> getServiceDuration(String employeeId, String serviceId){
        List<Integer> durations = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.SERVICEEMPLOYEEDURATION WHERE EMPLOYEEID=?" +
                    " AND SERVICEID=?");
            preparedStmt.setString(1, employeeId);
            preparedStmt.setString(2, serviceId);
            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()){
                durations.add(rs.getInt("DURATION"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return durations;
    }


    public static boolean deleteServiceDuration(String serviceDurationId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.SERVICEEMPLOYEEDURATION WHERE SERVICEID=?");
            preparedStmt.setString(1, serviceDurationId);
            preparedStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
