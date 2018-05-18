package data_loader.data_access_object;

import data_loader.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ServiceDurationDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static boolean createServiceDuration(){

        return false;
    }

    public static List<Duration> getServiceDuration(String employeeId, String serviceId){
        List<Duration> durations = new ArrayList<>();

        return durations;
    }

    public static boolean createServiceDuration(String serviceId, String employeeId, Duration duration){

        return false;
    }

    public static boolean deleteServiceDuration(String serviceDurationId){

        return false;
    }

}
