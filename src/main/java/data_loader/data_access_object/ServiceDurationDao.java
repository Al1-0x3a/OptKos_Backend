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

public class ServiceDurationDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static boolean createServiceDuration(String employeeId, String serviceId,int duration){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.SERVICEEMPLOYEEDURATION(DURATIONPLANNED, " +
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

   public static boolean createServiceDurationForEveryEmployee(Service service){
        ArrayList<String> employeeList = new ArrayList<>();
       StringBuilder sb = new StringBuilder();
       sb.append("INSERT INTO OPTKOS.SERVICEEMPLOYEEDURATION(DURATIONPLANNED, SERVICEID, EMPLOYEEID," +
               " DURATIONAVERAGE) VALUES ");
       
       try {
           preparedStmt = con.prepareStatement("SELECT EMPLOYEEID FROM OPTKOS.EMPLOYEE");
           try(ResultSet rs = preparedStmt.executeQuery()){
               while(rs.next()) {
                   employeeList.add(rs.getString("EMPLOYEEID"));
               }
           }
           preparedStmt.close();
           
           for(int i = 0; i<employeeList.size()-1; i++){
               sb.append("(?,?,?,?), ");
           }
           sb.append("(?,?,?,?)");
           int counter = 1;
           preparedStmt = con.prepareStatement(sb.toString());

           for (String s :
                   employeeList) {
               preparedStmt.setInt(counter, ((int)service.getDurationPlanned().toMinutes()));
               preparedStmt.setString(counter+1, service.getServiceId());
               preparedStmt.setString(counter+2, s);
               preparedStmt.setInt(counter+3, 0);
               counter += 4;
           }
           preparedStmt.execute();
           preparedStmt.close();
       } catch (SQLException e) {
           System.err.println("Error while Inserting ServiceEmployeeDurations");
           e.printStackTrace();
           return false;
       }
       return true;
   }

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
                            rs.getString("SERVICEID"),
                            rs.getString("LASTNAME"),
                            rs.getString("FIRSTNAME")));
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

   public static boolean updateServicesDurations(Service service){
       try {
           preparedStmt = con.prepareStatement("UPDATE OPTKOS.SERVICEEMPLOYEEDURATION SET DURATIONPLANNED=? WHERE " +
                   "SERVICEID=?");
           preparedStmt.setInt(1, ((int) service.getDurationPlanned().toMinutes()));
           preparedStmt.setString(2, service.getServiceId());
           preparedStmt.executeUpdate();
           preparedStmt.close();
       } catch (SQLException e) {
           System.err.println("Error while updateing ServiceEmployeeDurations");
           e.printStackTrace();
           return false;
       }
       return true;
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
