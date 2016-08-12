package edu.hm.cs.benchsql.controller.events;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import edu.hm.cs.benchsql.controller.threads.ImportProfileThread;
import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ImportEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ImportEventHandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    private Integer getImportCount() {
        Integer importCount = Integer.parseInt(this.mainView.getTextFieldImport().getText());
        if (importCount == 0) {
            importCount = this.mainView.getTableViewData().getItems().size();
        }
        return importCount;
    }

    @Override
    public void handle(final ActionEvent arg0) {
        if (this.importIsValid()) {
            final Integer importBase = this.mainView.getTableViewData().getItems().size();
            if (!"".equals(this.model.getConnectedTo()) && (importBase > 0)) {
                final String profileType = this.mainView.getComboBoxImportAs().getValue();
                if (profileType != null) {
                    final Integer importCount = this.getImportCount();
                    Integer importedCount = 0;

                    final long startTime = System.currentTimeMillis();
                    if (this.mainView.getCheckBoxImport().isSelected()) {
                        final Thread[] threadArray = new Thread[importCount];
                        for (int i = 0; i < threadArray.length; i++) {
                            threadArray[i] = new Thread(new ImportProfileThread(this.model, this.mainView, profileType,
                                    importedCount % importBase));
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
                    } else {
                        while (importedCount < importCount) {
                            final Thread thread = new Thread(new ImportProfileThread(this.model, this.mainView,
                                    profileType, importedCount % importBase));
                            thread.start();
                            try {
                                thread.join();
                            } catch (final InterruptedException e) {
                                e.printStackTrace();
                            }
                            importedCount++;
                        }
                    }
                    final long endTime = System.currentTimeMillis();
                    long runTime = endTime - startTime;
                    final long minutes = TimeUnit.MILLISECONDS.toMinutes(runTime);
                    runTime -= TimeUnit.MINUTES.toMillis(minutes);
                    final long seconds = TimeUnit.MILLISECONDS.toSeconds(runTime);
                    runTime -= TimeUnit.SECONDS.toMillis(seconds);
                    this.mainView.getLabelResult().setText(
                            String.format(this.model.getConnectedTo() + " benötigte für den Import von " + importCount
                                    + " " + profileType + " %d m, %d s, %d ms", minutes, seconds, runTime));
                } else {
                    final Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Fehler beim Import");
                    alert.setHeaderText("Kein Profiltyp gewählt");
                    alert.setContentText("Bitte wählen sie einen Profiltyp!");
                    alert.showAndWait();
                }
            } else {
                final Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Fehler beim Import");
                alert.setHeaderText("Serververbindung oder Importdaten fehlen");
                alert.setContentText("Bitte verbinden Sie sich mit einem Server und importieren Sie Daten!");
                alert.showAndWait();
            }
        } else {
            final Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fehler bei der Eingabe");
            alert.setHeaderText("Falsche Eingabe beim Import");
            alert.setContentText("Bitte geben Sie nur ganzahlige Werte an!");
            alert.showAndWait();
        }
    }

    private boolean importIsValid() {
        return Pattern.matches("\\d+", this.mainView.getTextFieldImport().getText());
    }
}
