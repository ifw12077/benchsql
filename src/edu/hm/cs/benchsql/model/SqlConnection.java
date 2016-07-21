package edu.hm.cs.benchsql.model;

public class SqlConnection {

    private String connectionIp;
    private String connectionPort;
    private String connectionInstance;
    private String connectionDatabase;
    private String connectionUser;
    private String connectionPassword;

    public SqlConnection() {
        this.initializeFields();
    }

    public String getConnectionDatabase() {
        return this.connectionDatabase;
    }

    public String getConnectionInstance() {
        return this.connectionInstance;
    }

    public String getConnectionIp() {
        return this.connectionIp;
    }

    public String getConnectionPassword() {
        return this.connectionPassword;
    }

    public String getConnectionPort() {
        return this.connectionPort;
    }

    public String getConnectionUser() {
        return this.connectionUser;
    }

    private void initializeFields() {
        this.setConnectionIp("");
        this.setConnectionPort("");
        this.setConnectionInstance("");
        this.setConnectionDatabase("");
        this.setConnectionUser("");
        this.setConnectionPassword("");
    }

    public void setConnectionDatabase(final String connectionDatabase) {
        this.connectionDatabase = connectionDatabase;
    }

    public void setConnectionInstance(final String connectionInstance) {
        this.connectionInstance = connectionInstance;
    }

    public void setConnectionIp(final String connectionIp) {
        this.connectionIp = connectionIp;
    }

    public void setConnectionPassword(final String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    public void setConnectionPort(final String connectionPort) {
        this.connectionPort = connectionPort;
    }

    public void setConnectionUser(final String connectionUser) {
        this.connectionUser = connectionUser;
    }
}
