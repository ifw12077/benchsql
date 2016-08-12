package edu.hm.cs.benchsql.controller.threads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.data.ImportData;
import edu.hm.cs.benchsql.view.MainView;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

public class OpenDataThread implements Runnable {

    private final Model model;
    private final MainView mainView;
    private final File file;

    public OpenDataThread(final Model model, final MainView mainView, final File file) {
        this.model = model;
        this.mainView = mainView;
        this.file = file;
    }

    private Workbook readFile(final File file) {
        Workbook returnWB = null;
        if (file != null) {
            String extension = "";
            final String fileName = file.getAbsolutePath();
            final int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }
            if ("xlsx".equals(extension)) {
                try {
                    final FileInputStream xfile = new FileInputStream(new File(fileName));
                    final Workbook workbook = new XSSFWorkbook(xfile);
                    returnWB = workbook;
                    workbook.close();
                    xfile.close();
                } catch (final FileNotFoundException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            } else if ("xls".equals(extension)) {
                try {
                    final FileInputStream xfile = new FileInputStream(new File(fileName));
                    final Workbook workbook = new HSSFWorkbook(xfile);
                    returnWB = workbook;
                    workbook.close();
                    xfile.close();
                } catch (final FileNotFoundException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            } else if ("csv".equals(extension)) {
                try {
                    final CSVReader csvReader = new CSVReader(
                            new InputStreamReader(new FileInputStream(file), "Cp1252"), ';');
                    final Workbook workbook = new HSSFWorkbook();
                    final CreationHelper helper = workbook.getCreationHelper();
                    final Sheet sheet = workbook.createSheet();
                    String[] line = csvReader.readNext();
                    int rowCount = 0;

                    while (line != null) {
                        final Row row = sheet.createRow(rowCount);
                        for (int j = 0; j < line.length; j++) {
                            row.createCell(j).setCellValue(helper.createRichTextString(line[j]));
                        }
                        rowCount++;
                        line = csvReader.readNext();
                    }
                    returnWB = workbook;
                    workbook.close();
                    csvReader.close();
                } catch (final FileNotFoundException e) {
                    e.printStackTrace();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnWB;
    }

    private String[][] readWorkbook(final Workbook workbook) {
        final Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        int i = 0;
        int j = 0;
        int cellCount = 0;

        while (iterator.hasNext()) {
            final Row nextRow = iterator.next();
            i++;
            final Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                cellIterator.next();
                j++;
            }
            if (j > cellCount) {
                cellCount = j;
            }

            j = 0;
        }

        iterator = firstSheet.iterator();
        final String[][] excelArray = new String[i][cellCount];
        i = 0;
        j = 0;

        while (iterator.hasNext()) {
            final Row nextRow = iterator.next();
            final Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                final Cell cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                final int cellType = cell.getCellType();
                final int cellNumber = cell.getColumnIndex();
                final String cellValue = cell.getStringCellValue();
                if (cellType == Cell.CELL_TYPE_STRING) {
                    excelArray[i][cellNumber] = cellValue;
                } else {
                    excelArray[i][cellNumber] = "";
                }
            }
            i++;
        }
        return excelArray;
    }

    @Override
    public void run() {
        final Workbook workbook = this.readFile(this.file);
        final String[][] dataArray = this.readWorkbook(workbook);
        this.writeToTable(dataArray);
    }

    private void writeToTable(final String[][] tableArray) {
        Platform.runLater(() -> this.mainView.getTableViewData().getColumns().clear());
        this.model.getImportData().removeAll(this.model.getImportData());
        final ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(tableArray));
        data.remove(0);
        for (int i = 0; i < tableArray[0].length; i++) {
            final TableColumn<String[], String> tc = new TableColumn<>(tableArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(p -> new SimpleStringProperty((p.getValue()[colNo])));
            tc.setPrefWidth(90);
            Platform.runLater(() -> this.mainView.getTableViewData().getColumns().add(tc));
        }
        this.model.getImportData().add(new ImportData(""));
        for (final String header : tableArray[0]) {
            this.model.getImportData().add(new ImportData(header));
        }
        Platform.runLater(() -> this.mainView.getTableViewData().setItems(data));
        Platform.runLater(
                () -> this.mainView.getlabelImportData().setText(this.file.getAbsolutePath() + " importiert!"));
        Platform.runLater(() -> this.mainView.getComboBoxImportAs().setDisable(false));
    }

}
