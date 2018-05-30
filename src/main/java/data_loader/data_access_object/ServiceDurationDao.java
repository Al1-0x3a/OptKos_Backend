package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.ServiceEmployeeDuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
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
            preparedStmt.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    // TODO: Look at dis sql query

    public static List<ServiceEmployeeDuration> getServiceDuration(String employeeId, String serviceId){
        List<ServiceEmployeeDuration> durations = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT sed.*, p.LASTNAME, p.FIRSTNAME FROM " +
                    "OPTKOS.SERVICEEMPLOYEEDURATION sed, OPTKOS.EMPLOYEE e, OPTKOS.PERSON p WHERE sed.EMPLOYEEID=? " +
                    "AND sed.SERVICEID=? AND e.PERSONID=? AND e.PERSONID=p.PERSONID");

            preparedStmt.setString(1, employeeId);
            preparedStmt.setString(2, serviceId);
            preparedStmt.setString(3, employeeId);
            try(ResultSet rs = preparedStmt.executeQuery()) {

                while (rs.next()) {
                    durations.add(new ServiceEmployeeDuration(Duration.ofMinutes(
                            rs.getInt("DURATIONPLANNED")),
                            Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                            rs.getString("EMPLOYEEID"),
                            rs.getString("SERVICEID")));
                    durations.get(durations.size()).setFirstName(rs.getString("FIRSTNAME"));
                    durations.get(durations.size()).setLastName(rs.getString("LASTNAME"));
                }
            }
            preparedStmt.close();

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
            preparedStmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
