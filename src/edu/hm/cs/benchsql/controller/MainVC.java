package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;

public class MainVC {

    private final Model model;
    private final MainView mainView;

    public MainVC(final Model model) {
        this.model = model;
        this.mainView = new MainView();
        this.setOnAction();
    }

    private void setOnAction() {
        this.mainView.getButtonImportData().setOnAction(new OpenEventHandler(this.model, this.mainView));
    }

    public void show() {
        this.mainView.show(this.model.getPrimaryStage());
    }

}
