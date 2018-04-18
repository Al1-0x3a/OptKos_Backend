package data_loader.data_access_object;
import data_loader.SqlConnection;
        import data_models.Address;

        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.List;
        import java.util.UUID;

public class AddressDao {

    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static List<Address> addressList;

    public static List<Address> getAllAddressFromDb(){

        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.ADRESS";
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
        if(addressList == null ){
            addressList = getAllAddressFromDb();
        }
        for (Address a : addressList)
        {
            if(a.getPersonId() == personId){
                return a;
            }
        }
        return null;
    }

    public static void createNewAddress(Address address){
        try {
            stmt = con.createStatement();
            String query = "INSERT INTO ADRESS (ADRESSID, POSTCODE, CITY, STREET, HOUSENR, PERSONID)" +
            "VALUES ('" + address.getAddressId() + "', '" + address.getPostcode() + "', '" + address.getCity()
            + "', '" + address.getStreet() + "', '" + address.getHousenr() + "', '" + address.getPersonId() + "');";
            stmt.execute(query);

            addressList.add(address);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
