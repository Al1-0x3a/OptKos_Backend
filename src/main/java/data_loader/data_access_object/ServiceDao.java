package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Service;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class ServiceDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Service> serviceList;

    public static List<Service> getAllServicesFromDb(){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.SERVICE";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                serviceList.add(new Service(UUID.fromString(rs.getString("SERVICEID")),
                        rs.getString("NAME"), rs.getString("DESCRIPTION"),
                        rs.getBigDecimal("PRICE"), rs.getTime("DURATIONPLANNED"),
                        rs.getTime("DURATIONAVERAGE"), rs.getString("ISDELETED")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    // TODO: change db type of the durations, or change type in service class
    public static void createService(Service service){
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.SERVICE(SERVICEID, NAME, DESCRIPTION, PRICE," +
                            " DURTATIONPLANNED, DURATIONAVERAGE) VALUES(?,?,?,?,?,?)");
            preparedStmt.setString(1, service.getServiceId().toString());
            preparedStmt.setString(2, service.getName());
            preparedStmt.setString(3, service.getDescription());
            preparedStmt.setBigDecimal(4, service.getPrice());
            preparedStmt.setTime(5,service.getDurationPlanned());
            preparedStmt.setTime(6,service.getDurationAverage());

            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Service getServiceById(UUID serviceId){
        if(serviceList == null ){
            serviceList = getAllServicesFromDb();
        }

        Service tmp = null;
        for (Service p : serviceList)
        {
            if(p.getServiceId() == serviceId){
                tmp = p;
            }
        }
        return tmp;
    }

    public static void deleteServiceByServiceId(UUID ServiceId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.SERVICE WHERE SERVICEID =?;");
            preparedStmt.setString(1, ServiceId.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
