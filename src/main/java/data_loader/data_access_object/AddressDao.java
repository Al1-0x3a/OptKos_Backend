package data_loader.data_access_object;
import data_loader.SqlConnection;
import data_models.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddressDao {

    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Address> addressList = new ArrayList<>();

    private AddressDao(){}

    public static List<Address> getAllAddressFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.ADDRESS";
            try (ResultSet rs = stmt.executeQuery(query)) {

                addressList = new ArrayList<>();
                while (rs.next()) {
                    addressList.add(new Address(rs.getString("ADDRESSID"),
                            rs.getString("POSTCODE"), rs.getString("CITY"),
                            rs.getString("STREET"), rs.getString("HOUSENR"),
                            rs.getString("PERSONID"),
                            rs.getString("ADDITION")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressList;
    }

    public static Address getAddressByPersonId(String personId){
        addressList = new ArrayList<>();
        if(addressList.isEmpty()){
            addressList = getAllAddressFromDb();
        }
        for (Address a : addressList)
        {
            if(a.getPersonId().equals(personId)){
                return a;
            }
        }
        return null;
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

            addressList.add(address);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAddressByPersonId(String personId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.ADDRESS WHERE PERSONID =?");
            preparedStmt.setString(1, personId);
            preparedStmt.executeUpdate();

                for (int i = 0; i< addressList.size(); i++){
                    if(Objects.equals(addressList.get(i).getPersonId(), personId)) {
                        addressList.remove(i);
                    }
            }
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }
}
