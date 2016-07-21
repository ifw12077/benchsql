package edu.hm.cs.benchsql.controller.events;

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

    private void connectToMssql(final SqlConnection sqlConnection) {
        // TODO Auto-generated method stub

    }

    private void connectToMysql(final SqlConnection sqlConnection) {
        // TODO Auto-generated method stub

    }

    private void connectToSybase(final SqlConnection sqlConnection) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handle(final ActionEvent event) {
        this.mainView.getButtonSave().fire();
        final String connectionString = this.mainView.getComboBoxTypes().getValue();
        final SqlConnection sqlConnection = this.model.getConnection(connectionString);
        if ("MySQL".equals(connectionString)) {
            this.connectToMysql(sqlConnection);
        } else if ("Microsoft SQL".equals(connectionString)) {
            this.connectToMssql(sqlConnection);
        } else if ("SQL Anywhere".equals(connectionString)) {
            this.connectToSybase(sqlConnection);
        }
    }
}