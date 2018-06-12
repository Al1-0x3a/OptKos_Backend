package manager;

import data_loader.data_access_object.CustomerDao;
import data_models.Customer;
import data_models.Phone;

public class PhoneManager {

    public Customer getCustomerByPhoneNumber(String phoneNumber){
        Customer customer = null;
        customer = CustomerDao.getCustomerByPhoneNumber(phoneNumber);
        if(customer.getPersonId() == null){
            System.out.println("Unknown Caller " + phoneNumber);
            Customer newCustomer = new Customer();

            /*Setting the PhoneNumber*/
            Phone phone = new Phone();
            phone.setPersonId(newCustomer.getPersonId());
            phone.setNumber(phoneNumber);
            newCustomer.getPhoneList().add(phone);

            return newCustomer;
        }else{
            System.out.println("Incoming call from: " + customer.getFirstname() + " " + customer.getLastname());
            return customer;
        }
    }
}
