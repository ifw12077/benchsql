package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class CloseEventHandler implements EventHandler<WindowEvent> {

    // private final Model model;
    // private final MainView mainView;

    public CloseEventHandler(final Model model, final MainView mainView) {
        // this.model = model;
        // this.mainView = mainView;
    }

    @Override
    public void handle(final WindowEvent event) {

    }
}