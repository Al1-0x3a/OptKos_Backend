package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Customer;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class CustomerDao {

    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Customer> customerList;

    public static List<Customer> getAllCustomFromDb(){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMER";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                Customer customer = new Customer(UUID.fromString(rs.getString("CUSTOMERID")),
                        rs.getDouble("MULTIPLIKATOR"),
                        rs.getString("ANNOTATION"),
                        rs.getString("PROBLEM").toCharArray()[0],
                        UUID.fromString(rs.getString("PERSONID")));

                customer.setCustomerCategory(CustomerCategoryDao.getCustomerCategoryById(
                        UUID.fromString(rs.getString("CUSTOMERCATEGORYID"))));
                customerList.add(customer);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }


    public static Customer getCustomerById(UUID cId){

        Customer customer = null;
        for(int i = 0; i< customerList.size(); i++){
            if(customerList != null && customerList.get(i).getCostumerId() == cId) {
                customer = customerList.get(i);
                break;
            }
        }

        if( customer == null){
            customer = getCustomerByIdFromDb(cId);
            if(customer !=null)
                customerList.add(customer);
        }
        return customer;
    }


    public static Customer getCustomerByIdFromDb(UUID cId){
        Customer customer = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMER c WHERE c.CUSTOMERID=" + cId + ";";
            ResultSet rs = stmt.executeQuery(query);

            customer = new Customer(UUID.fromString(rs.getString("CUSTOMERID")),
                    rs.getDouble("MULTIPLIKATOR"),
                    rs.getString("ANNOTATION"),
                    rs.getString("PROBLEM").toCharArray()[0],
                    UUID.fromString(rs.getString("PERSONID")));

            customer.setCustomerCategory(CustomerCategoryDao.getCustomerCategoryById(
                    UUID.fromString(rs.getString("CUSTOMERCATEGORYID"))));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

}
