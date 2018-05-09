package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<Customer> customerList = new ArrayList<>();

    private CustomerDao() {
    }

    public static List<Customer> getAllCustomFromDb() {
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMER";
            try (ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    Customer customer = new Customer(rs.getString("CUSTOMERID"),
                            rs.getDouble("MULTIPLIKATOR"),
                            rs.getString("ANNOTATION"),
                            rs.getString("PROBLEM").toCharArray()[0],
                            rs.getString("PERSONID"));

                    customer.setCustomerCategory(CustomerCategoryDao.getCustomerCategoryById(
                            rs.getString("CUSTOMERCATEGORYID")));
                    customerList.add(customer);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }


    public static Customer getCustomerById(String uuid){

        Customer customer = null;
        for (Customer customers : customerList) {
            if (customers.getCostumerId() == uuid) {
                customer = customers;
                break;
            }
        }

        if (customer == null) {
            customer = getCustomerByIdFromDb(uuid);
            if (customer != null)
                customerList.add(customer);
        }
        return customer;
    }


    public static Customer getCustomerByIdFromDb(String uuid){
        Customer customer = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMER c WHERE c.CUSTOMERID=" + uuid + ";";
            try (ResultSet rs = stmt.executeQuery(query)) {

                customer = new Customer(rs.getString("CUSTOMERID"),
                        rs.getDouble("MULTIPLIKATOR"),
                        rs.getString("ANNOTATION"),
                        rs.getString("PROBLEM").toCharArray()[0],
                        rs.getString("PERSONID"));

                customer.setCustomerCategory(CustomerCategoryDao.getCustomerCategoryById(
                        rs.getString("CUSTOMERCATEGORYID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

}
