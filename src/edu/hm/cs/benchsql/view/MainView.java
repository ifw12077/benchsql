package edu.hm.cs.benchsql.view;

import edu.hm.cs.benchsql.model.data.ImportAssignment;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
    private final Scene scene;
    private final ScrollPane scrollPane;
    private final SplitPane splitPane;
    private final VBox vBoxConnection;
    private final HBox hBoxServer;
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
    private final TableView<ImportAssignment> tableViewImportAs;
    private final HBox hBoxImportAs;
    private final Label labelImportAs;
    private final ComboBox<String> comboBoxImportAs;
    private final HBox hBoxImport;
    private final CheckBox checkBoxImport;
    private final Label labelImport;
    private final TextField textFieldImport;
    private final Button buttonImport;
    private final HBox hBoxResult;
    private final Label labelResult;

    public MainView() {
        this.hBoxServer = new HBox();
        this.hBoxServer.setSpacing(10);
        this.hBoxServer.setPadding(new Insets(5));
        this.hBoxServer.setAlignment(Pos.CENTER);
        this.labels = new Label("Typ:");
        this.comboBoxTypes = new ComboBox<>(
                FXCollections.observableArrayList("MySQL", "Microsoft SQL", "SQL Anywhere"));
        this.hBoxServer.getChildren().addAll(this.labels, this.comboBoxTypes);

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

        this.hBoxImportAs = new HBox();
        this.hBoxImportAs.setSpacing(10);
        this.hBoxImportAs.setPadding(new Insets(5));
        this.hBoxImportAs.setAlignment(Pos.CENTER);
        this.labelImportAs = new Label("Importieren als:");
        this.comboBoxImportAs = new ComboBox<>();
        this.comboBoxImportAs.setDisable(true);
        this.hBoxImportAs.getChildren().addAll(this.labelImportAs, this.comboBoxImportAs);
        this.tableViewImportAs = new TableView<>();

        this.hBoxImport = new HBox();
        this.hBoxImport.setSpacing(10);
        this.hBoxImport.setPadding(new Insets(5));
        this.hBoxImport.setAlignment(Pos.CENTER);
        this.checkBoxImport = new CheckBox("Threaded");
        this.labelImport = new Label("Anzahl Datensätze:");
        this.textFieldImport = new TextField();
        this.buttonImport = new Button("Importieren");
        this.hBoxImport.getChildren().addAll(this.checkBoxImport, this.labelImport, this.textFieldImport,
                this.buttonImport);

        this.hBoxResult = new HBox();
        this.hBoxResult.setSpacing(10);
        this.hBoxResult.setPadding(new Insets(5));
        this.hBoxResult.setAlignment(Pos.CENTER);
        this.labelResult = new Label("");
        this.hBoxResult.getChildren().addAll(this.labelResult);

        this.splitPane = new SplitPane();
        this.scrollPane = new ScrollPane();
        this.scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        this.vBoxConnection = new VBox();
        this.scrollPane.setContent(this.vBoxConnection);
        this.vBoxConnection.getChildren().addAll(this.hBoxServer, this.hBoxIpPort, this.hBoxDatabase, this.hBoxLogin,
                this.hBoxConnect, new Separator(Orientation.HORIZONTAL), this.hBoxImportData,
                new Separator(Orientation.HORIZONTAL), this.hBoxImportAs, this.tableViewImportAs, this.hBoxImport,
                this.hBoxResult);
        this.tableViewData = new TableView<>();
        this.splitPane.getItems().addAll(this.scrollPane, this.tableViewData);
        this.scene = new Scene(this.splitPane);
    }

    public Button getButtonConnect() {
        return this.buttonConnect;
    }

    public Button getButtonImport() {
        return this.buttonImport;
    }

    public Button getButtonImportData() {
        return this.buttonImportData;
    }

    public Button getButtonSave() {
        return this.buttonSave;
    }

    public CheckBox getCheckBoxImport() {
        return this.checkBoxImport;
    }

    public ComboBox<String> getComboBoxImportAs() {
        return this.comboBoxImportAs;
    }

    public ComboBox<String> getComboBoxTypes() {
        return this.comboBoxTypes;
    }

    public Label getLabelConnect() {
        return this.labelConnect;
    }

    public Label getlabelImportData() {
        return this.labelImportData;
    }

    public Label getLabelResult() {
        return this.labelResult;
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

    public TableView<ImportAssignment> getTableViewImportAs() {
        return this.tableViewImportAs;
    }

    public TextField getTextFieldDatabase() {
        return this.textFieldDatabase;
    }

    public TextField getTextFieldImport() {
        return this.textFieldImport;
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
        primaryStage.setMaximized(true);
        primaryStage.setTitle("benchSQL");
        primaryStage.setScene(this.scene);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("./../resources/sql-icon.png")));
        primaryStage.show();
        this.splitPane.setDividerPosition(0, 0.25);
    }
}
