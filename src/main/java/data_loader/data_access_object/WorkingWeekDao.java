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

    public static WorkingWeek getWorkingWeek(UUID employeeId){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.WORKINGDAY";
            ResultSet rs = stmt.executeQuery(query);

            int i = 0;
            while(rs.next()){
                WorkingDay wd = new WorkingDay(UUID.fromString(rs.getString("WORKINGDAYID")),rs.getTimestamp("STARTWORK").toLocalDateTime().toLocalTime(),
                        rs.getTimestamp("ENDWORK").toLocalDateTime().toLocalTime(),
                        rs.getTimestamp("STARTBREAK").toLocalDateTime().toLocalTime(),
                        rs.getTimestamp("ENDWORK").toLocalDateTime().toLocalTime(),
                        rs.getString("DAY"));
                workingDays[i] = wd;
                i++;
            } }catch (SQLException e) {
            e.printStackTrace();
        }
        workingWeek.setWorkingDays(workingDays);
        return workingWeek;

}

    // TODO: check if the db will save the times as LocalTime
    public static void setWorkingWeek(WorkingWeek workingWeek, UUID employeeId){
        try {
            stmt = con.createStatement();
            StringBuilder query = new StringBuilder("INSERT INTO OPTKOS.WORKINGDAY" +
                    "(WORKINGDAYID, DAY, STARTWORK, ENDWORK, STARTBREAK, ENDBREAK, EMPLOYEEID) VALUES ");
            for(int i = 0; i< 6; i++){
                query.append("('").append(workingWeek.getWorkingDayByIndex(i).getWorkingDayId().toString()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getDay()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getStartWorkingTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getEndWorkingTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getStartBreakTime()).append("', '")
                        .append(workingWeek.getWorkingDayByIndex(i).getEndBreakTime()).append("', '")
                        .append(employeeId.toString()).append("'),");
            }
            stmt.executeQuery(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteWorkingDaysByEmployeeId(UUID employeeId){
        try {
            stmt = con.createStatement();
            stmt.executeQuery("DELETE FROM OPTKOS.WORKINGDAY wd WHERE wd.EMPLOYEEID='" + employeeId.toString() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
