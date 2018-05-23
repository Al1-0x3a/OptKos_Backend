package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Colour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColourDao {
    private static Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static List<Colour> getAllColoursFromDb(){
        List<Colour> colourList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.COLOUR");
            try(ResultSet rs = preparedStmt.executeQuery()){

                while(rs.next()){
                    Colour Colour = new Colour(
                            rs.getString("COLOURID"),
                            rs.getString("BRIGHTNESS"),
                            rs.getString("HUE"),
                            rs.getString("MANUFACTURER")
                    );
                    colourList.add(Colour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colourList;
    }

    public static Colour getColourByColourId(String colourId){
        try {
            Colour colour = null;
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.COLOUR WHERE COLOURID=?");
            preparedStmt.setString(1, colourId);
            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    colour = new Colour(
                            rs.getString("COLOURID"),
                            rs.getString("BRIGHTNESS"),
                            rs.getString("HUE"),
                            rs.getString("MANUFACTURER")
                    );
                }
            }
            return colour;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeColourByColourId(String colourId, String brightness, String hue, String manufacturer){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.COLOUR SET COLOURBRIGHTNESS = ?, COLOURHUE = ?," +
                    " MANUFACTURER = ? WHERE COLOURID = ?");
            preparedStmt.setString(1, brightness);
            preparedStmt.setString(2, hue);
            preparedStmt.setString(3, manufacturer);
            preparedStmt.setString(4, colourId);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
