package util;

import client_api.PhoneApi;
import data_loader.data_access_object.PhoneDao;
import data_models.Customer;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class PhoneSimulator implements Observable {

    private static final String FORMAT = "%-50s";
    /*public static void main(String [] args){

        System.out.printf(FORMAT, "Phonecall Incoming...");
        PhoneApi.phoneNumber = "12345678";
        System.out.println("[" + PhoneApi.phoneNumber + "]");

    }*/

    public Customer getCallingPerson(String phoneNumber){
        return PhoneDao.getCustomerByPhoneNumber(phoneNumber);
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
