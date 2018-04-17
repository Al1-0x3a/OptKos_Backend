import data_loader.SqlConnection;

import java.sql.Connection;

public class Main {
    public static void main (String[] args){
        Connection con = SqlConnection.getConnection();
       System.out.println("Hallo Welt");
    }
}
