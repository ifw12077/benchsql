package edu.hm.cs.benchsql.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

public class OpenEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public OpenEventHandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Dateien", "*.xlsx"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 2003 Dateien", "*.xls"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Trennzeichen getrennt", "*.csv"));
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
                    try {
                        final FileInputStream xfile = new FileInputStream(new File(fileName));
                        final Workbook workbook = new XSSFWorkbook(xfile);
                        this.readExcel(workbook);
                        workbook.close();
                        xfile.close();
                    } catch (final FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                case "xls":
                    try {
                        final FileInputStream xfile = new FileInputStream(new File(fileName));
                        final Workbook workbook = new HSSFWorkbook(xfile);
                        this.readExcel(workbook);
                        workbook.close();
                        xfile.close();
                    } catch (final FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
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
            Integer i = 0;
            Integer j = 0;
            while (line != null) {
                i++;
                final String[] lineArray = line.split(delimiter);
                if (j == 0) {
                    j = lineArray.length;
                }
                if (lineArray.length != j) {
                    final Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Problem beim CSV Import");
                    alert.setHeaderText("Keine Tabellenformaterung gefunden");
                    alert.setContentText("Die ausgewählte CSV Datei hat nicht die benötigte Formatierung!");
                    alert.showAndWait();
                    break;
                }
                line = br.readLine();
            }
            br.close();
            final String[][] csvArray = new String[i][j];
            br = new BufferedReader(new FileReader(fileName));
            line = br.readLine();
            i = 0;
            while (line != null) {
                final String[] lineArray = line.split(delimiter);
                for (int k = 0; k < lineArray.length; k++) {
                    csvArray[i][k] = lineArray[k];
                }
                line = br.readLine();
                i++;
            }
            this.writeToTable(csvArray);
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

    private void readExcel(final Workbook workbook) {
        final Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        Integer i = 0;
        Integer j = 0;

        while (iterator.hasNext()) {
            final Row nextRow = iterator.next();
            i++;
            final Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                cellIterator.next();
                j++;
            }
        }

        iterator = firstSheet.iterator();
        final String[][] excelArray = new String[i][j];
        i = 0;
        j = 0;

        while (iterator.hasNext()) {
            final Row nextRow = iterator.next();
            final Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                final Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        excelArray[i][j] = cell.getStringCellValue();
                    case Cell.CELL_TYPE_BOOLEAN:
                        excelArray[i][j] = String.valueOf(cell.getBooleanCellValue());
                    case Cell.CELL_TYPE_NUMERIC:
                        excelArray[i][j] = String.valueOf(cell.getNumericCellValue());
                    default:
                        excelArray[i][j] = "";
                }
                j++;
            }
            i++;
            j = 0;
        }
        this.writeToTable(excelArray);
    }

    private void writeToTable(final String[][] tableArray) {
        for (final String[] array : tableArray) {
            for (final String field : array) {
                if (this.mainView.getTableViewData().getColumns().size() == 0) {

                }
            }
        }

    }

}
