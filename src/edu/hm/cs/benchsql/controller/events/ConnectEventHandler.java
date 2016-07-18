package edu.hm.cs.benchsql.controller.events;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.SqlConnection;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ConnectEventHandler implements EventHandler<ActionEvent> {

    private final MainView mainView;
    private final Model model;

    public ConnectEventHandler(final Model model, final MainView mainView) {
        this.mainView = mainView;
        this.model = model;
    }

    @Override
    public void handle(final ActionEvent event) {
        this.mainView.getButtonSqlServerSave().fire();
        final SqlConnection sqlConnection = this.model.getConnection(this.mainView.getComboBoxSqlServers().getValue());
        try {
            Class.forName(sqlConnection.getConnectionDriver()).newInstance();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}