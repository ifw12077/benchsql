package edu.hm.cs.benchsql.controller.events;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;

public class ConnectEventHandler implements EventHandler<ActionEvent> {

    private final MainView mainView;
    private final Model model;

    public ConnectEventHandler(final Model model, final MainView mainView) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void handle(final ActionEvent event) {
        this.mainView.getButtonSave().fire();
        this.mainView.getComboBoxImportAs().getSelectionModel().clearSelection();
        this.mainView.getComboBoxImportAs().getItems().clear();
        final String connectionString = this.mainView.getComboBoxTypes().getValue();
        if (connectionString != null) {
            try {
                this.loadProfileTypeCodes(this.model.getConnection(connectionString));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            if ("MySQL".equals(connectionString)) {
                this.model.setConnectedTo("MySQL");
            } else if ("Microsoft SQL".equals(connectionString)) {
                this.model.setConnectedTo("Microsoft SQL");
            } else if ("SQL Anywhere".equals(connectionString)) {
                this.model.setConnectedTo("SQL Anywhere");
            }
            this.mainView.getLabelConnect().setText(this.model.getConnectedTo() + " verbunden!");
        } else {
            this.model.showDialog(AlertType.WARNING, "Fehler beim Verbinden", "Serververbindung fehlt",
                    "Bitte wählen Sie eine Serververbindung aus!");
        }
    }

    private void loadProfileTypeCodes(final Connection connection) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT profiletypecode FROM pri_profiletype");
            while (resultSet.next()) {
                this.model.getProfileTypeCodes().add(resultSet.getString("profiletypecode"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        this.mainView.getComboBoxImportAs().getItems().addAll(this.model.getProfileTypeCodes());
        this.mainView.getComboBoxTestFor().getItems().addAll(this.model.getProfileTypeCodes());
    }
}