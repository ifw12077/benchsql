package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.controller.threads.OpenDataThread;
import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

public class OpenEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public OpenEventHandler(final Model model, final MainView mainView) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void handle(final ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Arbeitsmappe (*.xlsx)", "*.xlsx"));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Excel 97-2003 Arbeitsmappe (*.xls)", "*.xls"));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Trennzeichen getrennt (*.csv)", "*.csv"));
        final Thread openDataThread = new Thread(new OpenDataThread(this.model, this.mainView,
                fileChooser.showOpenDialog(this.model.getPrimaryStage())));
        openDataThread.start();
    }
}
