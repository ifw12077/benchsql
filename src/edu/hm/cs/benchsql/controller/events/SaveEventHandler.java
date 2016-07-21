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
        final SqlConnection sqlConnection = this.model.getConnection(this.mainView.getComboBoxTypes().getValue());
        sqlConnection.setConnectionIp(this.mainView.getTextFieldIp().getText());
        sqlConnection.setConnectionPort(this.mainView.getTextFieldPort().getText());
        sqlConnection.setConnectionInstance(this.mainView.getTextFieldInstance().getText());
        sqlConnection.setConnectionDatabase(this.mainView.getTextFieldDatabase().getText());
        sqlConnection.setConnectionUser(this.mainView.getTextFieldUser().getText());
        sqlConnection.setConnectionPassword(this.mainView.getTextFieldPasswort().getText());
    }

}
