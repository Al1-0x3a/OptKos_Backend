package data_loader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

	private static Connection connection = null;
	private static String url = "jdbc:db2://if-db2.hs-kempten.de:50000/ERPP";
	private static DbLoginData loginData = new DbLoginData();

	protected SqlConnection(){
	}

	public static Connection getConnection(){
		if (connection == null){
			try {
				connection = DriverManager.getConnection(url, loginData.getUsername(), loginData.getPassword());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}