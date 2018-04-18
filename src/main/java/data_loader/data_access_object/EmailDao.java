package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Email;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class EmailDao {

    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
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
    public static void createEmail(Email email){
        try {
            stmt = con.createStatement();
            String query = "INSERT INTO EMAIL (EMAILID, EMAIL, PERSONID) VALUES ('" + email.getEmailId() + "', '" +
                    email.getEmail() + "', '" + email.getPersonId() + "');";
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
