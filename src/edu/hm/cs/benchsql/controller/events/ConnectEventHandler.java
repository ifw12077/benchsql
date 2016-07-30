package edu.hm.cs.benchsql.controller.events;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://" + mssql.getIp() + ":" + mssql.getPort()
                    + ";databaseName=" + mssql.getDatabase(), mssql.getUser(), mssql.getPassword());
            this.loadProfileTypeCodes(connection);
            this.model.setConnectedTo("Microsoft SQL");
        } catch (final ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void connectToMySql(final SqlConnection mysql) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + mysql.getIp() + ":" + mysql.getPort() + "/"
                    + mysql.getDatabase() + "?useSSL=false", mysql.getUser(), mysql.getPassword());
            this.loadProfileTypeCodes(connection);
            this.model.setConnectedTo("MySQL");
        } catch (final ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void connectToSybase(final SqlConnection sybase) {
        Connection connection = null;
        try {
            Class.forName("sybase.jdbc4.sqlanywhere.IDriver");
            connection = DriverManager.getConnection("jdbc:sqlanywhere:UID=" + sybase.getUser() + ";PWD="
                    + sybase.getPassword() + ";eng=" + sybase.getInstance() + ";database=" + sybase.getDatabase()
                    + ";host=" + sybase.getIp() + ";port=" + sybase.getPort());
            this.loadProfileTypeCodes(connection);
            this.model.setConnectedTo("SQL Anywhere");
        } catch (final ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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
        this.mainView.getLabelConnect().setText(this.model.getConnectedTo() + " verbunden!");
    }

    private void loadProfileTypeCodes(final Connection connection) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT profiletypecode FROM pri_profiletype");
            this.write(resultSet);
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void write(final ResultSet resultSet) {
        final ArrayList<String> profiletypecodes = new ArrayList<>();
        try {
            while (resultSet.next()) {
                profiletypecodes.add(resultSet.getString("profiletypecode"));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        for (final String string : profiletypecodes) {
            this.mainView.getComboBoxImportAs().getItems().add(string);
        }
    }
}