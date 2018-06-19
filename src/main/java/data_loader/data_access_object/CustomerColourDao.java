package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.CustomerColour;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CustomerColourDao {
    private static Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static List<CustomerColour> getAllCustomerColoursFromDb(){
        List<CustomerColour> customerColourList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.CUSTOMERCOLOUR");
            try(ResultSet rs = preparedStmt.executeQuery()) {

                while (rs.next()) {
                    CustomerColour customerColour = buildColour(rs);
                    customerColourList.add(customerColour);
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerColourList;
    }

    public static CustomerColour buildColour(ResultSet rs){
        CustomerColour cc = null;
        try {
            cc = new CustomerColour(
                    rs.getString("CUSTOMERCOLOURID"),
                    rs.getInt("CONTENTWHITE"),
                    rs.getString("CUSTOMERID"),
                    rs.getInt("EXPOSURETIME"),
                    rs.getString("NATURALCOLOUR"),
                    rs.getDouble("OXIDATION"),
                    rs.getString("RESULT")
            );
        } catch (SQLException e) {
            System.err.println("Error while building CustomerColour");
            e.printStackTrace();
            return null;
        }
        return cc;
    }

    public static List<CustomerColour> getCustomerColourByCustomerId(String customerId){
        List<CustomerColour> customerColourList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.CUSTOMERCOLOUR WHERE CUSTOMERID=?");
            preparedStmt.setString(1, customerId);
            try(ResultSet rs = preparedStmt.executeQuery()){
                while (rs.next()){
                    CustomerColour customerColour = buildColour(rs);
                    customerColourList.add(customerColour);
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerColourList;
    }

    public static void createCustomerColour(CustomerColour customerColour){
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.CUSTOMERCOLOUR (NATURALCOLOUR, OXIDATION," +
                    " CONTENTWHITE, EXPOSURETIME, RESULT, CUSTOMERCOLOURID, CUSTOMERID) VALUES(?,?,?,?,?,?,?)");
            preparedStmt.setString(1, customerColour.getNatural());
            preparedStmt.setDouble(2,customerColour.getOxidation());
            preparedStmt.setDouble(3, customerColour.getContentWhite());
            preparedStmt.setInt(4, customerColour.getExposureTime());
            preparedStmt.setString(5, customerColour.getResult());
            preparedStmt.setString(6, customerColour.getCustomerColourId());
            preparedStmt.setString(7, customerColour.getCustomerId());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomerColourByCustomerId(String customerId){
        List<CustomerColour> customerColourList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.CUSTOMERCOLOUR WHERE CUSTOMERID=?");
            preparedStmt.setString(1, customerId);
            preparedStmt.executeUpdate();

            for (int i = 0; i< customerColourList.size(); i++){
                if(customerColourList.get(i).getCustomerId().equals(customerId)) {
                    customerColourList.remove(i);
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeCustomerColourByCustomerId(CustomerColour customerColour){
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.CUSTOMERCOLOUR SET NATURALCOLOUR =?, " +
                    "OXIDATION =?, CONTENTWHITE =?, EXPOSURETIME = ?," +
                    " Result =? WHERE CUSTOMERID=?");
            preparedStmt.setString(1, customerColour.getNatural());
            preparedStmt.setDouble(2, customerColour.getOxidation());
            preparedStmt.setInt(3, customerColour.getContentWhite());
            preparedStmt.setInt(4, customerColour.getExposureTime());
            preparedStmt.setString(5, customerColour.getResult());
            preparedStmt.setString(6, customerColour.getCustomerId());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
