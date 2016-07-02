package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.AusgabeView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class AusgabeVC {

    private Model model;
    private AusgabeView ausgabeView;


    public AusgabeVC(Model model) {
        this.model = model;
        this.ausgabeView = new AusgabeView();

        // Eventhandler registrieren
        ausgabeView.getBackBtn().setOnAction(new backBtnEventHandler());
    }

    public void show() {
        // View mit Daten befuellen
        int anz = 1;
        for (String key : model.getNamePwMap().keySet()) {
            // 1: Name, Vorname
            ausgabeView.getList().getItems().add(anz + ": " + key + ", " + model.getNamePwMap().get(key));
            anz++;
        }

        // View anzeigen
        ausgabeView.show(model.getPrimaryStage());
    }

    //+++++++++++++++++++++++++++++++++++++++++++++
    // Events
    //+++++++++++++++++++++++++++++++++++++++++++++


    class backBtnEventHandler implements EventHandler {

        @Override
        public void handle(Event event) {
            // zur naechsten Seiten springen!
            MainVC eingabeVC = new MainVC(model);
            eingabeVC.show();
        }
    }
}
