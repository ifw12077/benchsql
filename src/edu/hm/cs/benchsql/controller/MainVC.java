package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;

public class MainVC {

    private final Model model;
    private final MainView mainView;

    public MainVC(final Model model) {
        this.model = model;
        this.mainView = new MainView();
    }

    public void show() throws Exception {
        mainView.show(model.getPrimaryStage());
    }
}

