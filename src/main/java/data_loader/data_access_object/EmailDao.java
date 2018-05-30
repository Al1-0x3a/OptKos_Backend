package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Email;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDao {

    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt;

    private EmailDao() {}

    public static List<Email> getAllEmailsFromDb(){
        List<Email> emailList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.EMAIL");
            try (ResultSet rs = preparedStmt.executeQuery()) {

                emailList = new ArrayList<>();
                while (rs.next()) {
                    emailList.add(new Email(rs.getString("EMAILID"),
                            rs.getString("EMAIL"), rs.getString("PERSONID")));
                }
            }
            preparedStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailList;
    }

    public static List<Email> getEmailListByPersonId(String personId){
        List<Email> emailList = new ArrayList<>();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.EMAIL WHERE PERSONID=?");
            preparedStmt.setString(1, personId);
            try(ResultSet rs = preparedStmt.executeQuery()){
                while(rs.next()){
                    Email email = new Email(rs.getString("EMAILID"),
                            rs.getString("EMAIL"), rs.getString("PERSONID"));
                    emailList.add(email);
                }
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailList;
    }

    public static boolean createEmail(Email email){
        boolean b = false;
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.EMAIL (EMAILID, EMAIL, PERSONID) VALUES(?,?,?)");
            preparedStmt.setString(1, email.getEmailId());
            preparedStmt.setString(2,email.getEmailAddress());
            preparedStmt.setString(3, email.getPersonId());

            b = preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    public static void deleteEmailByPersonId(String personId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMAIL WHERE PERSONID =?");
            preparedStmt.setString(1, personId);
            preparedStmt.executeUpdate();
            preparedStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean deleteEmailByEmailId(String emailId){
        boolean b = false;
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMAIL WHERE EMAILID =?");
            preparedStmt.setString(1, emailId);
            b= preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean updateEmail(Email email){
        boolean result;
        try {

            preparedStmt = con.prepareStatement("UPDATE OPTKOS.EMAIL SET EMAIL=?WHERE EMAILID=?");
            preparedStmt.setString(1, email.getEmailAddress());
            preparedStmt.setString(2, email.getEmailId());
            result = preparedStmt.executeUpdate() != 0;
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }
}
