package edu.hm.cs.benchsql.controller.tasks;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.model.data.ImportAssignment;
import edu.hm.cs.benchsql.view.MainView;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ImportProfileTask extends Task<Void> {

    private final int index;
    private final Model model;
    private final MainView mainView;
    private final String profileType;

    public ImportProfileTask(final Model model, final MainView mainView, final String profileType, final int index) {
        this.model = model;
        this.mainView = mainView;
        this.profileType = profileType;
        this.index = index;
    }

    @Override
    protected Void call() throws Exception {
        final Integer objectId = this.createObject(this.profileType);
        if (objectId != null) {
            this.storeprofile(this.index, this.profileType, objectId);
        }
        return null;
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
                        if ("0000".equals(result)) {
                            objectId = Integer.parseInt(parts[1]);
                        }
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
        final String connectionString = this.model.getConnectedTo();
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
                    callStatement = connection.prepareCall("EXEC SETARGUMENT 'AGENT', '0'");
                    callStatement.execute();
                    resultSet = callStatement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    callStatement.close();
                    if ("0000".equals(result)) {
                        callStatement = connection.prepareCall("EXEC CREATEOBJECT");
                        resultSet = callStatement.executeQuery();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        final String[] parts = result.split(";");
                        result = parts[0];
                        if ("0000".equals(result)) {
                            objectId = Integer.parseInt(parts[1]);
                        }
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
                        if ("0000".equals(result)) {
                            objectId = Integer.parseInt(parts[1]);
                        }
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

    private String getTableColumnValueByName(final TableView<String[]> tableView, final String name, final int index) {
        for (final TableColumn<String[], ?> col : tableView.getColumns()) {
            if (col.getText().equals(name)) {
                return (String) col.getCellData(index);
            }
        }
        return "";
    }

    private void storeMsqlProfile(final String profileType, final Integer objectId, final String propGrpropPropattr,
            final String importDataValue, final Connection connection) {
        CallableStatement statement = null;
        ResultSet resultSet = null;
        String result = "";
        try {
            statement = connection.prepareCall("CALL SETARGUMENT('CURRENTPROFILETYPECODE', ?)");
            statement.setString(1, profileType);
            statement.execute();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }
            resultSet.close();
            statement.close();
            if ("0000".equals(result)) {
                statement = connection.prepareCall("CALL setargument('CURRENTOBJECTID', ?)");
                statement.setLong(1, objectId);
                statement.execute();
                resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                resultSet.close();
                statement.close();
                if ("0000".equals(result)) {
                    statement = connection.prepareCall("CALL setargument('CURRENTPROPGRPPROPPROPATTR', ?)");
                    statement.setString(1, propGrpropPropattr);
                    statement.execute();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    statement.close();
                    if ("0000".equals(result)) {
                        statement = connection.prepareCall("CALL setargument('CURRENTVALUE', ?)");
                        statement.setString(1, importDataValue);
                        statement.execute();
                        resultSet = statement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        resultSet.close();
                        statement.close();
                        if ("0000".equals(result)) {
                            statement = connection.prepareCall("CALL storeprofile()");
                            statement.execute();
                            resultSet = statement.getResultSet();
                            if (resultSet.next()) {
                                result = resultSet.getString(1);
                            }
                            resultSet.close();
                            statement.close();
                        }
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    private void storeprofile(final int itemNumber, final String profileType, final Integer objectId) {
        final String connectionString = this.model.getConnectedTo();
        final ObservableList<ImportAssignment> importAssignments = this.mainView.getTableViewImportAs().getItems();
        for (final ImportAssignment importAssignment : importAssignments) {
            final String propGrpropPropattr = importAssignment.getPropGrpPropAttributeCode();
            final String importData = importAssignment.getImportData().getImportDataValue();
            if (!"".equals(importData)) {
                String importDataValue = this.getTableColumnValueByName(this.mainView.getTableViewData(), importData,
                        itemNumber);
                if (importDataValue == null) {
                    importDataValue = "";
                }
                try {
                    if ("MySQL".equals(connectionString)) {
                        this.storeMsqlProfile(profileType, objectId, propGrpropPropattr, importDataValue,
                                this.model.getConnection(connectionString));
                    } else if ("Microsoft SQL".equals(connectionString)) {
                        this.storeTsqlProfile(profileType, objectId, propGrpropPropattr, importDataValue,
                                this.model.getConnection(connectionString));
                    } else if ("SQL Anywhere".equals(connectionString)) {
                        this.storeSsqlProfile(profileType, objectId, propGrpropPropattr, importDataValue,
                                this.model.getConnection(connectionString));
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void storeSsqlProfile(final String profileType, final Integer objectId, final String propGrpropPropattr,
            final String importDataValue, final Connection connection) {
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
                callStatement = connection.prepareCall("EXEC setargument 'CURRENTOBJECTID', ?");
                callStatement.setLong(1, objectId);
                callStatement.execute();
                resultSet = callStatement.getResultSet();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                resultSet.close();
                callStatement.close();
                if ("0000".equals(result)) {
                    callStatement = connection.prepareCall("EXEC setargument 'CURRENTPROPGRPPROPPROPATTR', ?");
                    callStatement.setString(1, propGrpropPropattr);
                    callStatement.execute();
                    resultSet = callStatement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    callStatement.close();
                    if ("0000".equals(result)) {
                        callStatement = connection.prepareCall("EXEC setargument 'CURRENTVALUE', ?");
                        callStatement.setString(1, importDataValue);
                        callStatement.execute();
                        resultSet = callStatement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        resultSet.close();
                        callStatement.close();
                        if ("0000".equals(result)) {
                            callStatement = connection.prepareCall("EXEC storeprofile");
                            callStatement.execute();
                            resultSet = callStatement.getResultSet();
                            if (resultSet.next()) {
                                result = resultSet.getString(1);
                            }
                            resultSet.close();
                            callStatement.close();
                        }
                    }
                }
            }
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    private void storeTsqlProfile(final String profileType, final Integer objectId, final String propGrpropPropattr,
            final String importDataValue, final Connection connection) {
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
                callStatement = connection.prepareCall("EXEC setargument 'CURRENTOBJECTID', ?");
                callStatement.setLong(1, objectId);
                callStatement.execute();
                resultSet = callStatement.getResultSet();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                }
                resultSet.close();
                callStatement.close();
                if ("0000".equals(result)) {
                    callStatement = connection.prepareCall("EXEC setargument 'CURRENTPROPGRPPROPPROPATTR', ?");
                    callStatement.setString(1, propGrpropPropattr);
                    callStatement.execute();
                    resultSet = callStatement.getResultSet();
                    if (resultSet.next()) {
                        result = resultSet.getString(1);
                    }
                    resultSet.close();
                    callStatement.close();
                    if ("0000".equals(result)) {
                        callStatement = connection.prepareCall("EXEC setargument 'CURRENTVALUE', ?");
                        callStatement.setString(1, importDataValue);
                        callStatement.execute();
                        resultSet = callStatement.getResultSet();
                        if (resultSet.next()) {
                            result = resultSet.getString(1);
                        }
                        resultSet.close();
                        callStatement.close();
                        if ("0000".equals(result)) {
                            callStatement = connection.prepareCall("EXEC storeprofile");
                            callStatement.execute();
                            resultSet = callStatement.getResultSet();
                            if (resultSet.next()) {
                                result = resultSet.getString(1);
                            }
                            resultSet.close();
                            callStatement.close();
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
