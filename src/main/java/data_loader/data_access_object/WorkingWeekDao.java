package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.WorkingDay;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkingWeekDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static List<WorkingDay> getAllWorkingDaysFromDb(){
        List<WorkingDay> workingDayList = new ArrayList<>();

        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.WORKINGDAY");
            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()){
                workingDayList.add(new WorkingDay(rs.getString("WORKINGDAYID"),
                        rs.getTime("STARTWORK").toLocalTime(),
                        rs.getTime("ENDWORK").toLocalTime(),
                        rs.getTime("STARKBREAK").toLocalTime(),
                        rs.getTime("ENDBREAK").toLocalTime(),
                        rs.getString("EMPLOYEEID"),
                        rs.getString("DAY")

                ));
            }
            preparedStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workingDayList;
    }

    public static List<WorkingDay> getWorkingDays(String employeeId, List<WorkingDay> employeeWorkingDays) {
        List<WorkingDay> tmp = employeeWorkingDays;

        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.WORKINGDAY WHERE EMPLOYEEID =?");
            preparedStmt.setString(1, employeeId);

            try (ResultSet rs = preparedStmt.executeQuery()) {

                int i = 0;

                while (rs.next()) {
                    i = getDayIndex(rs.getString("DAY"));
                    tmp.get(i).setWorkingDayId(rs.getString("WORKINGDAYID"));
                    tmp.get(i).setStartWorkingTime(rs.getTimestamp("STARTWORK")
                            .toLocalDateTime().toLocalTime());
                    tmp.get(i).setEndWorkingTime(rs.getTimestamp("ENDWORK")
                            .toLocalDateTime().toLocalTime());
                    tmp.get(i).setStartBreakTime(rs.getTimestamp("STARKBREAK")
                            .toLocalDateTime().toLocalTime());
                    tmp.get(i).setEndBreakTime(rs.getTimestamp("ENDBREAK")
                            .toLocalDateTime().toLocalTime());
                    i++;
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;

    }

    public static int getDayIndex(String day){
        switch (day){
            case "Montag": return 0;
            case "Dienstag": return 1;
            case "Mittwoch": return 2;
            case "Donnerstag": return 3;
            case "Freitag": return 4;
            case "Samstag": return 5;
            case "Sonntag": return 6;
        }
        return -1;
    }

    public static void setWorkingWeek(List<WorkingDay> workingDays, String employeeId) {

        try {
            for (int i = 0; i < 7; i++) {
                preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.WORKINGDAY (WORKINGDAYID, DAY," +
                        "STARTWORK, ENDWORK, STARKBREAK, ENDBREAK, EMPLOYEEID) VALUES(?,?,?,?,?,?,?)");
                preparedStmt.setString(1, workingDays.get(i).getWorkingDayId());
                preparedStmt.setString(2, workingDays.get(i).getDay());
                preparedStmt.setTime(3, Time.valueOf(workingDays.get(i).getStartWorkingTime()));
                preparedStmt.setTime(4, Time.valueOf(workingDays.get(i).getEndWorkingTime()));
                preparedStmt.setTime(5, Time.valueOf(workingDays.get(i).getStartBreakTime()));
                preparedStmt.setTime(6, Time.valueOf(workingDays.get(i).getEndBreakTime()));
                preparedStmt.setString(7, employeeId);
                preparedStmt.execute();
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteWorkingDaysByEmployeeId(String employeeId) {
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.WORKINGDAY wd WHERE wd.EMPLOYEEID=?");
            preparedStmt.setString(1,employeeId);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateWorkingDay(WorkingDay workingDay){
        boolean result;
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.WORKINGDAY SET STARTWORK = ?, ENDWORK = ?," +
                    " STARKBREAK = ?, ENDBREAK = ?, EMPLOYEEID = ? WHERE WORKINGDAYID = ?");
            preparedStmt.setTime(1, Time.valueOf(workingDay.getStartWorkingTime()));
            preparedStmt.setTime(2, Time.valueOf(workingDay.getEndWorkingTime()));
            preparedStmt.setTime(3, Time.valueOf(workingDay.getStartBreakTime()));
            preparedStmt.setTime(4, Time.valueOf(workingDay.getEndBreakTime()));
            preparedStmt.setString(5, workingDay.getEmployeeId());
            preparedStmt.setString(6, workingDay.getWorkingDayId());
            result = preparedStmt.executeUpdate() != 0;
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }
}
