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
import data_models.ColourMixture;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class ColourMixtureDao {
    private static Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static List<ColourMixture> getAllColourMixturesFromDb(){
        List<ColourMixture> colourMixtureList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.COLOURMIXTURE cm, OPTKOS.COLOUR c " +
                    "WHERE cm.COLOURID = c.COLOURID");
            try(ResultSet rs = preparedStmt.executeQuery()){

                while(rs.next()){
                    colourMixtureList.add(buildMixture(rs));
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colourMixtureList;
    }

    public static ColourMixture buildMixture(ResultSet rs){
        ColourMixture colourMixture = null;
        try {
            Colour c = new Colour(rs.getString(
                    "COLOURID"
            ), rs.getString("COLOURBRIGHTNESS"), rs.getString("COLOURHUE"),
                    rs.getString("MANUFACTURER"));
            colourMixture = new ColourMixture(
                    rs.getString("COLOURMIXTUREID"),
                    rs.getString("COLOURID"),
                    rs.getString("CUSTOMERID"),
                    rs.getInt("MIXINGRATIO"),
                    c
            );
        } catch (SQLException e) {
            System.err.println("Error while building ColourMixture");
            e.printStackTrace();
            return null;
        }
        return colourMixture;
    }

    public static void createColourMixture(ColourMixture colourMixture){
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.COLOURMIXTURE (MIXINGRATIO, COLOURID," +
                    " COLOURMIXTUREID, CUSTOMERID) VALUES(?,?,?,?)");
            preparedStmt.setInt(1, colourMixture.getMixingRatio());
            preparedStmt.setString(2, colourMixture.getColour().getColourId());
            preparedStmt.setString(3, colourMixture.getColourMixtureId());
            preparedStmt.setString(4, colourMixture.getCustomerId());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bulkCreate(List<ColourMixture> cmList){
        if(cmList.size()>0) {
            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO OPTKOS.COLOURMIXTURE (MIXINGRATIO, COLOURID, COLOURMIXTUREID, CUSTOMERID) VALUES");
            for (int i = 0; i < cmList.size(); i++) {
                query.append("(?,?,?,?),");
            }
            query.deleteCharAt(query.length() - 1);
            try {
                preparedStmt = con.prepareStatement(query.toString());
                int index = 1;
                for (ColourMixture cm :
                        cmList) {
                    preparedStmt.setInt(index++, cm.getMixingRatio());
                    preparedStmt.setString(index++, cm.getColour().getColourId());
                    preparedStmt.setString(index++, cm.getColourMixtureId());
                    preparedStmt.setString(index++, cm.getCustomerId());
                }
                preparedStmt.execute();
                preparedStmt.close();
            } catch (SQLException e) {
                System.err.println("ERROR while bulkcreating colourMixtures");
                e.printStackTrace();
            }
        }
    }

    public static void deleteColourMixtureByColourMixtureId(String colourMixtureId, String colourId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.COLOURMIXTURE WHERE COLOURID=?");
            preparedStmt.setString(1, colourId);
            preparedStmt.executeUpdate();
            preparedStmt.close();

            ColourDao.deleteColourByColourId(colourId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeCustomerColourByCustomerId(ColourMixture colourMixture){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.COLOURMIXTURE SET MIXINGRATIO = ?" +
                    " WHERE COLOURMIXTUREID = ?");
            preparedStmt.setInt(1, colourMixture.getMixingRatio());
            preparedStmt.setString(2, colourMixture.getColourMixtureId());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public static void bulkDeleteById(List<String> colorIds){
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("DELETE FROM OPTKOS.COLOURMIXTURE WHERE COLOURID IN (");
        for (String s :
                colorIds) {
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

    public static void addNewMixtures(List<ColourMixture> colourMixturesList){
        ArrayList<String> colorIds = new ArrayList<>();
        ArrayList<Colour> colors = new ArrayList<>();

        /*Get IDs*/
        for(ColourMixture c: colourMixturesList) {
            colorIds.add(c.getColour().getColourId());
            colors.add(c.getColour());
        }

        /*Delete all*/
        bulkDeleteById(colorIds);
        ColourDao.bulkDeleteById(colorIds);


        /*Create colours*/
        ColourDao.bulkcreate(colors);

        /*Create coloursMixtures*/
        bulkCreate(colourMixturesList);
    }
}
