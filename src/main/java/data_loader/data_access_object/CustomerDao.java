package data_loader.data_access_object;

import data_loader.SqlConnection;
import data_models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CustomerDao {
    private static final Connection con = SqlConnection.getConnection();
    private static PreparedStatement preparedStmt, preparedStmt2;

    private CustomerDao() {
    }

    public static List<Customer> getAllCustomersFromDb() {
        long start = System.nanoTime();
        List<Customer> customerList = new ArrayList<>();
        try {
            preparedStmt=con.prepareStatement("SELECT * FROM OPTKOS.PERSON p, OPTKOS.CUSTOMER c," +
                    " OPTKOS.ADDRESS a, OPTKOS.CUSTOMERCATEGORY cc, OPTKOS.CUSTOMERCOLOUR color," +
                    " OPTKOS.COLOURMIXTURE cm WHERE p.PERSONID = c.PERSONID AND a.PERSONID = p.PERSONID AND" +
                    " c.CUSTOMERCATEGORYID=cc.CUSTOMERCATEGORYID AND c.CUSTOMERID=color.CUSTOMERID AND" +
                    " c.CUSTOMERID=cm.CUSTOMERID");
            try (ResultSet rs = preparedStmt.executeQuery()) {

                customerList = new ArrayList<>();
                String cusId = null;
                Customer customer = null;
                while (rs.next()) {

                    if (cusId != null && cusId.equals(rs.getString("CUSTOMERID"))) {
                        customer.getColourMixtureList().add(ColourMixtureDao.buildMixture(rs));
                    }
                    else{
                        cusId = rs.getString("CUSTOMERID");
                        if(customer!= null)
                            customerList.add(customer);

                        customer = buildCustomer(rs);

                        /* CustomerCategory */
                        customer.setCustomerCategory(CustomerCategoryDao.buildCustomercategory(rs));

                        /* Address */
                        Address address = AddressDao.buildAddress(rs);

                        customer.setAddress(address);
                        customer.setCustomerColour(CustomerColourDao.buildColour(rs));

                    }
                }
                customerList.add(customer);
            }
            preparedStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* Email */
        List<Email> emailList = EmailDao.getAllEmailsFromDb();
        for(Customer c:customerList){

            List<Email> filteredList = emailList.stream().filter(p -> p.getPersonId().equals( c.getPersonId()))
                    .collect(Collectors.toList());
            emailList.removeAll(filteredList);
            c.getEmailList().addAll(filteredList);
        }

        /* Phone */
        List<Phone> phoneList = PhoneDao.getAllPhonesFromDb();
        for(Customer c:customerList){

            List<Phone> filteredList = phoneList.stream().filter(p -> p.getPersonId().equals( c.getPersonId()))
                    .collect(Collectors.toList());
            phoneList.removeAll(filteredList);
            c.getPhoneList().addAll(filteredList);
        }

        System.out.println("Get all Customers from Db: " + (System.nanoTime() - start)/1e6 + " ms");
        return customerList;
    }

    public static boolean createNewCustomer(Customer customer) {
        try {
            preparedStmt = con.prepareStatement(
                    "INSERT INTO OPTKOS.PERSON (PERSONID, LASTNAME, FIRSTNAME, TITLE, SALUTATION, GENDER)" +
                            " VALUES(?,?,?,?,?,?)");


            preparedStmt.setString(1, customer.getPersonId());
            preparedStmt.setString(2, customer.getLastname());
            preparedStmt.setString(3, customer.getFirstname());
            preparedStmt.setString(4, customer.getTitle().name());
            preparedStmt.setString(5, customer.getSalutation().name());
            preparedStmt.setString(6, customer.getGender().name());

            preparedStmt2 = con.prepareStatement("INSERT INTO OPTKOS.CUSTOMER(CUSTOMERID, PERSONID," +
                    " MULTIPLIKATOR, ANNOTATION, PROBLEM, CUSTOMERCATEGORYID) VALUES(?,?,?,?,?,?)");

            preparedStmt2.setString(1, customer.getCostumerId());
            preparedStmt2.setString(2, customer.getPersonId());
            preparedStmt2.setDouble(3, customer.getTimefactor());
            preparedStmt2.setString(4, customer.getAnnotation());
            preparedStmt2.setString(5, String.valueOf(customer.isProblemCustomer()).substring(0, 1));
            preparedStmt2.setString(6, customer.getCustomerCategory().getCustomerCategoryId());

            preparedStmt.execute();
            preparedStmt2.execute();
            preparedStmt.close();
            preparedStmt2.close();

            AddressDao.createNewAddress(customer.getAddress(), customer.getPersonId());
            if (!customer.getPhoneList().isEmpty()) {
                for (int i = 0; i < customer.getPhoneList().size(); i++) {
                    customer.getPhoneList().get(i).setPersonId(customer.getPersonId());

                    PhoneDao.createPhone(customer.getPhoneList().get(i));
                }
            }
            if (!customer.getEmailList().isEmpty()) {
                for (int i = 0; i < customer.getEmailList().size(); i++) {
                    customer.getEmailList().get(i).setPersonId(customer.getPersonId());

                    EmailDao.createEmail(customer.getEmailList().get(i));
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Customer getCustomerById(String customerId) {

        Customer customer = new Customer();
        try {
            preparedStmt = con.prepareStatement("SELECT * FROM OPTKOS.PERSON p, OPTKOS.CUSTOMER c " +
                    "WHERE p.PERSONID = c.PERSONID AND c.CUSTOMERID=?");
            preparedStmt.setString(1, customerId);
            try (ResultSet rs = preparedStmt.executeQuery()) {
                if (rs.next()) {
                    // Person
                    customer.setPersonId(rs.getString("PERSONID"));
                    customer.setFirstname(rs.getString("FIRSTNAME"));
                    customer.setLastname(rs.getString("LASTNAME"));
                    customer.setTitle(Person.TITLE.valueOf(rs.getString("TITLE")));
                    customer.setSalutation(Person.SALUTATION.valueOf(rs.getString("SALUTATION")));
                    customer.setGender(Person.GENDER.valueOf(rs.getString("GENDER")));
                    // Shit
                    customer.setPhoneList(PhoneDao.getPhoneListByPersonId(customer.getPersonId()));
                    customer.setEmailList(EmailDao.getEmailListByPersonId(customer.getPersonId()));
                    customer.setAddress(AddressDao.getAddressByPersonId(customer.getPersonId()));

                    // Customer
                    customer.setCostumerId(rs.getString("CUSTOMERID"));
                    customer.setTimefactor(rs.getDouble("MULTIPLIKATOR"));
                    customer.setAnnotation(rs.getString("ANNOTATION"));
                    customer.setAnnotation(rs.getString("PROBLEM"));

                    customer.setCustomerCategory(CustomerCategoryDao.getCustomerCategoryByIdFromDb(
                            rs.getString("CUSTOMERCATEGORYID")));
                }
            }
            preparedStmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static boolean updateCustomer(Customer customer) {
        boolean result;
        try {
            // Person
            preparedStmt = con.prepareStatement("UPDATE OPTKOS.PERSON SET LASTNAME=?,FIRSTNAME=?,TITLE=?," +
                    "SALUTATION=?,GENDER=? WHERE PERSONID=?");
            preparedStmt.setString(1, customer.getLastname());
            preparedStmt.setString(2, customer.getFirstname());
            preparedStmt.setString(3, customer.getTitle().name());
            preparedStmt.setString(4, customer.getSalutation().name());
            preparedStmt.setString(5, customer.getGender().name());
            preparedStmt.setString(6, customer.getPersonId());

            // Customer
            preparedStmt2 = con.prepareStatement("UPDATE OPTKOS.CUSTOMER SET MULTIPLIKATOR=?, ANNOTATION=?, " +
                    "PROBLEM=?, CUSTOMERCATEGORYID=? WHERE PERSONID=?");
            preparedStmt2.setDouble(1, customer.getTimefactor());
            preparedStmt2.setString(2, customer.getAnnotation());
            preparedStmt2.setString(3, String.valueOf(customer.isProblemCustomer()).substring(0, 1));
            preparedStmt2.setString(4, customer.getCustomerCategory().getCustomerCategoryId());
            preparedStmt2.setString(5, customer.getPersonId());

            boolean result1 = preparedStmt.executeUpdate() != 0;
            boolean result2 = preparedStmt2.executeUpdate() != 0;
            preparedStmt.close();
            preparedStmt2.close();
            result = result1 && result2;

            // other
            EmailDao.deleteEmailByPersonId(customer.getPersonId());
            for (Email e :
                    customer.getEmailList()) {
                EmailDao.createEmail(e);
            }
            PhoneDao.deleteAllPhoneByPersonId(customer.getPersonId());
            for (Phone p :
                    customer.getPhoneList()) {
                PhoneDao.createPhone(p);
            }

            AddressDao.updateAddress(customer.getAddress());


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public static Customer buildCustomer(ResultSet rs){
        Customer customer = null;
        try {
            // Person
            customer = new Customer(rs.getString("PERSONID"));
            customer.setFirstname(rs.getString("FIRSTNAME"));
            customer.setLastname(rs.getString("LASTNAME"));
            customer.setTitle(Person.TITLE.valueOf(rs.getString("TITLE")));
            customer.setSalutation(Person.SALUTATION.valueOf(rs.getString("SALUTATION")));
            customer.setGender(Person.GENDER.valueOf(rs.getString("GENDER")));

            // Customer
            customer.setCostumerId(rs.getString("CUSTOMERID"));
            customer.setTimefactor(rs.getDouble("MULTIPLIKATOR"));
            customer.setAnnotation(rs.getString("ANNOTATION"));
            if(rs.getString("PROBLEM").charAt(0) == 't')
                customer.setProblemCustomer(true);
            else customer.setProblemCustomer(false);

        } catch (SQLException e) {
            System.err.println("Error while building Cutomer");
            e.printStackTrace();
        }
        return customer;
    }
}