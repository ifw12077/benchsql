package edu.hm.cs.benchsql.controller.events;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ChangeImportEventhandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ChangeImportEventhandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    @Override
    public void handle(final ActionEvent event) {
        try {
            this.loadPropGrpPropAttributeCodes(this.model.getConnection(this.model.getConnectedTo()));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

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

    }
}