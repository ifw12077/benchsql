package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class MainVC {

    private final Model model;
    private final MainView mainView;

    public MainVC(final Model model) {
        this.model = model;
        this.mainView = new MainView();

        mainView.getAddBtn().setOnAction(new addBtnEventHandler());
        mainView.getOkBtn().setOnAction(new OkBtnEventHandler());
    }

    public void show() {
        mainView.show(model.getPrimaryStage());
    }

    class OkBtnEventHandler implements EventHandler {
        @Override
        public void handle(Event event) {
            AusgabeVC ausgabeVC = new AusgabeVC(model);
            ausgabeVC.show();
        }
    }

    class addBtnEventHandler implements EventHandler {
        @Override
        public void handle(Event event) {
            mainView.getMeldungT().setText("");

            // Daten aus den Textfeldern holen
            String vname = mainView.getVornameTF().getText();
            String nname = mainView.getNachnameTF().getText();

            //Textfelder zuruecksetzen
            mainView.getNachnameTF().setText("");
            mainView.getVornameTF().setText("");

            // Daten testen
            if (vname.isEmpty()) {
                mainView.getMeldungT().setText("Der Vorname fehlt!");
                return;
            }
            if (nname.isEmpty()) {
                mainView.getMeldungT().setText("Der Nachname fehlt!");
                return;
            }

            // Daten hinzufuegen
            final String erg;
            erg = model.getNamePwMap().put(nname, vname);

            // Meldung ausgeben
            if (erg == null) {
                mainView.getMeldungT().setText("Leser hinzugefügt");
            } else {
                mainView.getMeldungT().setText("Leser bereits vorhanden");
            }
        }
    }
}

