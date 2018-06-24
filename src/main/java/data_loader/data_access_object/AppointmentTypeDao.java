package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.AppointmentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentTypeDao {

    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private AppointmentTypeDao() {}

    public static List<AppointmentType> getAllAppointmentTypesFromDb(){
        List<AppointmentType> appointmentTypeList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.APOINTMENTTYPE");
            try (ResultSet rs = preparedStmt.executeQuery()) {

                while (rs.next()) {
                    AppointmentType appointmentType = new AppointmentType(
                            rs.getString("APOINTMENTTYPEID"),
                            rs.getString("NAME"),
                            rs.getString("DESCRIPTION"));
                    appointmentTypeList.add(appointmentType);
                }
                preparedStmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentTypeList;
    }

}
