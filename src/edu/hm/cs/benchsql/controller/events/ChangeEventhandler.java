package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.SqlConnection;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChangeEventhandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ChangeEventhandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent event) {
        final SqlConnection sqlConnection = this.model.getConnection(this.mainView.getComboBoxTypes().getValue());
        this.mainView.getTextFieldIp().setText(sqlConnection.getConnectionIp());
        this.mainView.getTextFieldPort().setText(sqlConnection.getConnectionPort());
        this.mainView.getTextFieldInstance().setText(sqlConnection.getConnectionInstance());
        this.mainView.getTextFieldDatabase().setText(sqlConnection.getConnectionDatabase());
        this.mainView.getTextFieldUser().setText(sqlConnection.getConnectionUser());
        this.mainView.getTextFieldPasswort().setText(sqlConnection.getConnectionPassword());
    }

}
