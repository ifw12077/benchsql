package edu.hm.cs.benchsql.model;

import javafx.stage.Stage;

public class Model {
    private final Stage primaryStage;
    private final SqlConnection mysqlConnection;
    private final SqlConnection mssqlConnection;
    private final SqlConnection sybaseConnection;
    private String connectedTo;

    public Model(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mysqlConnection = new SqlConnection();
        this.mssqlConnection = new SqlConnection();
        this.sybaseConnection = new SqlConnection();
    }

    public String getConnectedTo() {
        return this.connectedTo;
    }

    public SqlConnection getConnection(final String connectionString) {
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

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void setConnectedTo(final String connectedTo) {
        this.connectedTo = connectedTo;
    }
}