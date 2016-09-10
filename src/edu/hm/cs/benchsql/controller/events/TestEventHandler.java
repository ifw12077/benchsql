package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.controller.tasks.TestTask;
import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TestEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public TestEventHandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent arg0) {
        final Thread testThread = new Thread(new TestTask(this.model, this.mainView));
        testThread.start();
    }

}
