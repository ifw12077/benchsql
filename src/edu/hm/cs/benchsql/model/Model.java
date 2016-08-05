package edu.hm.cs.benchsql.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.hm.cs.benchsql.model.data.ImportData;
import edu.hm.cs.benchsql.model.data.SqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class Model {
    private final Stage primaryStage;
    private final SqlConnection mysqlConnection;
    private final SqlConnection mssqlConnection;
    private final SqlConnection sybaseConnection;
    private String connectedTo;
    private final ObservableList<ImportData> importData;
    private final ArrayList<String> profileTypeCodes;

    private final ArrayList<String> propGrpPropAttributeCodes;

    public Model(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.profileTypeCodes = new ArrayList<>();
        this.propGrpPropAttributeCodes = new ArrayList<>();
        this.importData = FXCollections.observableArrayList();
        this.mysqlConnection = new SqlConnection();
        this.mssqlConnection = new SqlConnection();
        this.sybaseConnection = new SqlConnection();
    }

    public String getConnectedTo() {
        return this.connectedTo;
    }

    public Connection getConnection(final String connectionString) throws SQLException, ClassNotFoundException {
        final SqlConnection sqlConnection = this.getSqlConnection(connectionString);
        switch (connectionString) {
            case "MySQL":
                Class.forName("com.mysql.jdbc.Driver");
                return DriverManager.getConnection(
                        "jdbc:mysql://" + sqlConnection.getIp() + ":" + sqlConnection.getPort() + "/"
                                + sqlConnection.getDatabase() + "?useSSL=false",
                        sqlConnection.getUser(), sqlConnection.getPassword());
            case "Microsoft SQL":
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                return DriverManager
                        .getConnection(
                                "jdbc:sqlserver://" + sqlConnection.getIp() + ":" + sqlConnection.getPort()
                                        + ";databaseName=" + sqlConnection.getDatabase(),
                                sqlConnection.getUser(), sqlConnection.getPassword());
            case "SQL Anywhere":
                Class.forName("sybase.jdbc4.sqlanywhere.IDriver");
                return DriverManager.getConnection(
                        "jdbc:sqlanywhere:UID=" + sqlConnection.getUser() + ";PWD=" + sqlConnection.getPassword()
                                + ";eng=" + sqlConnection.getInstance() + ";database=" + sqlConnection.getDatabase()
                                + ";host=" + sqlConnection.getIp() + ";port=" + sqlConnection.getPort());
            default:
                return null;
        }
    }

    public ObservableList<ImportData> getImportData() {
        return this.importData;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public ArrayList<String> getProfileTypeCodes() {
        return this.profileTypeCodes;
    }

    public ArrayList<String> getPropGrpPropAttributeCodes() {
        return this.propGrpPropAttributeCodes;
    }

    public SqlConnection getSqlConnection(final String connectionString) {
        switch (connectionString) {
            case "MySQL":
                return this.mysqlConnection;
            case "Microsoft SQL":
                return this.mssqlConnection;
            case "SQL Anywhere":
                return this.sybaseConnection;
            default:
                return null;
        }
    }

    public void setConnectedTo(final String connectedTo) {
        this.connectedTo = connectedTo;
    }
}