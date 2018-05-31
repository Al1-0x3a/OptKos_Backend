package util;

import client_api.PhoneApi;

public class PhoneSimulator {

    private static final String FORMAT = "%-50s";
    public static void main(String [] args){

        System.out.printf(FORMAT, "Phonecall Incoming...");
        PhoneApi.phoneNumber = "12345678";
        System.out.println("[" + PhoneApi.phoneNumber + "]");

    }
}
