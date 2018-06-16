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
    public static void deleteAllColourMixturesbyCustomerId(String customerID){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.COLOURMIXTURE cm JOIN OPTKOS.COLOUR c ON c.COLOURID = cm.COLourid WHERE CUSTOMERID=?");
            preparedStmt.setString(1, customerID);
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
            sbQuery.append("'" + s + "', ");
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
