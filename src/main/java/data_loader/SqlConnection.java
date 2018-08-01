/*
 * MIT License
 *
 * Copyright (c) 2018 Michael Szostak , Ali Kaya , Johannes Börmann, Nina Leveringhaus , Andre` Rehle , Felix Eisenmann , Patrick Handreck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
                System.err.println("Couldn't connect to Database");
                if (DbLoginData.getUsername() == null || DbLoginData.getPassword() == null) {
					System.err.println("DB Credentials are null");
				}
				e.printStackTrace();
				exit(1);
			}
		}
		return connection;
	}
}