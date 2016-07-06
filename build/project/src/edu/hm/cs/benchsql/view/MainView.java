package edu.hm.cs.benchsql.view;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {

    private final Scene scene;
    private final VBox vBoxMain;
    private final MenuBar menuBar;
    private final Menu menuFile;
    private final MenuItem menuItemOpen;
    private final SeparatorMenuItem separatorMenuItem;
    private final MenuItem menuItemQuit;
    private final Menu menuHelp;
    private final MenuItem menuItemAbout;
    private final SplitPane splitPane;
    private final VBox vBoxConnection;
    private final ScrollPane scrollPaneData;
    private final HBox hBox;

    public MainView() {
        this.vBoxMain = new VBox();

        this.menuBar = new MenuBar();
        this.menuFile = new Menu("Datei");
        this.menuItemOpen = new MenuItem("Öffnen");
        this.separatorMenuItem = new SeparatorMenuItem();
        this.menuItemQuit = new MenuItem("Beenden");
        this.menuFile.getItems().addAll(this.menuItemOpen, this.separatorMenuItem,
                this.menuItemQuit);
        this.menuHelp = new Menu("Hilfe");
        this.menuItemAbout = new MenuItem("Über");
        this.menuHelp.getItems().addAll(this.menuItemAbout);
        this.menuBar.getMenus().addAll(this.menuFile, this.menuHelp);

        this.splitPane = new SplitPane();
        this.vBoxConnection = new VBox();
        this.scrollPaneData = new ScrollPane();

        this.splitPane.getItems().addAll(this.vBoxConnection, this.scrollPaneData);

        this.hBox = new HBox();

        VBox.setVgrow(this.menuBar, Priority.NEVER);
        VBox.setVgrow(this.splitPane, Priority.ALWAYS);
        VBox.setVgrow(this.hBox, Priority.NEVER);
        this.vBoxMain.getChildren().addAll(this.menuBar, this.splitPane, this.hBox);
        this.scene = new Scene(this.vBoxMain, 1024, 768);
    }

    public MenuItem getMenuItemAbout() {
        return this.menuItemAbout;
    }

    public MenuItem getMenuItemOpen() {
        return this.menuItemOpen;
    }

    public MenuItem getMenuItemQuit() {
        return this.menuItemQuit;
    }

    public void show(final Stage primaryStage) {
        primaryStage.setTitle("benchSQL");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
}
