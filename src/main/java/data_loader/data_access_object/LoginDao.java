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
