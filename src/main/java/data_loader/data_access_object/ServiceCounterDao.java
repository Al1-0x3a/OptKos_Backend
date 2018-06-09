package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.*;

import java.sql.*;
import java.time.LocalDateTime;


public class ServiceCounterDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static ServiceCounter getAllServiceCounter(String serviceId, LocalDateTime startTime,
    LocalDateTime endTime){

        ServiceCounter serviceCounter = null;

        try {
            preparedStmt=con.prepareStatement("SELECT s.SERVICEID, s.NAME, COUNT(a.SERVICEID) AS COUNTER FROM OPTKOS.SERVICE s " +
                    "LEFT JOIN ( select * FROM OPTKOS.APOINTMENT " +
                    "WHERE OPTKOS.APOINTMENT.INDEEDTIMESTART >= ? " +
                    "AND OPTKOS.APOINTMENT.INDEEDTIMEEND <= ?) a on s.SERVICEID = a.SERVICEID " +
                    "AND OPTKOS.APOINTMENT.SERVICEID = ? " +
                    "GROUP BY s.SERVICEID, s.NAME\n");
            preparedStmt.setTimestamp(1, Timestamp.valueOf(startTime));
            preparedStmt.setTimestamp(2, Timestamp.valueOf(endTime));
            preparedStmt.setString(3, serviceId);

            try (ResultSet rs = preparedStmt.executeQuery()) {
                while (rs.next()) {
                    serviceCounter = new ServiceCounter(rs.getString("SERVICEID"));
                    serviceCounter.setServiceName(rs.getString("NAME"));
                    serviceCounter.setServiceCounter(rs.getInt("COUNTER"));
                }
            }
            preparedStmt.close();
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceCounter;
    }
}
