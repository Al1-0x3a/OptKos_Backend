package data_loader.data_access_object;
import data_loader.SqlConnection;
        import data_models.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
        import java.util.UUID;

public class AddressDao {

    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Address> addressList = new ArrayList<>();

    public static List<Address> getAllAddressFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.ADDRESS";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                addressList.add(new Address(UUID.fromString(rs.getString("ADDRESSID")),
                        rs.getString("POSTCODE"), rs.getString("CITY"),
                        rs.getString("STREET"), rs.getString("HOUSENR"),
                        UUID.fromString(rs.getString("PERSONID"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressList;
    }

    public static Address getAddressByPersonId(UUID personId){
        if(addressList.size() == 0 ){
            addressList = getAllAddressFromDb();
        }
        for (Address a : addressList)
        {
            System.out.println(a.getPersonId().toString());
            if(a.getPersonId().equals(personId)){
                return a;
            }
        }
        return null;
    }

    public static void createNewAddress(Address address, UUID personId){
        try {
            preparedStmt = con.prepareStatement("INSERT INTO OPTKOS.ADDRESS (ADDRESSID, POSTCODE, CITY, STREET, HOUSENR, PERSONID) VALUES(?,?,?,?,?,?)");
            preparedStmt.setString(1, address.getAddressId().toString());
            preparedStmt.setString(2, address.getPostcode());
            preparedStmt.setString(3, address.getCity());
            preparedStmt.setString(4, address.getStreet());
            preparedStmt.setString(5, address.getHousenr());
            preparedStmt.setString(6, personId.toString());
            preparedStmt.execute();

            addressList.add(address);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAddressByPersonId(UUID personId){

        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.ADDRESS WHERE PERSONID =?");
            preparedStmt.setString(1, personId.toString());

            if (preparedStmt.execute()){
                for (int i = 0; i< addressList.size(); i++){
                    if(addressList.get(i).getPersonId() == personId) {
                        addressList.remove(i);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
