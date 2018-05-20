package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Colour;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ColourDao {
    private static Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static List<Colour> getAllColoursFromDb(){
        List<Colour> colourList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.COLOUR");
            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()){
                Colour Colour = new Colour(
                        rs.getString("COLOURID"),
                        rs.getString("BRIGHTNESS"),
                        rs.getString("HUE"),
                        rs.getString("MANUFACTURER")
                );
                colourList.add(Colour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colourList;
    }

    public static List<Colour> getColourByColourId(String colourId){
        List<Colour> colourList = new ArrayList<>();
        if(colourList == null ){
            colourList = getAllColoursFromDb();
        }
        List<Colour> tmpList = null;
        for (Colour co : colourList)
        {
            if(co.getColourId() == colourId){
                tmpList.add(co);
            }
        }
        return tmpList;
    }

    public static void createCustomerColour(Colour colour){
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.COLOUR (COLOURID, COLOURBRIGHTNESS," +
                    "COLOURHUE, MANUFACTURER) VALUES(?,?,?,?)");
            preparedStmt.setString(1, colour.getColourId().toString());
            preparedStmt.setString(2,colour.getBrightness());
            preparedStmt.setString(3, colour.getHue());
            preparedStmt.setString(4, colour.getManufacturer());
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteColourByColourId(String colourId){
        List<Colour> colourList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.COLOUR WHERE COLOURID=?");
            preparedStmt.setString(1, colourId.toString());
            preparedStmt.executeUpdate();

            for (int i = 0; i< colourList.size(); i++){
                if(colourList.get(i).getColourId() == colourId) {
                    colourList.remove(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeColourByColourId(UUID colourId, String brightness, String hue, String manufacturer){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.COLOUR SET COLOURBRIGHTNESS = ?, COLOURHUE = ?," +
                    " MANUFACTURER = ? WHERE COLOURID = ?");
            preparedStmt.setString(1, brightness);
            preparedStmt.setString(2, hue);
            preparedStmt.setString(3, manufacturer);
            preparedStmt.setString(4, colourId.toString());
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
