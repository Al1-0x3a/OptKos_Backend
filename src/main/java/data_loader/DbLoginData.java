package data_loader;

import java.util.prefs.Preferences;

public class DbLoginData {
    private static Preferences preferences = Preferences.userNodeForPackage(DbLoginData.class);

    private DbLoginData() {
    }

    public static void setCredentials(String username, String password) {
        preferences.put("db_username", username);
        preferences.put("db_password", password);
    }

    public static String getUsername() {
        return preferences.get("db_username", null);
    }

    public static String getPassword() {
        return preferences.get("db_password", null);
    }
}
