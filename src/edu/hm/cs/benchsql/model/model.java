package edu.hm.cs.benchsql.model;

import javafx.stage.Stage;

public class Model {
    private final Stage primaryStage;
    private final SqlConnection mysqlConnection;
    private final SqlConnection mssqlConnection;
    private final SqlConnection sybaseConnection;

    public Model(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mysqlConnection = new SqlConnection("jdbc:mysql:", "com.mysql.jdbc.Driver");
        this.mssqlConnection = new SqlConnection("jdbc:sqlserver:", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.sybaseConnection = new SqlConnection("jdbc:sqlanywhere:", "sybase.jdbc.sqlanywhere.IDriver");
    }

    public SqlConnection getConnection(final String connection) {
        SqlConnection returnConnnection = null;
        if ("MySQL".equals(connection)) {
            returnConnnection = this.mysqlConnection;
        } else if ("Microsoft SQL".equals(connection)) {
            returnConnnection = this.mssqlConnection;
        } else {
            returnConnnection = this.sybaseConnection;
        }
        return returnConnnection;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}