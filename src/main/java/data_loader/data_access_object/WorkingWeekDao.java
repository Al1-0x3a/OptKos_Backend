package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.WorkingDay;
import data_models.WorkingWeek;

import java.sql.*;
import java.util.UUID;

public class WorkingWeekDao {
    // TODO: fill week by getting all days by sql query
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static WorkingWeek workingWeek;
    private static WorkingDay[] workingDays = new WorkingDay[6];

    public static WorkingWeek getWorkingWeek(UUID employeeId) {
        try {
            stmt = con.createStatement();
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.WORKINGDAY WHERE EMPLOYEEID =?");
            preparedStmt.setString(1, employeeId.toString());
            ResultSet rs = preparedStmt.executeQuery();

            workingWeek = new WorkingWeek();
            int i = 0;
            while (rs.next()) {
                workingWeek.getWorkingDayByIndex(i).setWorkingDayId(UUID.fromString(rs.getString("WORKINGDAYID")));
                workingWeek.getWorkingDayByIndex(i).setStartWorkingTime(rs.getTimestamp("STARTWORK").toLocalDateTime().toLocalTime());
                workingWeek.getWorkingDayByIndex(i).setEndWorkingTime(rs.getTimestamp("ENDWORK").toLocalDateTime().toLocalTime());
                workingWeek.getWorkingDayByIndex(i).setStartBreakTime(rs.getTimestamp("STARKBREAK").toLocalDateTime().toLocalTime());
                workingWeek.getWorkingDayByIndex(i).setEndBreakTime(rs.getTimestamp("ENDBREAK").toLocalDateTime().toLocalTime());
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workingWeek;

    }

    // TODO: check if the db will save the times as LocalTime
    public static void setWorkingWeek(WorkingWeek workingWeek, UUID employeeId) {
        try {
/*            stmt = con.createStatement();
            StringBuilder query = new StringBuilder("INSERT INTO OPTKOS.WORKINGDAY" +
                    "(WORKINGDAYID, DAY, STARTWORK, ENDWORK, STARTBREAK, ENDBREAK, EMPLOYEEID) VALUES ");*/
            for (int i = 0; i < 6; i++) {
/*                query.append("('").append(workingWeek.getWorkingDayByIndex(i).getWorkingDayId().toString()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getDay()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getStartWorkingTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getEndWorkingTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getStartBreakTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getEndBreakTime()).append("', '")
                        .append(employeeId.toString()).append("'),");
            stmt.executeQuery(query.toString());*/

                preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.WORKINGDAY (WORKINGDAYID, DAY, STARTWORK, ENDWORK, STARKBREAK, ENDBREAK, EMPLOYEEID) VALUES(?,?,?,?,?,?,?)");
                preparedStmt.setString(1, workingWeek.getWorkingDayByIndex(i).getWorkingDayId().toString());
                preparedStmt.setString(2, workingWeek.getWorkingDayByIndex(i).getDay());
                preparedStmt.setTime(3, Time.valueOf(workingWeek.getWorkingDayByIndex(i).getStartWorkingTime()));
                preparedStmt.setTime(4, Time.valueOf(workingWeek.getWorkingDayByIndex(i).getEndWorkingTime()));
                preparedStmt.setTime(5, Time.valueOf(workingWeek.getWorkingDayByIndex(i).getStartBreakTime()));
                preparedStmt.setTime(6, Time.valueOf(workingWeek.getWorkingDayByIndex(i).getEndBreakTime()));
                preparedStmt.setString(7, employeeId.toString());
                preparedStmt.execute();
            }
            // stmt.executeQuery(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteWorkingDaysByEmployeeId(UUID employeeId) {
        try {
            stmt = con.createStatement();
            stmt.executeQuery("DELETE FROM OPTKOS.WORKINGDAY wd WHERE wd.EMPLOYEEID='" + employeeId.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
