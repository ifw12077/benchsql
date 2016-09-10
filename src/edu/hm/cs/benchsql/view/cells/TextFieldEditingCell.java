package edu.hm.cs.benchsql.view.cells;

import java.util.ArrayList;
import java.util.List;

import edu.hm.cs.benchsql.model.data.TestAssignment;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class TextFieldEditingCell extends TableCell<TestAssignment, String> {
    private TextField textField;

    public TextFieldEditingCell() {

    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        this.setText(this.getItem());
        this.setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    private void createTextField() {
        this.textField = new TextField(this.getString());
        this.textField.setMinWidth(this.getWidth() - (this.getGraphicTextGap() * 2));
        this.textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                TextFieldEditingCell.this.commitEdit(TextFieldEditingCell.this.textField.getText());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                TextFieldEditingCell.this.cancelEdit();
            } else if (t.getCode() == KeyCode.TAB) {
                TextFieldEditingCell.this.commitEdit(TextFieldEditingCell.this.textField.getText());
                final TableColumn<TestAssignment, ?> nextColumn = TextFieldEditingCell.this
                        .getNextColumn(!t.isShiftDown());
                if (nextColumn != null) {
                    TextFieldEditingCell.this.getTableView().edit(TextFieldEditingCell.this.getTableRow().getIndex(),
                            nextColumn);
                }
            }
        });
        this.textField.focusedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
            if (!newValue && (TextFieldEditingCell.this.textField != null)) {
                TextFieldEditingCell.this.commitEdit(TextFieldEditingCell.this.textField.getText());
            }
        });
    }

    private List<TableColumn<TestAssignment, ?>> getLeaves(final TableColumn<TestAssignment, ?> root) {
        final List<TableColumn<TestAssignment, ?>> columns = new ArrayList<>();
        if (root.getColumns().isEmpty()) {
            if (root.isEditable()) {
                columns.add(root);
            }
            return columns;
        } else {
            for (final TableColumn<TestAssignment, ?> column : root.getColumns()) {
                columns.addAll(this.getLeaves(column));
            }
            return columns;
        }
    }

    private TableColumn<TestAssignment, ?> getNextColumn(final boolean forward) {
        final List<TableColumn<TestAssignment, ?>> columns = new ArrayList<>();
        for (final TableColumn<TestAssignment, ?> column : this.getTableView().getColumns()) {
            columns.addAll(this.getLeaves(column));
        }
        if (columns.size() < 2) {
            return null;
        }
        final int currentIndex = columns.indexOf(this.getTableColumn());
        int nextIndex = currentIndex;
        if (forward) {
            nextIndex++;
            if (nextIndex > (columns.size() - 1)) {
                nextIndex = 0;
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
            }
        }
        return columns.get(nextIndex);
    }

    private String getString() {
        return this.getItem() == null ? "" : this.getItem().toString();
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (this.textField == null) {
            this.createTextField();
        }
        this.setGraphic(this.textField);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Platform.runLater(() -> {
            TextFieldEditingCell.this.textField.requestFocus();
            TextFieldEditingCell.this.textField.selectAll();
        });
    }

    @Override
    public void updateItem(final String item, final boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            this.setText(null);
            this.setGraphic(null);
        } else {
            if (this.isEditing()) {
                if (this.textField != null) {
                    this.textField.setText(this.getString());
                }
                this.setGraphic(this.textField);
                this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                this.setText(this.getString());
                this.setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

}
