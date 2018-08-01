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
import data_models.AppointmentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentTypeDao {

    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private AppointmentTypeDao() {}

    public static AppointmentType getAppointmentTypeById(String appTId){
        AppointmentType appointmentType = null;
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.APOINTMENTTYPE WHERE APOINTMENTTYPEID=?");
            preparedStmt.setString(1, appTId);
            try(ResultSet rs = preparedStmt.executeQuery()) {
                rs.next();
                appointmentType = new AppointmentType(
                        rs.getString("APOINTMENTTYPEID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"));
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentType;
    }

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
