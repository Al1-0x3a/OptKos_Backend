package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.CustomerCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerCategoryDao {

    private static final Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<CustomerCategory> customerCategoryList = new ArrayList<>();

    private CustomerCategoryDao() {
    }

    public static List<CustomerCategory> getAllCustomerCategoriesFromDb() {
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMERCATEGORY";
            try (ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    CustomerCategory customerCategory = new CustomerCategory(
                            rs.getString("CUSTOMERCATEGORYID"),
                            rs.getString("NAME"),
                            rs.getString("DESCRIPTION"));
                    customerCategoryList.add(customerCategory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCategoryList;
    }

    public static CustomerCategory getCustomerCategoryById(String uuid){

        CustomerCategory customerCategory = null;
        for (CustomerCategory customerCategories : customerCategoryList) {
            if (customerCategories.getCustomerCategoryId() == uuid) {
                customerCategory = customerCategories;
                break;
            }
        }

        if (customerCategory == null) {
            customerCategory = getCustomerCategoryByIdFromDb(uuid);
            if (customerCategory != null)
                customerCategoryList.add(customerCategory);
        }
        return customerCategory;
    }


    public static CustomerCategory getCustomerCategoryByIdFromDb(String uuid){
        CustomerCategory customerCategory = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMERCATEGORY customerCategory WHERE customerCategory.CUSTOMERCATEGORYID=" + uuid + ";";
            try (ResultSet rs = stmt.executeQuery(query)) {

                customerCategory = new CustomerCategory(
                        rs.getString("CUSTOMERCATEGORYID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerCategory;
    }
}
