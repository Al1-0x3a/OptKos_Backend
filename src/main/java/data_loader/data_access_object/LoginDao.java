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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    public static boolean registerLoginData(String id, String username, String hashedPw){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.LOGINDATA(LOGINDATAID, USERNAME, PASSWORD)" +
                    " VALUES (?,?,?)");
            preparedStmt.setString(1, id);
            preparedStmt.setString(2, username);
            preparedStmt.setString(3, hashedPw);

            preparedStmt.executeUpdate();
            preparedStmt.close();

        } catch (SQLException e) {
            System.err.println("Error while registering loginData");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getPasswordByUsername(String username){
        String pw = null;
        try {
            preparedStmt = con.prepareStatement("SELECT PASSWORD FROM OPTKOS.LOGINDATA WHERE USERNAME=?");
            preparedStmt.setString(1, username);

            try(ResultSet rs = preparedStmt.executeQuery()){
                rs.next();
                pw = rs.getString("PASSWORD");
            }
            preparedStmt.close();
        } catch (SQLException e) {
            System.err.println("Error while getting user password");
            e.printStackTrace();
            return null;
        }
        return pw;
    }

}
