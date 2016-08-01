package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.SqlConnection;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SaveEventHandler implements EventHandler<ActionEvent> {

    private final MainView mainView;
    private final Model model;

    public SaveEventHandler(final Model model, final MainView mainView) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void handle(final ActionEvent event) {
        final SqlConnection sqlConnection = this.model.getSqlConnection(this.mainView.getComboBoxTypes().getValue());
        if (sqlConnection != null) {
            sqlConnection.setIp(this.mainView.getTextFieldIp().getText());
            sqlConnection.setPort(this.mainView.getTextFieldPort().getText());
            sqlConnection.setInstance(this.mainView.getTextFieldInstance().getText());
            sqlConnection.setDatabase(this.mainView.getTextFieldDatabase().getText());
            sqlConnection.setUser(this.mainView.getTextFieldUser().getText());
            sqlConnection.setPassword(this.mainView.getPasswordFieldPasswort().getText());
        }
    }
}
