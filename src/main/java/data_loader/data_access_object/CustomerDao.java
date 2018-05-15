package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            if (Objects.equals(customers.getCostumerId(), uuid)) {
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
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.CUSTOMER customer WHERE customer.CUSTOMERID=?");
            preparedStmt.setString(1, uuid);
            try (ResultSet rs = preparedStmt.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer(rs.getString("CUSTOMERID"),
                            rs.getDouble("MULTIPLIKATOR"),
                            rs.getString("ANNOTATION"),
                            rs.getString("PROBLEM").toCharArray()[0],
                            rs.getString("PERSONID"));

                    customer.setCustomerCategory(CustomerCategoryDao.getCustomerCategoryById(
                            rs.getString("CUSTOMERCATEGORYID")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static boolean createCustomer(Customer customer) {
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.CUSTOMER(CUSTOMERID, MULTIPLIKATOR, ANNOTATION, PROBLEM," +
                            " PERSONID, CUSTOMERCATEGORYID) VALUES(?,?,?,?,?,?)");
            preparedStmt.setString(1, customer.getCostumerId());
            preparedStmt.setDouble(2, customer.getTimefactor());
            preparedStmt.setString(3, customer.getAnnotation());
            preparedStmt.setString(4, String.valueOf(customer.isProblemCustomer()).substring(0, 1));
            preparedStmt.setString(5, customer.getPersonId());
            preparedStmt.setString(6, customer.getCustomerCategory().getCustomerCategoryId());

            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteCustomerByCustomerId(String customerId){
        try {
            preparedStmt = con.prepareStatement("DELETE FROM OPTKOS.CUSTOMER WHERE CUSTOMERID =?;");
            preparedStmt.setString(1, customerId);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
