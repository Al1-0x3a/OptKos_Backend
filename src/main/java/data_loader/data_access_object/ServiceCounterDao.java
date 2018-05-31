package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ServiceCounterDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static List<ServiceCounter> getAllServiceCounter(LocalDateTime startTime, LocalDateTime endTime){

        List<ServiceCounter> serviceCounterList = new ArrayList<>();

        try {
            preparedStmt=con.prepareStatement("SELECT OPTKOS.SERVICE.SERVICEID, OPTKOS.SERVICE.NAME, " +
                    "COUNT(OPTKOS.APOINTMENT.APOINTMENTID) AS COUNTER " +
                    "FROM OPTKOS.APOINTMENT LEFT JOIN OPTKOS.SERVICE ON OPTKOS.SERVICE.SERVICEID = OPTKOS.APOINTMENT.SERVICEID " +
                    "WHERE OPTKOS.APOINTMENT.PLANTIMESTART >= ? " +
                    "AND OPTKOS.APOINTMENT.PLANTIMEEND <= ? " +
                    "GROUP BY OPTKOS.SERVICE.SERVICEID, OPTKOS.SERVICE.NAME;");
            preparedStmt.setTimestamp(1, Timestamp.valueOf(startTime));
            preparedStmt.setTimestamp(2, Timestamp.valueOf(endTime));

            try (ResultSet rs = preparedStmt.executeQuery()) {
                while (rs.next()) {
                    ServiceCounter serviceCounter = new ServiceCounter(rs.getString("SERVICEID"));
                    serviceCounter.setServiceName(rs.getString("NAME"));
                    serviceCounter.setServiceCounter(rs.getString("COUNTER"));

                    serviceCounterList.add(serviceCounter);
                }
            }
            preparedStmt.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceCounterList;
    }
}
