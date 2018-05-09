package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Email;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDao {

    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Email> emailList = new ArrayList<>();

    private EmailDao() {}

    public static List<Email> getAllEmailsFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.EMAIL";
            try (ResultSet rs = stmt.executeQuery(query)) {

                emailList = new ArrayList<>();
                while (rs.next()) {
                    emailList.add(new Email(rs.getString("EMAILID"),
                            rs.getString("EMAIL"), rs.getString("PERSONID")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailList;
    }

    public static List<Email> getEmailListByPersonId(String personId){
        if(emailList.isEmpty()) {
            emailList = getAllEmailsFromDb();
        }
        List<Email> tmpList = new ArrayList<>();
        for (Email e : emailList)
        {
            if(e.getPersonId().equals(personId)){
                tmpList.add(e);
            }
        }
        return tmpList;
    }
    public static boolean createEmail(Email email){
        boolean b = false;
        try {
            preparedStmt= con.prepareStatement("INSERT INTO OPTKOS.EMAIL (EMAILID, EMAIL, PERSONID) VALUES(?,?,?)");
            preparedStmt.setString(1, email.getEmailId());
            preparedStmt.setString(2,email.getEmail());
            preparedStmt.setString(3, email.getPersonId());

            b = preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        emailList.add(email);
        return b;
    }


    public static void deleteEmailByPersonId(String personId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.EMAIL WHERE PERSONID =?");
            preparedStmt.setString(1, personId);
            preparedStmt.executeUpdate();

                for (int i = 0; i< emailList.size(); i++){
                    if(emailList.get(i).getPersonId().equals(personId)) {
                        emailList.remove(i);
                }
            }
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
            preparedStmt.setString(2, email.getEmailId());
            preparedStmt.executeUpdate();
            b =true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }
}
