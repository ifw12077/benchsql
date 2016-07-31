package edu.hm.cs.benchsql.model;

import javafx.stage.Stage;

public class Model {
    private final Stage primaryStage;
    private final SqlConnection mysqlConnection;
    private final SqlConnection mssqlConnection;
    private final SqlConnection sybaseConnection;
    private String connectedTo;
    private String[] tableDataColumns;

    public Model(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mysqlConnection = new SqlConnection();
        this.mssqlConnection = new SqlConnection();
        this.sybaseConnection = new SqlConnection();
    }

    public String getConnectedTo() {
        return this.connectedTo;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
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

    public String[] getTableDataColumns() {
        return this.tableDataColumns;
    }

    public void setConnectedTo(final String connectedTo) {
        this.connectedTo = connectedTo;
    }

    public void setTableDataColumns(final String[] tableDataColumns) {
        this.tableDataColumns = tableDataColumns;
    }
}