package data_loader;

import java.util.prefs.Preferences;

public class DbLoginData {

    public DbLoginData() {
    }
    Preferences preferences =
            Preferences.userNodeForPackage(DbLoginData.class);

    public void setCredentials(String username, String password) {
        preferences.put("db_username", username);
        preferences.put("db_password", password);
    }

    public String getUsername() {
        return preferences.get("db_username", null);
    }

    public String getPassword() {
        return preferences.get("db_password", null);
    }
}
