package edu.hm.cs.benchsql.model;

import javafx.stage.Stage;

public class Model {
    private final Stage primaryStage;
    private final SqlConnection mysqlConnection;
    private final SqlConnection mssqlConnection;
    private final SqlConnection sybaseConnection;

    public Model(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mysqlConnection = new SqlConnection();
        this.mssqlConnection = new SqlConnection();
        this.sybaseConnection = new SqlConnection();
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
        // SqlConnection returnConnnection = null;
        // if ("MySQL".equals(connectionString)) {
        // returnConnnection = this.mysqlConnection;
        // } else if ("Microsoft SQL".equals(connectionString)) {
        // returnConnnection = this.mssqlConnection;
        // } else if ("SQL Anywhere".equals(connectionString)) {
        // returnConnnection = this.sybaseConnection;
        // }
        // return returnConnnection;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}