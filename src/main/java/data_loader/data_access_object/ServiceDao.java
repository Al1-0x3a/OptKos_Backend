package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Service;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private ServiceDao() {
    }

    public static List<Service> getAllServicesFromDb() {
        long start = System.nanoTime();
        List<Service> serviceList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.SERVICE");
            try (ResultSet rs = preparedStmt.executeQuery()) {

                while (rs.next()) {
                    serviceList.add(new Service(rs.getString("SERVICEID"),
                            rs.getString("NAME"), rs.getString("DESCRIPTION"),
                            rs.getBigDecimal("PRICE"), Duration.ofMinutes(
                            rs.getInt("DURTATIONPLANNED")),
                            Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                            rs.getString("ISDELETED")));
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ServiceEmployeeDurationDao.setDurationsToService(serviceList);
        System.out.println("Get all Services from Db: " + (System.nanoTime() - start)/1e6 + " ms");
        return serviceList;
    }

    public static boolean createService(Service service) {
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.SERVICE(SERVICEID, NAME, DESCRIPTION, PRICE," +
                            " DURTATIONPLANNED, DURATIONAVERAGE, ISDELETED) VALUES(?,?,?,?,?,?,?)");
            preparedStmt.setString(1, service.getServiceId());
            preparedStmt.setString(2, service.getName());
            preparedStmt.setString(3, service.getDescription());
            preparedStmt.setBigDecimal(4, service.getPrice());
            preparedStmt.setInt(5, (int) service.getDurationPlanned().toMinutes());
            preparedStmt.setInt(6, (int) service.getDurationPlanned().toMinutes());
            preparedStmt.setString(7, service.getIsDeleted());

            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Service getServiceById(String serviceId){
        Service service = null;
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.SERVICE WHERE SERVICEID=?");
            preparedStmt.setString(1, serviceId);
            try(ResultSet rs = preparedStmt.executeQuery()){
                service = new Service(rs.getString("SERVICEID"),
                        rs.getString("NAME"), rs.getString("DESCRIPTION"),
                        rs.getBigDecimal("PRICE"), Duration.ofMinutes(
                        rs.getInt("DURTATIONPLANNED")),
                        Duration.ofMinutes(rs.getInt("DURATIONAVERAGE")),
                        rs.getString("ISDELETED"));
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    public static boolean updateService(Service service) {
        boolean result;
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.SERVICE SET NAME=?, DESCRIPTION=?, PRICE=?, " +
                    "DURTATIONPLANNED=?, DURATIONAVERAGE=?, ISDELETED=? WHERE SERVICEID=?");
            preparedStmt.setString(1, service.getName());
            preparedStmt.setString(2, service.getDescription());
            preparedStmt.setBigDecimal(3, service.getPrice());
            preparedStmt.setInt(4, (int) service.getDurationPlanned().toMinutes());
            preparedStmt.setInt(5, (int) service.getDurationAverage().toMinutes());
            preparedStmt.setString(6, service.getIsDeleted());
            preparedStmt.setString(7, service.getServiceId());

            result = preparedStmt.executeUpdate() != 0;
            preparedStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public static boolean deleteServiceByServiceId(String serviceId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.SERVICE WHERE SERVICEID =?");
            preparedStmt.setString(1, serviceId);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

}
