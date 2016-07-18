package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.model.Model;
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
        this.model.getConnection(this.mainView.getComboBoxSqlServers().getValue());
    }

}
