/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
            preparedStmt=con.prepareStatement("SELECT s.SERVICEID, s.NAME, " +
                    "COUNT(a.SERVICEID) AS COUNTER " +
                    "FROM OPTKOS.SERVICE s LEFT JOIN ( SELECT * FROM OPTKOS.APOINTMENT " +
                    "WHERE OPTKOS.APOINTMENT.PLANTIMESTART >= ? " +
                    "AND OPTKOS.APOINTMENT.PLANTIMEEND <= ? ) " +
                    "a ON s.SERVICEID = a.SERVICEID WHERE s.SERVICEID = ? " +
                    "GROUP BY s.SERVICEID, s.NAME");
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
