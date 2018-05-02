package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Email;
import data_models.Phone;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class EmailDao {

    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Email> emailList;

    public static List<Email> getAllEmailsFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.EMAIL";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                emailList.add(new Email(UUID.fromString(rs.getString("EMAILID")),
                        rs.getString("EMAIL"), UUID.fromString(rs.getString("PERSONID"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailList;
    }

    public static List<Email> getEmailListByPersonId(UUID personId){
        if(emailList == null ){
            emailList = getAllEmailsFromDb();
        }
        List<Email> tmpList = null;
        for (Email e : emailList)
        {
            if(e.getPersonId() == personId){
                tmpList.add(e);
            }
        }
        return tmpList;
    }
    public static boolean createEmail(Email email){
        boolean b = false;
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.EMAIL (EMAILID, EMAIL, PERSONID) VALUES(?,?,?)");
            preparedStmt.setString(1, email.getEmailId().toString());
            preparedStmt.setString(2,email.getEmail());
            preparedStmt.setString(3, email.getPersonId().toString());

            b = preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }


    public static void deleteEmailByPersonId(UUID personId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMAIL WHERE PERSONID =?");
            preparedStmt.setString(1, personId.toString());

            if (preparedStmt.execute()){
                for (int i = 0; i< emailList.size(); i++){
                    if(emailList.get(i).getPersonId() == personId) {
                        emailList.remove(i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean deleteEmailByEmailId(UUID emailId){
        boolean b = false;
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMAIL WHERE EMAILID =?");
            preparedStmt.setString(1, emailId.toString());

            b= preparedStmt.execute();
            if (b){
                for (int i = 0; i< emailList.size(); i++){
                    if(emailList.get(i).getPersonId() == emailId) {
                        emailList.remove(i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean updateEmail(Email email){
        boolean b = false;
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.EMAIL SET EMAIL=?WHERE EMAILID=?");
            preparedStmt.setString(1, email.getEmail());
            preparedStmt.setString(2, email.getEmailId().toString());
            b = preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }
}
