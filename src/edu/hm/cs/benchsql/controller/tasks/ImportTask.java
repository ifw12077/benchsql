package edu.hm.cs.benchsql.controller.tasks;

import java.util.regex.Pattern;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;

public class ImportTask extends Task<Void> {

    private final MainView mainView;
    private final Model model;
    private String profileType;
    private String operation;
    private Integer importCount;
    private long[] statisticArray;

    public ImportTask(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
        super.messageProperty()
                .addListener((obs, oldMessage, newMessage) -> this.mainView.getLabelResult().setText(newMessage));
        super.setOnSucceeded(event -> this.mainView.getLabelResult()
                .setText(this.operation + this.model.getConnectedTo() + ":\nImport von " + this.importCount + " "
                        + this.profileType + " (100 Durchgänge)\nMinimalzeit: "
                        + this.model.getTimeString(this.statisticArray[0]) + "\nMaximalzeit: "
                        + this.model.getTimeString(this.statisticArray[1]) + "\nDurchschnitt: "
                        + this.model.getTimeString(this.statisticArray[2])));
    }

    @Override
    protected Void call() throws Exception {
        if (this.importIsValid()) {
            final Integer importBase = this.mainView.getTableViewData().getItems().size();
            if (!"".equals(this.model.getConnectedTo()) && (importBase > 0)) {
                this.profileType = this.mainView.getComboBoxImportAs().getValue();
                if (this.profileType != null) {
                    this.importCount = this.getImportCount();
                    final Integer importedCount = 0;
                    final long[] timeArray = new long[this.model.percent];
                    this.operation = this.mainView.getCheckBoxImport().isSelected() ? "Parallel mit " : "Seriell mit ";
                    if (this.mainView.getCheckBoxImport().isSelected()) {
                        for (int i = 0; i < this.model.percent; i++) {
                            final long startTime = System.currentTimeMillis();
                            this.importParallel(importBase, this.profileType, this.importCount, importedCount);
                            final long endTime = System.currentTimeMillis();
                            timeArray[i] = endTime - startTime;
                            this.updateMessage("Durchgang " + (i + 1) + " abgeschlossen.");
                        }
                    } else {
                        for (int i = 0; i < this.model.percent; i++) {
                            final long startTime = System.currentTimeMillis();
                            this.importSerial(importBase, this.profileType, this.importCount, importedCount);
                            final long endTime = System.currentTimeMillis();
                            timeArray[i] = endTime - startTime;
                            this.updateMessage("Durchgang " + (i + 1) + " abgeschlossen.");
                        }
                    }
                    this.statisticArray = this.model.getStatistics(timeArray);
                } else {
                    Platform.runLater(() -> this.model.showDialog(AlertType.WARNING, "Fehler beim Import",
                            "Kein Profiltyp gewählt", "Bitte wählen sie einen Profiltyp!"));
                }
            } else {
                Platform.runLater(() -> this.model.showDialog(AlertType.WARNING, "Fehler beim Import",
                        "Serververbindung oder Importdaten fehlen",
                        "Bitte verbinden Sie sich mit einem Server und importieren Sie Daten!"));
            }
        } else {
            Platform.runLater(() -> this.model.showDialog(AlertType.WARNING, "Fehler bei der Eingabe",
                    "Falsche Eingabe beim Import", "Bitte geben Sie nur ganzahlige Werte an!"));
        }
        return null;
    }

    private Integer getImportCount() {
        Integer importCount = Integer.parseInt(this.mainView.getTextFieldImport().getText());
        if (importCount == 0) {
            importCount = this.mainView.getTableViewData().getItems().size();
        }
        if (importCount > 100) {
            importCount = 100;
        }
        return importCount;
    }

    private boolean importIsValid() {
        return Pattern.matches("\\d+", this.mainView.getTextFieldImport().getText());
    }

    private void importParallel(final Integer importBase, final String profileType, final Integer importCount,
            Integer importedCount) {
        final Thread[] threadArray = new Thread[importCount];
        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new Thread(
                    new ImportProfileTask(this.model, this.mainView, profileType, importedCount % importBase));
            threadArray[i].start();
            importedCount++;
        }

        for (final Thread element : threadArray) {
            try {
                element.join();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void importSerial(final Integer importBase, final String profileType, final Integer importCount,
            Integer importedCount) {
        while (importedCount < importCount) {
            final Thread thread = new Thread(
                    new ImportProfileTask(this.model, this.mainView, profileType, importedCount % importBase));
            thread.start();
            try {
                thread.join();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            importedCount++;
        }
    }
}
