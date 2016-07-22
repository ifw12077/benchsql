package edu.hm.cs.benchsql.model;

public class SqlConnection {

    private String ip;
    private String port;
    private String instance;
    private String database;
    private String user;
    private String password;

    public SqlConnection() {
        this.initializeFields();
    }

    public String getDatabase() {
        return this.database;
    }

    public String getInstance() {
        return this.instance;
    }

    public String getIp() {
        return this.ip;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPort() {
        return this.port;
    }

    public String getUser() {
        return this.user;
    }

    private void initializeFields() {
        this.setIp("");
        this.setPort("");
        this.setInstance("");
        this.setDatabase("");
        this.setUser("");
        this.setPassword("");
    }

    public void setDatabase(final String Database) {
        this.database = Database;
    }

    public void setInstance(final String Instance) {
        this.instance = Instance;
    }

    public void setIp(final String Ip) {
        this.ip = Ip;
    }

    public void setPassword(final String Password) {
        this.password = Password;
    }

    public void setPort(final String Port) {
        this.port = Port;
    }

    public void setUser(final String User) {
        this.user = User;
    }
}
