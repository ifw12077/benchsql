package edu.hm.cs.benchsql.view;

import edu.hm.cs.benchsql.model.data.ImportAssignment;
import edu.hm.cs.benchsql.model.data.ImportData;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;

public class ComboBoxEditingCell extends TableCell<ImportAssignment, ImportData> {

    private ComboBox<ImportData> comboBox;
    private final ObservableList<ImportData> importData;

    public ComboBoxEditingCell(final ObservableList<ImportData> importData) {
        this.importData = importData;
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        this.setText(this.getTyp().getImportDataValue());
    }

    private void comboBoxConverter(final ComboBox<ImportData> comboBox) {
        comboBox.setCellFactory((c) -> {
            return new ListCell<ImportData>() {
                @Override
                protected void updateItem(final ImportData item, final boolean empty) {
                    super.updateItem(item, empty);

                    if ((item == null) || empty) {
                        this.setText(null);
                    } else {
                        this.setText(item.getImportDataValue());
                    }
                }
            };
        });
    }

    private void createComboBox() {
        this.comboBox = new ComboBox<>(this.importData);
        this.comboBoxConverter(this.comboBox);
        this.comboBox.valueProperty().set(this.getTyp());
        this.comboBox.setMinWidth(this.getWidth() - (this.getGraphicTextGap() * 2));
        this.comboBox.setOnAction((e) -> {
            this.commitEdit(this.comboBox.getSelectionModel().getSelectedItem());
        });
    }

    private ImportData getTyp() {
        return this.getItem() == null ? new ImportData("") : this.getItem();
    }

    @Override
    public void startEdit() {
        if (!this.isEmpty()) {
            super.startEdit();
            this.createComboBox();
            this.setText(null);
            this.setGraphic(this.comboBox);
        }
    }

    @Override
    public void updateItem(final ImportData item, final boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            this.setText(null);
            this.setGraphic(null);
        } else {
            if (this.isEditing()) {
                if (this.comboBox != null) {
                    this.comboBox.setValue(this.getTyp());
                }
                this.setText(this.getTyp().getImportDataValue());
                this.setGraphic(this.comboBox);
            } else {
                this.setText(this.getTyp().getImportDataValue());
                this.setGraphic(null);
            }
        }
    }

}