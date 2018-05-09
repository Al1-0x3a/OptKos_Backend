package data_loader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.exit;

public class SqlConnection {

	private static Connection connection = null;
	private static final String url = "jdbc:db2://if-db2.hs-kempten.de:50000/ERPP:retrieveMessagesFromServerOnGetMessage=true;";

	protected SqlConnection(){
	}

	public static Connection getConnection(){
		if (connection == null){
			try {
				connection = DriverManager.getConnection(url, DbLoginData.getUsername(), DbLoginData.getPassword());
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Couldn't connect to Database\n exiting...");
				exit(1);
			}
		}
		return connection;
	}
}