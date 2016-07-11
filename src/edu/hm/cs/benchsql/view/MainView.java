package edu.hm.cs.benchsql.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {

    private final Scene scene;
    private final SplitPane splitPane;
    private final VBox vBoxConnection;
    private final HBox hBoxSqlServers;
    private final Label labelSqlServers;
    private final ComboBox<String> comboBoxSqlServers;
    private final HBox hBoxSqlServerIp;
    private final Label labelSqlServerIp;
    private final TextField textFieldSqlServerIp;
    private final HBox hBoxSqlServerPort;
    private final Label labelSqlServerPort;
    private final TextField textFieldSqlServerPort;
    private final HBox hBoxSqlServerUser;
    private final Label labelSqlServerUser;
    private final TextField textFieldSqlServerUser;
    private final HBox hBoxSqlServerPasswort;
    private final Label labelSqlServerPasswort;
    private final TextField textFieldSqlServerPasswort;
    private final HBox hBoxSqlServerConnect;
    private final Label labelSqlServerConnect;
    private final Button buttonSqlServerConnect;
    private final HBox hBoxImportData;
    private final Label labelImportData;
    private final Button buttonImportData;
    private final TableView<String> tableViewData;

    public MainView() {
        this.hBoxSqlServers = new HBox();
        this.hBoxSqlServers.setSpacing(10);
        this.hBoxSqlServers.setPadding(new Insets(5));
        this.hBoxSqlServers.setAlignment(Pos.CENTER);
        this.labelSqlServers = new Label("SQL-Server Typ:");
        this.comboBoxSqlServers = new ComboBox<>(
                FXCollections.observableArrayList("MySQL", "Microsoft SQL", "SQL Anywhere"));
        this.hBoxSqlServers.getChildren().addAll(this.labelSqlServers, this.comboBoxSqlServers);

        this.hBoxSqlServerIp = new HBox();
        this.hBoxSqlServerIp.setSpacing(10);
        this.hBoxSqlServerIp.setPadding(new Insets(5));
        this.hBoxSqlServerIp.setAlignment(Pos.CENTER);
        this.labelSqlServerIp = new Label("SQL-Server IP/Name:");
        this.textFieldSqlServerIp = new TextField();
        this.hBoxSqlServerIp.getChildren().addAll(this.labelSqlServerIp, this.textFieldSqlServerIp);

        this.hBoxSqlServerPort = new HBox();
        this.hBoxSqlServerPort.setSpacing(10);
        this.hBoxSqlServerPort.setPadding(new Insets(5));
        this.hBoxSqlServerPort.setAlignment(Pos.CENTER);
        this.labelSqlServerPort = new Label("SQL-Server Port:");
        this.textFieldSqlServerPort = new TextField();
        this.hBoxSqlServerPort.getChildren().addAll(this.labelSqlServerPort, this.textFieldSqlServerPort);

        this.hBoxSqlServerUser = new HBox();
        this.hBoxSqlServerUser.setSpacing(10);
        this.hBoxSqlServerUser.setPadding(new Insets(5));
        this.hBoxSqlServerUser.setAlignment(Pos.CENTER);
        this.labelSqlServerUser = new Label("SQL-Server User:");
        this.textFieldSqlServerUser = new TextField();
        this.hBoxSqlServerUser.getChildren().addAll(this.labelSqlServerUser, this.textFieldSqlServerUser);

        this.hBoxSqlServerPasswort = new HBox();
        this.hBoxSqlServerPasswort.setSpacing(10);
        this.hBoxSqlServerPasswort.setPadding(new Insets(5));
        this.hBoxSqlServerPasswort.setAlignment(Pos.CENTER);
        this.labelSqlServerPasswort = new Label("SQL-Server Passwort:");
        this.textFieldSqlServerPasswort = new TextField();
        this.hBoxSqlServerPasswort.getChildren().addAll(this.labelSqlServerPasswort, this.textFieldSqlServerPasswort);

        this.hBoxSqlServerConnect = new HBox();
        this.hBoxSqlServerConnect.setSpacing(10);
        this.hBoxSqlServerConnect.setPadding(new Insets(5));
        this.hBoxSqlServerConnect.setAlignment(Pos.CENTER);
        this.labelSqlServerConnect = new Label("Keine Verbindung!");
        this.buttonSqlServerConnect = new Button("Verbinden");
        this.hBoxSqlServerConnect.getChildren().addAll(this.labelSqlServerConnect, this.buttonSqlServerConnect);

        this.hBoxImportData = new HBox();
        this.hBoxImportData.setSpacing(10);
        this.hBoxImportData.setPadding(new Insets(5));
        this.hBoxImportData.setAlignment(Pos.CENTER);
        this.labelImportData = new Label("Keine Daten!");
        this.buttonImportData = new Button("Daten öffnen");
        this.hBoxImportData.getChildren().addAll(this.labelImportData, this.buttonImportData);

        this.splitPane = new SplitPane();
        this.vBoxConnection = new VBox();
        this.vBoxConnection.getChildren().addAll(this.hBoxSqlServers, this.hBoxSqlServerIp, this.hBoxSqlServerPort,
                this.hBoxSqlServerUser, this.hBoxSqlServerPasswort, this.hBoxSqlServerConnect,
                new Separator(Orientation.HORIZONTAL), this.hBoxImportData);
        this.tableViewData = new TableView<>();
        this.splitPane.getItems().addAll(this.vBoxConnection, this.tableViewData);
        this.scene = new Scene(this.splitPane, 1024, 768);
    }

    public Button getButtonImportData() {
        return this.buttonImportData;
    }

    public ComboBox<String> getComboBoxSqlServers() {
        return this.comboBoxSqlServers;
    }

    public TableView<String> getTableViewData() {
        return this.tableViewData;
    }

    public void show(final Stage primaryStage) {
        primaryStage.setTitle("benchSQL");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}
