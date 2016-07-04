package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Optional;

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
                if (lineArray.length > j) {
                    j = lineArray.length;
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
        final Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {
            final Row nextRow = iterator.next();
            final Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                final Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }

    }

    private void writeToTable(final String[][] csvArray) {

    }

}
