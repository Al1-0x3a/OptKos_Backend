import client_api.AdministrativeApi;
import client_api.AppointmentApi;
import client_api.PhoneApi;
import client_api.StatisticApi;
import data_loader.SqlConnection;
import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class Main {
    private static final String SUCCESS = "[Success]";
    private static final String ERROR = "[Error]";
    private static final String FORMAT = "%-50s";

    public static void main (String[] args){
        System.out.println(getHeader());

        System.out.printf(FORMAT, "Launching administrative endpoint...");
        Endpoint.publish("http://localhost:1337/AdministrativeApi", new AdministrativeApi());
        System.out.println(SUCCESS);

        System.out.printf(FORMAT, "Launching appointment endpoint...");
        Endpoint.publish("http://localhost:1338/AppointmentApi", new AppointmentApi());
        System.out.println(SUCCESS);

        System.out.printf(FORMAT, "Launching Phone endpoint...");
        Endpoint.publish("http://localhost:1340/PhoneApi", new PhoneApi());
        System.out.println(SUCCESS);
      
        System.out.printf(FORMAT, "Launching statistics endpoint...");
        Endpoint.publish("http://localhost:1339/StatisticApi", new StatisticApi());
        System.out.println(SUCCESS);

        try {
            System.out.printf(FORMAT, "Testing DB2 connection...");
            long start = System.currentTimeMillis();
            boolean status = SqlConnection.getConnection().isValid(30);
            long end = System.currentTimeMillis();
            if (status) {
                System.out.println(SUCCESS);
                System.out.printf("Initial ping took %d ms%n", (end - start));Make fixes to make phoneSimulator work correctly
            } else {
                System.err.println(ERROR);
                System.exit(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf(FORMAT, "Closing Databse Connection...");

                if (SqlConnection.getConnection() != null) {
                    try {
                        SqlConnection.getConnection().close();
                    } catch (Exception e) {
                        System.out.println(ERROR);
                        e.printStackTrace();
                    }
                    System.out.println(SUCCESS);
                }
            }
        }){});

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
