package edu.hm.cs.benchsql.controller.events;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.data.TestAssignment;
import edu.hm.cs.benchsql.view.MainView;
import edu.hm.cs.benchsql.view.cells.TextFieldEditingCell;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ChangeTestEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ChangeTestEventHandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent arg0) {
        this.mainView.getTableViewTestFor().getItems().clear();
        this.mainView.getTableViewTestFor().getColumns().clear();
        this.model.getTestPropGrpPropAttributeCodes().clear();

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
                            + this.mainView.getComboBoxTestFor().getValue()
                            + "')) order by propgrppropattributecode asc");
            while (resultSet.next()) {
                this.model.getTestPropGrpPropAttributeCodes().add(resultSet.getString("propgrppropattributecode"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        final Callback<TableColumn<TestAssignment, String>, TableCell<TestAssignment, String>> cellFactory = p -> new TextFieldEditingCell();
        final TableColumn<TestAssignment, String> tc1 = new TableColumn<>("PropGrpPropAttributeCode");
        tc1.setCellValueFactory(new PropertyValueFactory<TestAssignment, String>("propGrpPropAttributeCode"));
        tc1.setEditable(false);
        final TableColumn<TestAssignment, String> tc2 = new TableColumn<>("Wert");
        tc2.setCellValueFactory(new PropertyValueFactory<TestAssignment, String>("value"));
        tc2.setCellFactory(cellFactory);
        tc2.setEditable(true);
        this.mainView.getTableViewTestFor().setEditable(true);
        this.mainView.getTableViewTestFor().getColumns().setAll(tc1, tc2);
        final ObservableList<TestAssignment> data = FXCollections.observableArrayList();
        for (final String propGrpPropAttributeCode : this.model.getTestPropGrpPropAttributeCodes()) {
            data.add(new TestAssignment(propGrpPropAttributeCode, ""));
        }
        this.mainView.getTableViewTestFor().setItems(data);
        this.mainView.getTableViewTestFor().setFixedCellSize(25);
        this.mainView.getTableViewTestFor().prefHeightProperty().bind(this.mainView.getTableViewTestFor()
                .fixedCellSizeProperty().multiply(Bindings.size(this.mainView.getTableViewTestFor().getItems())));
        this.mainView.getTableViewTestFor().minHeightProperty()
                .bind(this.mainView.getTableViewTestFor().prefHeightProperty().add(25));
        this.mainView.getTableViewTestFor().maxHeightProperty()
                .bind(this.mainView.getTableViewTestFor().prefHeightProperty().add(25));
    }
}
