package edu.hm.cs.benchsql.model;

public class SqlConnection {

    private final String connectionUrl;
    private String connectionIp;
    private String connectionPort;
    private String connectionUser;
    private String connectionPassword;
    private final String connectionDriver;

    public SqlConnection(final String connectionUrl, final String connectionDriver) {
        this.connectionDriver = connectionDriver;
        this.connectionUrl = connectionUrl;
        this.initializeFields();
    }

    public String getConnectionDriver() {
        return this.connectionDriver;
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

    public String getConnectionUrl() {
        return this.connectionUrl;
    }

    public String getConnectionUser() {
        return this.connectionUser;
    }

    private void initializeFields() {
        this.setConnectionIp("");
        this.setConnectionPort("");
        this.setConnectionUser("");
        this.setConnectionPassword("");
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
