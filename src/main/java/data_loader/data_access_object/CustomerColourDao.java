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
