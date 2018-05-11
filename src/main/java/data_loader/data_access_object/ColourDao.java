package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Colour;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class ColourDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Colour> colourList;

    public static List<Colour> getAllColoursFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.COLOUR";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                Colour Colour = new Colour(
                    UUID.fromString(rs.getString("COLOURID")),
                    rs.getString("BRIGHTNESS"),
                    rs.getString("HUE"),
                    rs.getString("MANUFACTURER")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colourList;
    }

    public static List<Colour> getColourByColourId(UUID colourId){
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
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.COLOUR (COLOURID, COLOURBRIGHTNESS, COLOURHUE, MANUFACTURER) VALUES(?,?,?,?)");
            preparedStmt.setString(1, colour.getColourId().toString());
            preparedStmt.setString(2,colour.getBrightness());
            preparedStmt.setString(3, colour.getHue());
            preparedStmt.setString(4, colour.getManufacturer());
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteColourByColourId(UUID colourId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.COLOUR WHERE COLOURID=?");
            preparedStmt.setString(1, colourId.toString());

            if (preparedStmt.execute()){
                for (int i = 0; i< colourList.size(); i++){
                    if(colourList.get(i).getColourId() == colourId) {
                        colourList.remove(i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeColourByColourId(UUID colourId, String brightness, String hue, String manufacturer){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.COLOUR SET COLOURBRIGHTNESS = ?, COLOURHUE = ?, MANUFACTURER = ? WHERE COLOURID = ?;");
            preparedStmt.setString(1, brightness);
            preparedStmt.setString(2, hue);
            preparedStmt.setString(3, manufacturer);
            preparedStmt.setString(4, colourId.toString());
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
