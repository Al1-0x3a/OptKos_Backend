
import client_api.AdministrativeApi;

import client_api.AppointmentApi;


import javax.xml.ws.Endpoint;

public class Main {
    public static void main (String[] args){
        System.out.println("Launching administrative endpoint...");
        Endpoint.publish("http://localhost:1337/AdministrativeApi", new AdministrativeApi());
        System.out.println("Done");

        System.out.println("Launching appointment endpoint...");
        Endpoint.publish("http://localhost:1338/AppointmentApi", new AppointmentApi());
        System.out.println("Done");


    }
}
