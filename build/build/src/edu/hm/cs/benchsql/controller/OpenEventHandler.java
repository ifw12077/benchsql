package edu.hm.cs.benchsql.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import edu.hm.cs.benchsql.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

public class OpenEventHandler implements javafx.event.EventHandler<ActionEvent> {

    private final Model model;

    public OpenEventHandler(final Model model) {
        this.model = model;
    }

    @Override
    public void handle(final ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Excel Dateien (.xlsx)", "*.xlsx"));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Excel 2003 Dateien (.xls)", "*.xls"));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Trennzeichen getrennt (.csv)", "*.csv"));
        final File file = fileChooser.showOpenDialog(this.model.getPrimaryStage());
        if (file != null) {
            String extension = "";
            final String fileName = file.getAbsolutePath();
            final Integer i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }
            switch (extension) {
                case "xlsx":

                    break;
                case "xls":

                    break;
                case "csv":
                    final TextInputDialog dialog = new TextInputDialog(";");
                    dialog.setTitle("CSV Trennzeichen");
                    dialog.setHeaderText("Mit welchem Zeichen werden die Spalten getrennt?");
                    dialog.setContentText("Trennzeichen:");
                    final Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        this.readCsv(fileName, result.get());
                    }
                default:
                    break;
            }
        }

    }

    private void readCsv(final String fileName, final String delimiter) {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(fileName));
            line = br.readLine();
            while (line != null) {
                final String[] country = line.split(delimiter);
                System.out.println(country.length);
                line = br.readLine();
            }
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
