package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.controller.events.ChangeImportEventHandler;
import edu.hm.cs.benchsql.controller.events.ChangeServerEventHandler;
import edu.hm.cs.benchsql.controller.events.ChangeTestEventHandler;
import edu.hm.cs.benchsql.controller.events.ConnectEventHandler;
import edu.hm.cs.benchsql.controller.events.ImportEventHandler;
import edu.hm.cs.benchsql.controller.events.OpenEventHandler;
import edu.hm.cs.benchsql.controller.events.SaveEventHandler;
import edu.hm.cs.benchsql.controller.events.TestEventHandler;
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
        this.mainView.getComboBoxTypes().setOnAction(new ChangeServerEventHandler(this.model, this.mainView));
        this.mainView.getComboBoxImportAs().setOnAction(new ChangeImportEventHandler(this.model, this.mainView));
        this.mainView.getButtonImport().setOnAction(new ImportEventHandler(this.model, this.mainView));
        this.mainView.getComboBoxTestFor().setOnAction(new ChangeTestEventHandler(this.model, this.mainView));
        this.mainView.getButtonTest().setOnAction(new TestEventHandler(this.model, this.mainView));
    }

    public void show() {
        this.mainView.show(this.model.getPrimaryStage());
    }
}