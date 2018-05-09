package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Employee;
import data_models.WorkingDay;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkingWeekDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<WorkingDay> workingDays = new ArrayList<>();

    public static List<WorkingDay> getWorkingDays(String employeeId, List<WorkingDay> employeeWorkingDays) {
        List<WorkingDay> tmp = employeeWorkingDays;

        try {
            stmt = con.createStatement();
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.WORKINGDAY WHERE EMPLOYEEID =?");
            preparedStmt.setString(1, employeeId.toString());

            ResultSet rs = preparedStmt.executeQuery();

            int i = 0;
            while (rs.next()) {
                tmp.get(i).setWorkingDayId(rs.getString("WORKINGDAYID"));
                tmp.get(i).setStartWorkingTime(rs.getTimestamp("STARTWORK").toLocalDateTime().toLocalTime());
                tmp.get(i).setEndWorkingTime(rs.getTimestamp("ENDWORK").toLocalDateTime().toLocalTime());
                tmp.get(i).setStartBreakTime(rs.getTimestamp("STARKBREAK").toLocalDateTime().toLocalTime());
                tmp.get(i).setEndBreakTime(rs.getTimestamp("ENDBREAK").toLocalDateTime().toLocalTime());
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;

    }

    // TODO: check if the db will save the times as LocalTime
    public static void setWorkingWeek(List<WorkingDay> workingDays, String employeeId) {

        try {
/*            stmt = con.createStatement();
            StringBuilder query = new StringBuilder("INSERT INTO OPTKOS.WORKINGDAY" +
                    "(WORKINGDAYID, DAY, STARTWORK, ENDWORK, STARTBREAK, ENDBREAK, EMPLOYEEID) VALUES ");*/
            for (int i = 0; i < 7; i++) {
/*                query.append("('").append(workingWeek.getWorkingDayByIndex(i).getWorkingDayId().toString()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getDay()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getStartWorkingTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getEndWorkingTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getStartBreakTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getEndBreakTime()).append("', '")
                        .append(employeeId.toString()).append("'),");
            stmt.executeQuery(query.toString());*/

                preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.WORKINGDAY (WORKINGDAYID, DAY, STARTWORK, ENDWORK, STARKBREAK, ENDBREAK, EMPLOYEEID) VALUES(?,?,?,?,?,?,?)");
                preparedStmt.setString(1, workingDays.get(i).getWorkingDayId().toString());
                preparedStmt.setString(2, workingDays.get(i).getDay());
                preparedStmt.setTime(3, Time.valueOf(workingDays.get(i).getStartWorkingTime()));
                preparedStmt.setTime(4, Time.valueOf(workingDays.get(i).getEndWorkingTime()));
                preparedStmt.setTime(5, Time.valueOf(workingDays.get(i).getStartBreakTime()));
                preparedStmt.setTime(6, Time.valueOf(workingDays.get(i).getEndBreakTime()));
                preparedStmt.setString(7, employeeId.toString());
                preparedStmt.execute();
            }
            // stmt.executeQuery(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteWorkingDaysByEmployeeId(String employeeId) {
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.WORKINGDAY wd WHERE wd.EMPLOYEEID=?");
            preparedStmt.setString(1,employeeId);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private WorkingWeekDao() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean updateWorkingDay(WorkingDay workingDay){
        boolean result;
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.WORKINGDAY SET STARTWORK = ?, ENDWORK = ?, STARKBREAK = ?, ENDBREAK = ? WHERE WORKINGDAYID = ?");
            preparedStmt.setTime(1, Time.valueOf(workingDay.getStartWorkingTime()));
            preparedStmt.setTime(2, Time.valueOf(workingDay.getEndWorkingTime()));
            preparedStmt.setTime(3, Time.valueOf(workingDay.getStartBreakTime()));
            preparedStmt.setTime(4, Time.valueOf(workingDay.getEndBreakTime()));
            preparedStmt.setString(5, workingDay.getWorkingDayId().toString());
            result = preparedStmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return result;
    }
}
