package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.controller.events.ChangeImportEventhandler;
import edu.hm.cs.benchsql.controller.events.ChangeServerEventhandler;
import edu.hm.cs.benchsql.controller.events.ConnectEventHandler;
import edu.hm.cs.benchsql.controller.events.ImportEventHandler;
import edu.hm.cs.benchsql.controller.events.OpenEventHandler;
import edu.hm.cs.benchsql.controller.events.SaveEventHandler;
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
        this.mainView.getButtonSave().setOnAction(new SaveEventHandler(this.model, this.mainView));
        this.mainView.getButtonConnect().setOnAction(new ConnectEventHandler(this.model, this.mainView));
        this.mainView.getComboBoxTypes().setOnAction(new ChangeServerEventhandler(this.model, this.mainView));
        this.mainView.getComboBoxImportAs().setOnAction(new ChangeImportEventhandler(this.model, this.mainView));
        this.mainView.getButtonImport().setOnAction(new ImportEventHandler(this.model, this.mainView));
    }

    public void show() {
        this.mainView.show(this.model.getPrimaryStage());
    }
}