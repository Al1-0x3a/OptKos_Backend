package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.AppointmentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentTypeDao {

    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<AppointmentType> appointmentTypeList = new ArrayList<>();

    private AppointmentTypeDao() {}

    public static List<AppointmentType> getAllAppointmentTypesFromDb(){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENTTYPE";
            try (ResultSet rs = stmt.executeQuery(query)) {

            while(rs.next()){
                AppointmentType appointmentType = new AppointmentType(
                        rs.getString("APPOINTMENTTYPEID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"));
                appointmentTypeList.add(appointmentType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentTypeList;
    }

    public static AppointmentType getAppointmentTypeById(String appTId){

        AppointmentType appT = null;
        for (AppointmentType appointmentTypes : appointmentTypeList) {
            if (appointmentTypes.getAppointmentTypeId() == appTId) {
                appT = appointmentTypes;
                break;
            }
        }

        if( appT == null){
            appT = getAppointmentTypeByIdFromDb(appTId);
            if(appT !=null)
                appointmentTypeList.add(appT);
        }
        return appT;
    }


    public static AppointmentType getAppointmentTypeByIdFromDb(String appTId){
        AppointmentType appointmentType = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENTTYPE at WHERE at.APPOINTMENTTYPEID=" + appTId + ";";
            try (ResultSet rs = stmt.executeQuery(query)) {

            appointmentType = new AppointmentType(
                    rs.getString("APPOINTMENTTYPEID"),
                    rs.getString("NAME"),
                    rs.getString("DESCRIPTION"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentType;
    }

}
