package edu.hm.cs.benchsql.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private final HBox hBoxs;
    private final Label labels;
    private final ComboBox<String> comboBoxTypes;
    private final HBox hBoxIpPort;
    private final Label labelIp;
    private final TextField textFieldIp;
    private final Label labelPort;
    private final TextField textFieldPort;
    private final HBox hBoxDatabase;
    private final Label labelInstance;
    private final TextField textFieldInstance;
    private final Label labelDatabase;
    private final TextField textFieldDatabase;
    private final HBox hBoxLogin;
    private final Label labelUser;
    private final TextField textFieldUser;
    private final Label labelPasswort;
    private final PasswordField passwordFieldPasswort;
    private final HBox hBoxConnect;
    private final Button buttonSave;
    private final Label labelConnect;
    private final Button buttonConnect;
    private final HBox hBoxImportData;
    private final Label labelImportData;
    private final Button buttonImportData;
    private final TableView<String[]> tableViewData;

    public MainView() {
        this.hBoxs = new HBox();
        this.hBoxs.setSpacing(10);
        this.hBoxs.setPadding(new Insets(5));
        this.hBoxs.setAlignment(Pos.CENTER);
        this.labels = new Label("Typ:");
        this.comboBoxTypes = new ComboBox<>(
                FXCollections.observableArrayList("MySQL", "Microsoft SQL", "SQL Anywhere"));
        this.hBoxs.getChildren().addAll(this.labels, this.comboBoxTypes);

        this.hBoxIpPort = new HBox();
        this.hBoxIpPort.setSpacing(10);
        this.hBoxIpPort.setPadding(new Insets(5));
        this.hBoxIpPort.setAlignment(Pos.CENTER);
        this.labelIp = new Label("IP/Name:");
        this.textFieldIp = new TextField();
        this.labelPort = new Label("Port:");
        this.textFieldPort = new TextField();
        this.hBoxIpPort.getChildren().addAll(this.labelIp, this.textFieldIp, new Separator(Orientation.VERTICAL),
                this.labelPort, this.textFieldPort);

        this.hBoxDatabase = new HBox();
        this.hBoxDatabase.setSpacing(10);
        this.hBoxDatabase.setPadding(new Insets(5));
        this.hBoxDatabase.setAlignment(Pos.CENTER);
        this.labelInstance = new Label("Instanz:");
        this.textFieldInstance = new TextField();
        this.labelDatabase = new Label("Datenbankname:");
        this.textFieldDatabase = new TextField();
        this.hBoxDatabase.getChildren().addAll(this.labelInstance, this.textFieldInstance,
                new Separator(Orientation.VERTICAL), this.labelDatabase, this.textFieldDatabase);

        this.hBoxLogin = new HBox();
        this.hBoxLogin.setSpacing(10);
        this.hBoxLogin.setPadding(new Insets(5));
        this.hBoxLogin.setAlignment(Pos.CENTER);
        this.labelUser = new Label("User:");
        this.textFieldUser = new TextField();
        this.labelPasswort = new Label("Passwort:");
        this.passwordFieldPasswort = new PasswordField();
        this.hBoxLogin.getChildren().addAll(this.labelUser, this.textFieldUser, new Separator(Orientation.VERTICAL),
                this.labelPasswort, this.passwordFieldPasswort);

        this.hBoxConnect = new HBox();
        this.hBoxConnect.setSpacing(10);
        this.hBoxConnect.setPadding(new Insets(5));
        this.hBoxConnect.setAlignment(Pos.CENTER);
        this.buttonSave = new Button("Speichern");
        this.labelConnect = new Label("");
        this.buttonConnect = new Button("Verbinden");
        this.hBoxConnect.getChildren().addAll(this.buttonSave, this.labelConnect, this.buttonConnect);

        this.hBoxImportData = new HBox();
        this.hBoxImportData.setSpacing(10);
        this.hBoxImportData.setPadding(new Insets(5));
        this.hBoxImportData.setAlignment(Pos.CENTER);
        this.labelImportData = new Label("");
        this.buttonImportData = new Button("Daten öffnen");
        this.hBoxImportData.getChildren().addAll(this.labelImportData, this.buttonImportData);

        this.splitPane = new SplitPane();
        this.vBoxConnection = new VBox();
        this.vBoxConnection.getChildren().addAll(this.hBoxs, this.hBoxIpPort, this.hBoxDatabase, this.hBoxLogin,
                this.hBoxConnect, new Separator(Orientation.HORIZONTAL), this.hBoxImportData);
        this.tableViewData = new TableView<>();
        this.splitPane.getItems().addAll(this.vBoxConnection, this.tableViewData);
        this.scene = new Scene(this.splitPane);
    }

    public Button getButtonConnect() {
        return this.buttonConnect;
    }

    public Button getButtonImportData() {
        return this.buttonImportData;
    }

    public Button getButtonSave() {
        return this.buttonSave;
    }

    public ComboBox<String> getComboBoxTypes() {
        return this.comboBoxTypes;
    }

    public Label getlabelImportData() {
        return this.labelImportData;
    }

    public PasswordField getPasswordFieldPasswort() {
        return this.passwordFieldPasswort;
    }

    public SplitPane getSplitPane() {
        return this.splitPane;
    }

    public TableView<String[]> getTableViewData() {
        return this.tableViewData;
    }

    public TextField getTextFieldDatabase() {
        return this.textFieldDatabase;
    }

    public TextField getTextFieldInstance() {
        return this.textFieldInstance;
    }

    public TextField getTextFieldIp() {
        return this.textFieldIp;
    }

    public TextField getTextFieldPort() {
        return this.textFieldPort;
    }

    public TextField getTextFieldUser() {
        return this.textFieldUser;
    }

    public void show(final Stage primaryStage) {
        primaryStage.setTitle("benchSQL");
        primaryStage.setScene(this.scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
