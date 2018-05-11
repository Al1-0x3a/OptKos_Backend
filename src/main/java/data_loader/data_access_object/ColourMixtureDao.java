package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.ColourMixture;

import java.sql.*;
import java.util.List;

public class ColourMixtureDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<ColourMixture> colourMixtureList;

    public static List<ColourMixture> getAllColourMixturesFromDb(){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.COLOURMIXTURE";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                ColourMixture colourMixture = new ColourMixture(
                        rs.getString("COLOURMIXTUREID"),
                        rs.getString("COLOURID"),
                        rs.getString("CUSTOMERID"),
                        rs.getInt("MIXINGRATIO")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colourMixtureList;
    }

    public static List<ColourMixture> getColourMixtureByColourMixtureId(String colourMixtureId){
        if(colourMixtureList == null ){
            colourMixtureList = getAllColourMixturesFromDb();
        }
        List<ColourMixture> tmpList = null;
        for (ColourMixture comi : colourMixtureList)
        {
            if(comi.getCustomerId() == colourMixtureId){
                tmpList.add(comi);
            }
        }
        return tmpList;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteColourMixtureByColourMixtureId(String colourMixtureId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.COLOURMIXTURE WHERE COLOURMIXTUREID=?");
            preparedStmt.setString(1, colourMixtureId.toString());
            preparedStmt.executeUpdate();

            for (int i = 0; i< colourMixtureList.size(); i++){
                if(colourMixtureList.get(i).getColourMixtureId() == colourMixtureId) {
                    colourMixtureList.remove(i);
                }
            }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
