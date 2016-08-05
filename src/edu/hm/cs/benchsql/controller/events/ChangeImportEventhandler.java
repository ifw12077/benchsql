package edu.hm.cs.benchsql.controller.events;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.data.ImportAssignment;
import edu.hm.cs.benchsql.model.data.ImportData;
import edu.hm.cs.benchsql.view.ComboBoxEditingCell;
import edu.hm.cs.benchsql.view.MainView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ChangeImportEventhandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ChangeImportEventhandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent event) {
        this.mainView.getTableViewImportAs().getItems().clear();
        this.mainView.getTableViewImportAs().getColumns().removeAll(this.mainView.getTableViewImportAs().getColumns());

        try {
            this.loadPropGrpPropAttributeCodes(this.model.getConnection(this.model.getConnectedTo()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadPropGrpPropAttributeCodes(final Connection connection) {

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT propgrppropattributecode FROM v_pri_propgrppropattributecode where propertygroupid = (select propertygroupid from pri_propertygroupprofiletype where profiletypeid = (Select profiletypeid from pri_profiletype where profiletypecode = '"
                            + this.mainView.getComboBoxImportAs().getValue()
                            + "')) order by propgrppropattributecode asc");
            while (resultSet.next()) {
                this.model.getPropGrpPropAttributeCodes().add(resultSet.getString("propgrppropattributecode"));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        final TableColumn<ImportAssignment, String> tc1 = new TableColumn<>("PropGrpPropAttributeCode");
        final TableColumn<ImportAssignment, ImportData> tc2 = new TableColumn<>("ImportData");

        tc1.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPropGrpPropAttributeCode()));
        tc2.setCellValueFactory(cellData -> cellData.getValue().getImportData());
        final Callback<TableColumn<ImportAssignment, ImportData>, TableCell<ImportAssignment, ImportData>> comboBoxCellFactory = (
                final TableColumn<ImportAssignment, ImportData> param) -> new ComboBoxEditingCell(
                        this.model.getImportData());
        tc2.setCellFactory(comboBoxCellFactory);
        tc2.setOnEditCommit((final TableColumn.CellEditEvent<ImportAssignment, ImportData> t) -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setAssignment(t.getNewValue());
        });
        this.mainView.getTableViewImportAs().setEditable(true);
        tc1.setEditable(false);
        tc2.setEditable(true);
        this.mainView.getTableViewImportAs().getColumns().addAll(tc1, tc2);
        final ObservableList<ImportAssignment> data = FXCollections.observableArrayList();
        for (final String propGrpPropAttributeCode : this.model.getPropGrpPropAttributeCodes()) {
            data.add(new ImportAssignment(propGrpPropAttributeCode, this.model.getImportData().get(0)));
        }
        this.mainView.getTableViewImportAs().setItems(data);
    }
}