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

    private void connectToMsSql(final SqlConnection sqlConnection) {

    }

    private void connectToMySql(final SqlConnection sqlConnection) {

    }

    private void connectToSybase(final SqlConnection sqlConnection) {

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