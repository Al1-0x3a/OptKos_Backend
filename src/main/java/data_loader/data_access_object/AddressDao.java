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
import data_models.Address;

import java.sql.*;

public class AddressDao {

    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private AddressDao(){}

    public static Address getAddressByPersonId(String personId){
        Address address = null;
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.ADDRESS WHERE PERSONID=?");
            preparedStmt.setString(1, personId);
            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    address = buildAddress(rs);
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return address;
    }

    public static void createNewAddress(Address address, String personId){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.ADDRESS (ADDRESSID, POSTCODE, CITY, STREET," +
                    " HOUSENR, PERSONID, ADDITION) VALUES(?,?,?,?,?,?,?)");
            preparedStmt.setString(1, address.getAddressId());
            preparedStmt.setString(2, address.getPostcode());
            preparedStmt.setString(3, address.getCity());
            preparedStmt.setString(4, address.getStreet());
            preparedStmt.setString(5, address.getHousenr());
            preparedStmt.setString(6, personId);
            preparedStmt.setString(7, address.getAddition());
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            System.err.println("Error while creating a address");
            e.printStackTrace();
        }
    }

    public static void deleteAddressByPersonId(String personId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.ADDRESS WHERE PERSONID =?");
            preparedStmt.setString(1, personId);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        } catch (SQLException e) {
            System.err.println("Error while deleting address by peronsId");
            e.printStackTrace();
        }

    }

    public static boolean updateAddress(Address address){
        boolean result;
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.ADDRESS SET POSTCODE=?,CITY=?,STREET=?,HOUSENR=?," +
                    "ADDITION=? WHERE ADDRESSID=?");
            preparedStmt.setString(1, address.getPostcode());
            preparedStmt.setString(2, address.getCity());
            preparedStmt.setString(3, address.getStreet());
            preparedStmt.setString(4, address.getHousenr());
            preparedStmt.setString(5, address.getAddition());
            preparedStmt.setString(6, address.getAddressId());
            result = preparedStmt.executeUpdate() != 0;
            preparedStmt.close();
        } catch (SQLException e) {
            System.err.println("Error while updating address");
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public static Address buildAddress(ResultSet rs){
        Address address = null;
        try {
            address = new Address(rs.getString("ADDRESSID"),
                    rs.getString("POSTCODE"), rs.getString("CITY"),
                    rs.getString("STREET"), rs.getString("HOUSENR"),
                    rs.getString("PERSONID"),
                    rs.getString("ADDITION"));
        } catch (SQLException e) {
            System.err.println("Error while building address");
            e.printStackTrace();
        }

        return address;
    }
}
