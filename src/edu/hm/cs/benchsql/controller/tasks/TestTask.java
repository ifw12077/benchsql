package edu.hm.cs.benchsql.controller.tasks;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.data.TestAssignment;
import edu.hm.cs.benchsql.view.MainView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;

public class TestTask extends Task<Void> {

    private final Model model;
    private final MainView mainView;
    private String profileType;
    private long[] statisticArray;
    private int size = 0;

    public TestTask(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
        super.messageProperty()
                .addListener((obs, oldMessage, newMessage) -> this.mainView.getLabelTest().setText(newMessage));
        super.setOnSucceeded(event -> this.mainView.getLabelTest()
                .setText(this.model.getConnectedTo() + " (" + this.size + " Ergebnisse):\nLesen von " + this.profileType
                        + " (100 Durchgänge)\nMinimalzeit: " + this.model.getTimeString(this.statisticArray[0])
                        + "\nMaximalzeit: " + this.model.getTimeString(this.statisticArray[1]) + "\nDurchschnitt: "
                        + this.model.getTimeString(this.statisticArray[2])));
    }

    @Override
    protected Void call() throws Exception {
        if (!"".equals(this.model.getConnectedTo())) {
            this.profileType = this.mainView.getComboBoxTestFor().getValue();
            if (this.profileType != null) {
                final long[] timeArray = new long[this.model.percent];
                for (int i = 0; i < this.model.percent; i++) {
                    final long startTime = System.currentTimeMillis();
                    this.readObjects(this.profileType);
                    final long endTime = System.currentTimeMillis();
                    timeArray[i] = endTime - startTime;
                    this.updateMessage("Durchgang " + (i + 1) + " abgeschlossen.");
                }
                this.statisticArray = this.model.getStatistics(timeArray);
            } else {
                Platform.runLater(() -> this.model.showDialog(AlertType.WARNING, "Fehler beim Messen",
                        "Kein Profiltyp gewählt", "Bitte wählen sie einen Profiltyp!"));
            }
        } else {
            Platform.runLater(() -> this.model.showDialog(AlertType.WARNING, "Fehler beim Messen",
                    "Serververbindung fehlt", "Bitte verbinden Sie sich mit einem Server!"));
        }
        return null;
    }

    private void readMsqlObjects(final String profileType, final ObservableList<TestAssignment> testAssignments,
            final Connection connection) {
        CallableStatement statement = null;
        ResultSet resultSet = null;
        String result = "";
        try {
            statement = connection.prepareCall("CALL SETARGUMENT('TARGETPROFILETYPECODE',?)");
            statement.setString(1, profileType);
            statement.execute();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
            statement.close();
            if ("0000".equals(result)) {
                statement = connection.prepareCall("CALL SETARGUMENT('SOURCEPROFILETYPECODE','USER')");
                statement.execute();
                resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                resultSet.close();
                statement.close();
                if ("0000".equals(result)) {
                    statement = connection.prepareCall("CALL SETARGUMENT('SOURCEOBJECTID', '1')");
                    statement.execute();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    statement.close();
                    if ("0000".equals(result)) {
                        statement = connection.prepareCall("CALL SETARGUMENT('MODULEITEMCODE', ?)");
                        statement.setString(1, "MI-" + profileType + "LIST");
                        statement.execute();
                        resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        resultSet.close();
                        statement.close();
                        if ("0000".equals(result)) {
                            for (final TestAssignment testAssignment : testAssignments) {
                                final String propGrpropPropattr = testAssignment.getPropGrpPropAttributeCode();
                                final String value = testAssignment.getValue();
                                if (!"".equals(value.trim())) {
                                    statement = connection.prepareCall("CALL ADDARGUMENT('FILTERARGUMENT', ?, ?);");
                                    statement.setString(1, propGrpropPropattr);
                                    statement.setString(2, value);
                                    statement.execute();
                                    resultSet = statement.getResultSet();
                                    if (resultSet.next()) {
                                        result = resultSet.getString(1);
                                    }
                                    resultSet.close();
                                    statement.close();
                                }
                            }
                            if ("0000".equals(result)) {
                                statement = connection.prepareCall("CALL READOBJECTS()");
                                statement.execute();
                                resultSet = statement.getResultSet();
                                if (this.size == 0) {
                                    while (resultSet.next()) {
                                        this.size++;
                                    }
                                }
                                resultSet.close();
                                statement.close();
                            }
                        }
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    private void readObjects(final String profileType) {
        final String connectionString = this.model.getConnectedTo();
        final ObservableList<TestAssignment> testAssignments = this.mainView.getTableViewTestFor().getItems();
        try {
            if ("MySQL".equals(connectionString)) {
                this.readMsqlObjects(profileType, testAssignments, this.model.getConnection(connectionString));
            } else if ("Microsoft SQL".equals(connectionString)) {
                this.readTsqlObjects(profileType, testAssignments, this.model.getConnection(connectionString));
            } else if ("SQL Anywhere".equals(connectionString)) {
                this.readSsqlObjects(profileType, testAssignments, this.model.getConnection(connectionString));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void readSsqlObjects(final String profileType, final ObservableList<TestAssignment> testAssignments,
            final Connection connection) {
        CallableStatement callStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result = "";
        try {
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_arguments (id int NOT NULL IDENTITY, propertygroup long nvarchar NULL, propertykey long nvarchar NULL, propertyvalue long nvarchar NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_member (id int NOT NULL IDENTITY, memberid int NULL, workstatus long nvarchar NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_contract (id int NOT NULL IDENTITY, contractid int NULL, workstatus long nvarchar NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_source (id int NOT NULL IDENTITY, sourceid int NULL, workstatus long nvarchar NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_target (id int NOT NULL IDENTITY, targetid int NULL, workstatus long nvarchar NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_sysarguments (id int NOT NULL IDENTITY, argkey long nvarchar NULL, argvalue long nvarchar NULL)");
            statement.close();
            callStatement = connection.prepareCall("EXEC SETARGUMENT 'CURRENTPROFILETYPECODE', ?");
            callStatement.setString(1, profileType);
            callStatement.execute();
            resultSet = callStatement.getResultSet();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
            callStatement.close();
            if ("0000".equals(result)) {
                callStatement = connection.prepareCall("EXEC SETARGUMENT 'SOURCEPROFILETYPECODE', 'USER'");
                callStatement.execute();
                resultSet = callStatement.getResultSet();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                resultSet.close();
                callStatement.close();
                if ("0000".equals(result)) {
                    callStatement = connection.prepareCall("EXEC SETARGUMENT 'SOURCEOBJECTID','1'");
                    callStatement.execute();
                    resultSet = callStatement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    callStatement.close();
                    if ("0000".equals(result)) {
                        callStatement = connection.prepareCall("EXEC SETARGUMENT 'MODULEITEMCODE', ?");
                        callStatement.setString(1, "MI-" + profileType + "LIST");
                        callStatement.execute();
                        resultSet = callStatement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        resultSet.close();
                        callStatement.close();
                        if ("0000".equals(result)) {
                            for (final TestAssignment testAssignment : testAssignments) {
                                final String propGrpropPropattr = testAssignment.getPropGrpPropAttributeCode();
                                final String value = testAssignment.getValue();
                                if (!"".equals(value.trim())) {
                                    callStatement = connection.prepareCall("EXEC ADDARGUMENT 'FILTERARGUMENT', ?, ?");
                                    callStatement.setString(1, propGrpropPropattr);
                                    callStatement.setString(2, value);
                                    callStatement.execute();
                                    resultSet = callStatement.getResultSet();
                                    if (resultSet.next()) {
                                        result = resultSet.getString(1);
                                    }
                                    resultSet.close();
                                    callStatement.close();
                                }
                            }
                            if ("0000".equals(result)) {
                                callStatement = connection.prepareCall("EXEC READOBJECTS");
                                callStatement.execute();
                                resultSet = callStatement.getResultSet();
                                resultSet.close();
                                callStatement.close();
                            }
                        }
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    private void readTsqlObjects(final String profileType, final ObservableList<TestAssignment> testAssignments,
            final Connection connection) {
        CallableStatement callStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result = "";
        try {
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE #tmp_arguments (id int NOT NULL IDENTITY, propertygroup nvarchar(max) NULL, propertykey nvarchar(max) NULL, propertyvalue nvarchar(max) NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE #tmp_member (id int NOT NULL IDENTITY, memberid int NULL, workstatus nvarchar(30) NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE #tmp_contract (id int NOT NULL IDENTITY, contractid int NULL, workstatus nvarchar(30) NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE #tmp_source (id int NOT NULL IDENTITY, sourceid int NULL, workstatus nvarchar(30) NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE #tmp_target (id int NOT NULL IDENTITY, targetid int NULL, workstatus nvarchar(30) NULL)");
            statement.close();
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE #tmp_sysarguments (id int NOT NULL IDENTITY, argkey nvarchar(30) NULL, argvalue nvarchar(max) NULL)");
            statement.close();
            callStatement = connection.prepareCall("EXEC SETARGUMENT 'TARGETPROFILETYPECODE', ?");
            callStatement.setString(1, profileType);
            callStatement.execute();
            resultSet = callStatement.getResultSet();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
            callStatement.close();
            if ("0000".equals(result)) {
                callStatement = connection.prepareCall("EXEC SETARGUMENT 'SOURCEPROFILETYPECODE', 'USER'");
                callStatement.execute();
                resultSet = callStatement.getResultSet();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                resultSet.close();
                callStatement.close();
                if ("0000".equals(result)) {
                    callStatement = connection.prepareCall("EXEC SETARGUMENT 'SOURCEOBJECTID','1'");
                    callStatement.execute();
                    resultSet = callStatement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    callStatement.close();
                    if ("0000".equals(result)) {
                        callStatement = connection.prepareCall("EXEC SETARGUMENT 'MODULEITEMCODE', ?");
                        callStatement.setString(1, "MI-" + profileType + "LIST");
                        callStatement.execute();
                        resultSet = callStatement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        resultSet.close();
                        callStatement.close();
                        if ("0000".equals(result)) {
                            for (final TestAssignment testAssignment : testAssignments) {
                                final String propGrpropPropattr = testAssignment.getPropGrpPropAttributeCode();
                                final String value = testAssignment.getValue();
                                if (!"".equals(value.trim())) {
                                    callStatement = connection.prepareCall("EXEC ADDARGUMENT 'FILTERARGUMENT', ?, ?");
                                    callStatement.setString(1, propGrpropPropattr);
                                    callStatement.setString(2, value);
                                    callStatement.execute();
                                    resultSet = callStatement.getResultSet();
                                    if (resultSet.next()) {
                                        result = resultSet.getString(1);
                                    }
                                    resultSet.close();
                                    callStatement.close();
                                }
                            }
                            if ("0000".equals(result)) {
                                callStatement = connection.prepareCall("EXEC READOBJECTS");
                                callStatement.execute();
                                resultSet = callStatement.getResultSet();
                                if (this.size == 0) {
                                    while (resultSet.next()) {
                                        this.size++;
                                    }
                                }
                                resultSet.close();
                                callStatement.close();
                            }
                        }
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

}
