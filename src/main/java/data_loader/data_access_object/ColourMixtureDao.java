package data_loader.data_access_object;

import data_loader.SqlConnection;
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
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.COLOURMIXTURE");
            try(ResultSet rs = preparedStmt.executeQuery()){

                while(rs.next()){
                    ColourMixture colourMixture = buildMixture(rs);
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
            colourMixture = new ColourMixture(
                    rs.getString("COLOURMIXTUREID"),
                    rs.getString("COLOURID"),
                    rs.getString("CUSTOMERID"),
                    rs.getInt("MIXINGRATIO")
            );
        } catch (SQLException e) {
            System.err.println("Error while building ColourMixture");
            e.printStackTrace();
            return null;
        }
        return colourMixture;
    }

    public static ColourMixture getColourMixtureByColourMixtureId(String colourMixtureId){
        ColourMixture colourMixture = null;
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.COLOURMIXTURE WHERE COLOURMIXTUREID=?");
            preparedStmt.setString(1, colourMixtureId);
            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    colourMixture = buildMixture(rs);
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colourMixture;
    }

    public static void createColourMixture(ColourMixture colourMixture){
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.COLOURMIXTURE (MIXINGRATIO, COLOURID," +
                    " COLOURMIXTUREID, CUSTOMERID) VALUES(?,?,?,?)");
            preparedStmt.setInt(1, colourMixture.getMixingRatio());
            preparedStmt.setString(2, colourMixture.getColourId().toString());
            preparedStmt.setString(3, colourMixture.getColourMixtureId().toString());
            preparedStmt.setString(4, colourMixture.getCustomerId().toString());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteColourMixtureByColourMixtureId(String colourMixtureId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.COLOURMIXTURE WHERE COLOURMIXTUREID=?");
            preparedStmt.setString(1, colourMixtureId.toString());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeCustomerColourByCustomerId(String colourMixtureId, int mixingRatio){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.COLOURMIXTURE SET MIXINGRATIO = ?" +
                    " WHERE COLOURMIXTUREID = ?");
            preparedStmt.setInt(1, mixingRatio);
            preparedStmt.setString(2, colourMixtureId.toString());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
