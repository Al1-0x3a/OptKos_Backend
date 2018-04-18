package data_loader;

/***
 * user login data for db connection is stored here
 * everyone has to enter own data here
 * this class shouldnt be committed with user data
 */
public class DbLoginData {
    private String username = "",
    password = "";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public DbLoginData() {
    }
}
