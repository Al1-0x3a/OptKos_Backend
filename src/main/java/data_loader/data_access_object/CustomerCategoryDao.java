package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.CustomerCategory;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class CustomerCategoryDao {

    private static Connection con = SqlConnection.getConnection();
    private static Statement stmt;
    private static PreparedStatement preparedStmt;
    private static List<CustomerCategory> customerCategoryList;


    public static List<CustomerCategory> getAllCustomerCategoriesFromDb(){
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMERCATEGORY";
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                CustomerCategory customerCategory = new CustomerCategory(
                        rs.getString("CUSTOMERCATEGORYID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"));
                customerCategoryList.add(customerCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCategoryList;
    }

    public static CustomerCategory getCustomerCategoryById(String ccId){

        CustomerCategory cc = null;
        for(int i = 0; i< customerCategoryList.size(); i++){
            if(customerCategoryList != null && customerCategoryList.get(i).getCustomerCategoryId() == ccId) {
                cc = customerCategoryList.get(i);
                break;
            }
        }

        if( cc == null){
            cc = getCustomerCategoryByIdFromDb(ccId);
            if(cc !=null)
                customerCategoryList.add(cc);
        }
        return cc;
    }


    public static CustomerCategory getCustomerCategoryByIdFromDb(String ccId){
        CustomerCategory customerCategory = null;
        try {
            stmt = con.createStatement();
            String query = "SELECT * FROM OPTKOS.CUSTOMERCATEGORY cc WHERE cc.CUSTOMERCATEGORYID=" + ccId + ";";
            ResultSet rs = stmt.executeQuery(query);

            customerCategory = new CustomerCategory(
                    rs.getString("CUSTOMERCATEGORYID"),
                    rs.getString("NAME"),
                    rs.getString("DESCRIPTION"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerCategory;
    }
}
