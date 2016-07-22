package edu.hm.cs.benchsql.controller.events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.SqlConnection;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ConnectEventHandler implements EventHandler<ActionEvent> {

    private final MainView mainView;
    private final Model model;

    public ConnectEventHandler(final Model model, final MainView mainView) {
        this.mainView = mainView;
        this.model = model;
    }

    private void connectToMsSql(final SqlConnection mssql) {

    }

    private void connectToMySql(final SqlConnection mysql) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://" + mysql.getIp() + ":" + mysql.getPort() + "/" + mysql.getDatabase(),
                    mysql.getUser(), mysql.getPassword());
            connect.createStatement();
        } catch (final ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void connectToSybase(final SqlConnection sybase) {

    }

    @Override
    public void handle(final ActionEvent event) {
        this.mainView.getButtonSave().fire();
        final String connectionString = this.mainView.getComboBoxTypes().getValue();
        final SqlConnection sqlConnection = this.model.getConnection(connectionString);
        if ("MySQL".equals(connectionString)) {
            this.connectToMySql(sqlConnection);
        } else if ("Microsoft SQL".equals(connectionString)) {
            this.connectToMsSql(sqlConnection);
        } else if ("SQL Anywhere".equals(connectionString)) {
            this.connectToSybase(sqlConnection);
        }
    }
}