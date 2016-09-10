package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.data.SqlConnection;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChangeServerEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ChangeServerEventHandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent event) {
        final SqlConnection sqlConnection = this.model.getSqlConnection(this.mainView.getComboBoxTypes().getValue());
        if (sqlConnection != null) {
            this.mainView.getTextFieldIp().setText(sqlConnection.getIp());
            this.mainView.getTextFieldPort().setText(sqlConnection.getPort());
            this.mainView.getTextFieldInstance().setText(sqlConnection.getInstance());
            this.mainView.getTextFieldDatabase().setText(sqlConnection.getDatabase());
            this.mainView.getTextFieldUser().setText(sqlConnection.getUser());
            this.mainView.getPasswordFieldPasswort().setText(sqlConnection.getPassword());
            this.mainView.getComboBoxImportAs().getSelectionModel().clearSelection();
            this.mainView.getComboBoxImportAs().getItems().clear();
            this.mainView.getTableViewImportAs().getColumns().clear();
            this.mainView.getTableViewImportAs().getItems().clear();
            this.mainView.getLabelConnect().setText("");
            this.mainView.getLabelResult().setText("");
            this.mainView.getComboBoxTestFor().getSelectionModel().clearSelection();
            this.mainView.getComboBoxTestFor().getItems().clear();
            this.mainView.getTableViewTestFor().getColumns().clear();
            this.mainView.getTableViewTestFor().getItems().clear();
            this.model.setConnectedTo("");
        }
    }
}
