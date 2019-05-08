package io.vincentpalazzo.gradledatabase.exstension;


/**
 * @author https://github.com/vincenzopalazzo
 */
public class GradleDatabaseExstension {

    private String url;
    private String username;
    private String password;
    private String nameDatabase;
    private String driver;
    private String nameJar;

    public String getNameJar() {
        return nameJar;
    }

    public void setNameJar(String nameJar) {
        this.nameJar = nameJar;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameDatabase() {
        return nameDatabase;
    }
    public void setNameDatabase(String nameDatabase) {
        this.nameDatabase = nameDatabase;
    }

    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
}
