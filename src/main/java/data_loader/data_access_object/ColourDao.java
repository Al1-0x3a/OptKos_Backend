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
            preparedStmt.close();
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
            preparedStmt.close();
            return colour;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createColour(Colour colour){
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.COLOUR (COLOURID, COLOURBRIGHTNESS," +
                    "COLOURHUE, MANUFACTURER) VALUES(?,?,?,?)");
            preparedStmt.setString(1, colour.getColourId());
            preparedStmt.setString(2,colour.getBrightness());
            preparedStmt.setString(3, colour.getHue());
            preparedStmt.setString(4, colour.getManufacturer());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bulkcreate(List<Colour> colourList){
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO OPTKOS.COLOUR (COLOURID, COLOURBRIGHTNESS, COLOURHUE, MANUFACTURER) VALUES");
        for (int i = 0; i<colourList.size(); i++){
            query.append("(?,?,?,?),");
        }
        query.deleteCharAt(query.length()-1);
        try {
            preparedStmt = con.prepareStatement(query.toString());
            int index = 1;
            for (Colour c :
                    colourList) {
                preparedStmt.setString(index++, c.getColourId());
                preparedStmt.setString(index++,c.getBrightness());
                preparedStmt.setString(index++, c.getHue());
                preparedStmt.setString(index++, c.getManufacturer());
            }
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            System.err.println("ERROR while bulkcreating colours");
            e.printStackTrace();
        }
    }

    public static void deleteColourByColourId(String colourId){
        List<Colour> colourList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.COLOUR WHERE COLOURID=?");
            preparedStmt.setString(1, colourId);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeColourByColourId(Colour colour){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.COLOUR SET COLOURBRIGHTNESS = ?, COLOURHUE = ?," +
                    " MANUFACTURER = ? WHERE COLOURID = ?");
            preparedStmt.setString(1, colour.getBrightness());
            preparedStmt.setString(2, colour.getHue());
            preparedStmt.setString(3, colour.getManufacturer());
            preparedStmt.setString(4, colour.getColourId());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public static void bulkDeleteById(List<String> colourIdList){
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("DELETE FROM OPTKOS.COLOUR WHERE COLOURID IN (");
        for (String s :
                colourIdList) {
            sbQuery.append("'").append(s).append("', ");
        }
        sbQuery.deleteCharAt(sbQuery.length()-2);
        sbQuery.append(")");

        try {
            preparedStmt = con.prepareStatement(sbQuery.toString());

            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
