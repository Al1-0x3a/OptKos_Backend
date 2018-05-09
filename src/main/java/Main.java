
import client_api.AdministrativeApi;

import client_api.AppointmentApi;
import data_loader.DbLoginData;
import data_loader.SqlConnection;


import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class Main {
    private static final String success = "[Success]";
    private static final String error = "[Error]";

    public static void main (String[] args){
        System.out.println(getHeader());

        System.out.print("Launching administrative endpoint...   ");
        Endpoint.publish("http://localhost:1337/AdministrativeApi", new AdministrativeApi());
        System.out.println(success);

        System.out.print("Launching appointment endpoint...   ");
        Endpoint.publish("http://localhost:1338/AppointmentApi", new AppointmentApi());
        System.out.println(success);

        try {
            System.out.print("Testing DB2 connection...   ");
            long start = System.currentTimeMillis();
            boolean status = SqlConnection.getConnection().isValid(60);
            long end = System.currentTimeMillis();
            if (status) {
                System.out.println(success);
                System.out.printf("Initial ping took %d ms%n", (end - start));
            } else {
                System.err.println(error);
                System.exit(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
/*        DbLoginData db = new DbLoginData();
        db.setCredentials("Username", "Passwd");*/

    }

    private static String getHeader() {
        return "\n" +
                "\n" +
                "   ____          _    _  __             ____                _                     _ \n" +
                "  / __ \\        | |  | |/ /            |  _ \\              | |                   | |\n" +
                " | |  | | _ __  | |_ | ' /  ___   ___  | |_) |  __ _   ___ | | __ ___  _ __    __| |\n" +
                " | |  | || '_ \\ | __||  <  / _ \\ / __| |  _ <  / _` | / __|| |/ // _ \\| '_ \\  / _` |\n" +
                " | |__| || |_) || |_ | . \\| (_) |\\__ \\ | |_) || (_| || (__ |   <|  __/| | | || (_| |\n" +
                "  \\____/ | .__/  \\__||_|\\_\\\\___/ |___/ |____/  \\__,_| \\___||_|\\_\\\\___||_| |_| \\__,_|\n" +
                "         | |                                                                        \n" +
                "         |_|                                                                        \n" +
                "\n";
    }
}
