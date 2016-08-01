package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChangeImportEventhandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ChangeImportEventhandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent event) {
        /* final String connectionString = */this.mainView.getComboBoxImportAs().getValue();
        /* final SqlConnection sqlConnection = */this.model.getSqlConnection(this.model.getConnectedTo());
    }
}
