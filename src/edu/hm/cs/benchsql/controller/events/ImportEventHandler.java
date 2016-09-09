package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.controller.tasks.ImportTask;
import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ImportEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ImportEventHandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent arg0) {
        final Thread importThread = new Thread(new ImportTask(this.model, this.mainView));
        importThread.start();
    }
}
