package edu.hm.cs.benchsql.controller.events;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextInputDialog;
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Dateien (*.xlsx)", "*.xlsx"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel 2003 Dateien (*.xls)", "*.xls"));
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Trennzeichen getrennt (*.csv)", "*.csv"));
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
                    try {
                        final TextInputDialog dialog = new TextInputDialog(";");
                        dialog.setTitle("CSV Trennzeichen");
                        dialog.setHeaderText("Mit welchem Zeichen werden die Spalten getrennt?");
                        dialog.setContentText("Trennzeichen:");
                        final CSVReader csvReader;
                        final Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            csvReader = new CSVReader(new FileReader(fileName), result.get().charAt(0));
                            final Workbook workbook = new HSSFWorkbook();
                            final CreationHelper helper = workbook.getCreationHelper();
                            final Sheet sheet = workbook.createSheet();
                            String[] line = csvReader.readNext();
                            Integer rowCount = 0;

                            while (line != null) {
                                final Row row = sheet.createRow(rowCount);
                                for (int j = 0; j < line.length; j++) {
                                    row.createCell(j).setCellValue(helper.createRichTextString(line[j]));
                                }
                                rowCount++;
                                line = csvReader.readNext();
                            }
                            this.readExcel(workbook);
                            workbook.close();
                            csvReader.close();
                        }
                    } catch (final FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                default:
                    break;
            }
        }

    }

    private void readExcel(final Workbook workbook) {
        final Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        Integer i = 0;
        Integer j = 0;
        Integer cellCount = 0;

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
                final int cellType = cell.getCellType();
                if (cellType == Cell.CELL_TYPE_STRING) {
                    excelArray[i][j] = cell.getStringCellValue();
                } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
                    excelArray[i][j] = String.valueOf(cell.getBooleanCellValue());
                } else if (cellType == Cell.CELL_TYPE_NUMERIC) {
                    excelArray[i][j] = String.valueOf(cell.getNumericCellValue());
                } else {
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
        final ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(tableArray));
        for (int i = 0; i < tableArray[0].length; i++) {
            final TableColumn<String[], String> tc = new TableColumn<>(tableArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(p -> new SimpleStringProperty((p.getValue()[colNo])));
            tc.setPrefWidth(90);
            this.mainView.getTableViewData().getColumns().add(tc);
        }
        this.mainView.getTableViewData().setItems(data);
    }

}
