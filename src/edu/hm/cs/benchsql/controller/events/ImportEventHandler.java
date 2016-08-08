package edu.hm.cs.benchsql.controller.events;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ImportEventHandler implements EventHandler<ActionEvent> {

    private final Model model;
    private final MainView mainView;

    public ImportEventHandler(final Model model, final MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    private Integer createMsqlObject(final String profileType, final Connection connection) {
        CallableStatement statement = null;
        ResultSet resultSet = null;
        Integer objectId = null;
        String result = "";
        try {
            statement = connection.prepareCall("CALL SETARGUMENT('CURRENTPROFILETYPECODE',?)");
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
                    statement = connection.prepareCall("CALL SETARGUMENT('AGENT','0')");
                    statement.execute();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    statement.close();
                    if ("0000".equals(result)) {
                        statement = connection.prepareCall("CALL CREATEOBJECT()");
                        statement.execute();
                        resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        final String[] parts = result.split(";");
                        result = parts[0];
                        objectId = Integer.parseInt(parts[1]);
                        resultSet.close();
                        statement.close();
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return objectId;
    }

    private Integer createObject(final String profileType) {
        final String connectionString = this.mainView.getComboBoxTypes().getValue();
        Integer objectId = null;
        try {
            if ("MySQL".equals(connectionString)) {
                objectId = this.createMsqlObject(profileType, this.model.getConnection(connectionString));
            } else if ("Microsoft SQL".equals(connectionString)) {
                objectId = this.createTsqlObject(profileType, this.model.getConnection(connectionString));
            } else if ("SQL Anywhere".equals(connectionString)) {
                objectId = this.createSsqlObject(profileType, this.model.getConnection(connectionString));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return objectId;
    }

    private Integer createSsqlObject(final String profileType, final Connection connection) {
        CallableStatement callStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Integer objectId = null;
        String result = "";
        try {
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_arguments (id int NOT NULL IDENTITY, propertygroup nvarchar(max) NULL, propertykey nvarchar(max) NULL, propertyvalue nvarchar(max) NULL, CONSTRAINT PK_tmp_arguments_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_member (id int NOT NULL IDENTITY, memberid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_member_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_contract (id int NOT NULL IDENTITY, contractid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_contract_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_source (id int NOT NULL IDENTITY, sourceid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_source_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_target (id int NOT NULL IDENTITY, targetid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_target_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_sysarguments (id int NOT NULL IDENTITY, argkey nvarchar(30) NULL, argvalue nvarchar(max) NULL, CONSTRAINT PK_tmp_sysarguments_id PRIMARY KEY (id))");
            statement.close();
            callStatement = connection.prepareCall("CALL SETARGUMENT('CURRENTPROFILETYPECODE',?)");
            callStatement.setString(1, profileType);
            callStatement.execute();
            resultSet = callStatement.getResultSet();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
            callStatement.close();
            if ("0000".equals(result)) {
                callStatement = connection.prepareCall("CALL SETARGUMENT('SOURCEPROFILETYPECODE','USER')");
                callStatement.execute();
                resultSet = callStatement.getResultSet();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                resultSet.close();
                callStatement.close();
                if ("0000".equals(result)) {
                    callStatement = connection.prepareCall("CALL SETARGUMENT('AGENT','0')");
                    callStatement.execute();
                    resultSet = callStatement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    callStatement.close();
                    if ("0000".equals(result)) {
                        callStatement = connection.prepareCall("CALL CREATEOBJECT()");
                        callStatement.execute();
                        resultSet = callStatement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        final String[] parts = result.split(";");
                        result = parts[0];
                        objectId = Integer.parseInt(parts[1]);
                        resultSet.close();
                        callStatement.close();
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return objectId;
    }

    private Integer createTsqlObject(final String profileType, final Connection connection) {
        CallableStatement callStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Integer objectId = null;
        String result = "";
        try {
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_arguments (id int NOT NULL IDENTITY, propertygroup nvarchar(max) NULL, propertykey nvarchar(max) NULL, propertyvalue nvarchar(max) NULL, CONSTRAINT PK_tmp_arguments_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_member (id int NOT NULL IDENTITY, memberid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_member_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_contract (id int NOT NULL IDENTITY, contractid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_contract_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_source (id int NOT NULL IDENTITY, sourceid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_source_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_target (id int NOT NULL IDENTITY, targetid int NULL, workstatus nvarchar(30) NULL, CONSTRAINT PK_tmp_target_id PRIMARY KEY (id))");
            statement.close();
            statement = connection.createStatement();
            statement.executeQuery(
                    "CREATE TABLE #tmp_sysarguments (id int NOT NULL IDENTITY, argkey nvarchar(30) NULL, argvalue nvarchar(max) NULL, CONSTRAINT PK_tmp_sysarguments_id PRIMARY KEY (id))");
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
                    callStatement = connection.prepareCall("EXEC SETARGUMENT 'AGENT','0'");
                    callStatement.execute();
                    resultSet = callStatement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    callStatement.close();
                    if ("0000".equals(result)) {
                        callStatement = connection.prepareCall("EXEC CREATEOBJECT");
                        callStatement.execute();
                        resultSet = callStatement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        final String[] parts = result.split(";");
                        result = parts[0];
                        objectId = Integer.parseInt(parts[1]);
                        resultSet.close();
                        callStatement.close();
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return objectId;
    }

    @Override
    public void handle(final ActionEvent arg0) {
        final String profileType = this.mainView.getComboBoxImportAs().getValue();
        final Integer importCount = Integer.parseInt(this.mainView.getTextFieldImport().getText());
        Integer importedCount = 0;

        while (importedCount < importCount) {
            final Integer objectId = this.createObject(profileType);
            System.out.println(objectId);

            importedCount++;
            System.out.println(importedCount);
        }
    }
}
