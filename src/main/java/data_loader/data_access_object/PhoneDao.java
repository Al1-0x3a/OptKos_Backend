package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Phone;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class PhoneDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static List<Phone>phoneList;

    public static List<Phone> getAllPhonesFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PHONE";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                phoneList.add(new Phone(UUID.fromString(rs.getString("PHONEID")),
                        rs.getString("NUMBER"), rs.getString("DESCRIPTION"),
                        rs.getString("ANNOTATION"), UUID.fromString(rs.getString("PERSONID"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneList;
    }

    public static List<Phone> getListByPersonId(UUID personId){
        if(phoneList == null ){
            phoneList = getAllPhonesFromDb();
        }
        List<Phone> tmpList = null;
        for (Phone p : phoneList)
        {
            if(p.getPersonId() == personId){
                tmpList.add(p);
            }
        }
        return tmpList;
    }

}
