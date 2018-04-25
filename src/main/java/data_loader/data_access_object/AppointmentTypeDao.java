package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.AppointmentType;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class AppointmentTypeDao {

    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<AppointmentType> appointmentTypeList;


    public static List<AppointmentType> getAllAppointmentTypesFromDb(){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENTTYPE";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                AppointmentType appointmentType = new AppointmentType(
                        UUID.fromString(rs.getString("APPOINTMENTTYPEID")),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"));
                appointmentTypeList.add(appointmentType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentTypeList;
    }

    public static AppointmentType getAppointmentTypeById(UUID appTId){

        AppointmentType appT = null;
        for(int i = 0; i< appointmentTypeList.size(); i++){
            if(appointmentTypeList != null && appointmentTypeList.get(i).getAppointmentTypeId() == appTId) {
                appT = appointmentTypeList.get(i);
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


    public static AppointmentType getAppointmentTypeByIdFromDb(UUID appTId){
        AppointmentType appointmentType = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.APOINTMENTTYPE at WHERE at.APPOINTMENTTYPEID=" + appTId + ";";
            ResultSet rs = stmt.executeQuery(query);

            appointmentType = new AppointmentType(
                    UUID.fromString(rs.getString("APPOINTMENTTYPEID")),
                    rs.getString("NAME"),
                    rs.getString("DESCRIPTION"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentType;
    }

}