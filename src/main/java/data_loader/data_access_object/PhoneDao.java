package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Phone;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class PhoneDao {
    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
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

    public static void createPhone(Phone phone){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.PHONE (PHONEID, NUMBER, DESCRIPTION, ANNOTATION, PERSONID) VALUES(?,?,?,?,?)");
            preparedStmt.setString(1, phone.getPhoneId().toString());
            preparedStmt.setString(2, phone.getNumber());
            preparedStmt.setString(3, phone.getDescription());
            preparedStmt.setString(4, phone.getAnnotation());
            preparedStmt.setString(5, phone.getPersonId().toString());
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deletePhoneByPersonId(UUID personId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.PHONE WHERE PERSONID =?");
            preparedStmt.setString(1, personId.toString());

            if (preparedStmt.execute()){
                for (int i = 0; i< phoneList.size(); i++){
                    if(phoneList.get(i).getPersonId() == personId) {
                        phoneList.remove(i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
