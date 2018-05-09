package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Phone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PhoneDao {
    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Phone> phoneList = new ArrayList<>();

    private PhoneDao() {
    }

    public static List<Phone> getAllPhonesFromDb() {

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.PHONE";
            try (ResultSet rs = stmt.executeQuery(query)) {

            phoneList = new ArrayList<>();
            while(rs.next()) {
                phoneList.add(new Phone(rs.getString("PHONEID"),
                        rs.getString("NUMBER"), rs.getString("DESCRIPTION"),
                        rs.getString("ANNOTATION"), rs.getString("PERSONID")));
            }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phoneList;
    }

    public static List<Phone> getListByPersonId(String personId){
        if(phoneList.size() == 0 ){
            phoneList = getAllPhonesFromDb();
        }
        List<Phone> tmpList = new ArrayList<>();
        for (Phone p : phoneList)
        {
            if(p.getPersonId().equals(personId)){
                tmpList.add(p);
            }
        }
        return tmpList;
    }

    public static boolean createPhone(Phone phone) {
        boolean result = false;
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.PHONE (PHONEID, NUMBER, DESCRIPTION, ANNOTATION, PERSONID) VALUES(?,?,?,?,?)");
            preparedStmt.setString(1, phone.getPhoneId());
            preparedStmt.setString(2, phone.getNumber());
            preparedStmt.setString(3, phone.getDescription());
            preparedStmt.setString(4, phone.getAnnotation());
            preparedStmt.setString(5, phone.getPersonId());
            result = preparedStmt.execute();
            phoneList.add(phone);
        } catch (SQLException e) {
            System.err.println("An Error occured while writing an Phone into the DB");
            e.printStackTrace();
        }
        return result;
    }


    public static boolean deletePhoneByPhoneId(String phoneId){
        boolean b = false;
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.PHONE WHERE PHONEID =?");
            preparedStmt.setString(1, phoneId);

            b = preparedStmt.execute();
            if (b){
                for (int i = 0; i< phoneList.size(); i++){
                    if(phoneList.get(i).getPersonId() == phoneId) {
                        phoneList.remove(i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static boolean deleteAllPhoneByPersonId(String personId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.PHONE WHERE PERSONID =?");
            preparedStmt.setString(1, personId);

            preparedStmt.executeUpdate();
                for (int i = 0; i< phoneList.size(); i++){
                    if(phoneList.get(i).getPersonId() == personId) {
                        phoneList.remove(i);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        phoneList = new ArrayList<>();
        return true;
    }

    public static boolean updatePhone(Phone phone){
        boolean result;
        try {
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.PHONE SET NUMBER=?, DESCRIPTION=?," +
                    " ANNOTATION=? WHERE PHONEID=?");
            preparedStmt.setString(1, phone.getNumber());
            preparedStmt.setString(2, phone.getDescription());
            preparedStmt.setString(3, phone.getAnnotation());
            preparedStmt.setString(4, phone.getPhoneId());
            result = preparedStmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }
}
