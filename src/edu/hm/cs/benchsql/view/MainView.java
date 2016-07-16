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
    private final HBox hBoxSqlServerIpPort;
    private final Label labelSqlServerIp;
    private final TextField textFieldSqlServerIp;
    private final Label labelSqlServerPort;
    private final TextField textFieldSqlServerPort;
    private final HBox hBoxSqlServerLogin;
    private final Label labelSqlServerUser;
    private final TextField textFieldSqlServerUser;
    private final Label labelSqlServerPasswort;
    private final TextField textFieldSqlServerPasswort;
    private final HBox hBoxSqlServerConnect;
    private final Button buttonSqlServerSave;
    private final Label labelSqlServerConnect;
    private final Button buttonSqlServerConnect;
    private final HBox hBoxImportData;
    private final Label labelImportData;
    private final Button buttonImportData;
    private final TableView<String[]> tableViewData;

    public MainView() {
        this.hBoxSqlServers = new HBox();
        this.hBoxSqlServers.setSpacing(10);
        this.hBoxSqlServers.setPadding(new Insets(5));
        this.hBoxSqlServers.setAlignment(Pos.CENTER);
        this.labelSqlServers = new Label("SQL-Server Typ:");
        this.comboBoxSqlServers = new ComboBox<>(
                FXCollections.observableArrayList("MySQL", "Microsoft SQL", "SQL Anywhere"));
        this.hBoxSqlServers.getChildren().addAll(this.labelSqlServers, this.comboBoxSqlServers);

        this.hBoxSqlServerIpPort = new HBox();
        this.hBoxSqlServerIpPort.setSpacing(10);
        this.hBoxSqlServerIpPort.setPadding(new Insets(5));
        this.hBoxSqlServerIpPort.setAlignment(Pos.CENTER);
        this.labelSqlServerIp = new Label("SQL-Server IP/Name:");
        this.textFieldSqlServerIp = new TextField();
        this.labelSqlServerPort = new Label("SQL-Server Port:");
        this.textFieldSqlServerPort = new TextField();
        this.hBoxSqlServerIpPort.getChildren().addAll(this.labelSqlServerIp, this.textFieldSqlServerIp,
                new Separator(Orientation.VERTICAL), this.labelSqlServerPort, this.textFieldSqlServerPort);

        this.hBoxSqlServerLogin = new HBox();
        this.hBoxSqlServerLogin.setSpacing(10);
        this.hBoxSqlServerLogin.setPadding(new Insets(5));
        this.hBoxSqlServerLogin.setAlignment(Pos.CENTER);
        this.labelSqlServerUser = new Label("SQL-Server User:");
        this.textFieldSqlServerUser = new TextField();
        this.labelSqlServerPasswort = new Label("SQL-Server Passwort:");
        this.textFieldSqlServerPasswort = new TextField();
        this.hBoxSqlServerLogin.getChildren().addAll(this.labelSqlServerUser, this.textFieldSqlServerUser,
                new Separator(Orientation.VERTICAL), this.labelSqlServerPasswort, this.textFieldSqlServerPasswort);

        this.hBoxSqlServerConnect = new HBox();
        this.hBoxSqlServerConnect.setSpacing(10);
        this.hBoxSqlServerConnect.setPadding(new Insets(5));
        this.hBoxSqlServerConnect.setAlignment(Pos.CENTER);
        this.buttonSqlServerSave = new Button("Speichern");
        this.labelSqlServerConnect = new Label("Keine Verbindung!");
        this.buttonSqlServerConnect = new Button("Verbinden");
        this.hBoxSqlServerConnect.getChildren().addAll(this.buttonSqlServerSave, this.labelSqlServerConnect,
                this.buttonSqlServerConnect);

        this.hBoxImportData = new HBox();
        this.hBoxImportData.setSpacing(10);
        this.hBoxImportData.setPadding(new Insets(5));
        this.hBoxImportData.setAlignment(Pos.CENTER);
        this.labelImportData = new Label("Keine Daten!");
        this.buttonImportData = new Button("Daten öffnen");
        this.hBoxImportData.getChildren().addAll(this.labelImportData, this.buttonImportData);

        this.splitPane = new SplitPane();
        this.vBoxConnection = new VBox();
        this.vBoxConnection.getChildren().addAll(this.hBoxSqlServers, this.hBoxSqlServerIpPort, this.hBoxSqlServerLogin,
                this.hBoxSqlServerConnect, new Separator(Orientation.HORIZONTAL), this.hBoxImportData);
        this.tableViewData = new TableView<>();
        this.splitPane.getItems().addAll(this.vBoxConnection, this.tableViewData);
        this.scene = new Scene(this.splitPane);
    }

    public Button getButtonImportData() {
        return this.buttonImportData;
    }

    public Button getButtonSqlServerSave() {
        return this.buttonSqlServerSave;
    }

    public ComboBox<String> getComboBoxSqlServers() {
        return this.comboBoxSqlServers;
    }

    public Label getlabelImportData() {
        return this.labelImportData;
    }

    public SplitPane getSplitPane() {
        return this.splitPane;
    }

    public TableView<String[]> getTableViewData() {
        return this.tableViewData;
    }

    public void show(final Stage primaryStage) {
        primaryStage.setTitle("benchSQL");
        primaryStage.setScene(this.scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
