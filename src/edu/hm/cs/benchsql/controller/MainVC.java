package edu.hm.cs.benchsql.controller;

import edu.hm.cs.benchsql.model.Model;
import edu.hm.cs.benchsql.view.MainView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class MainVC {

    private final Model model;
    private final MainView mainView;

    public MainVC(final Model model) {
        this.model = model;
        this.mainView = new MainView();
        this.setOnAction();
    }

    private void setOnAction() {
        this.mainView.getMenuItemOpen().setOnAction(new OpenEventHandler(this.model, this.mainView));

        this.mainView.getMenuItemAbout().setOnAction(event -> {
            final Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("\u00dcber benchSQL");
            alert.setHeaderText("\u00a9 by Jan Opitz");
            alert.setContentText("Version: 2016-07-04");
            alert.getButtonTypes().setAll(new ButtonType("OK", ButtonData.CANCEL_CLOSE));
            alert.showAndWait();
        });

        this.mainView.getMenuItemQuit().setOnAction(event -> Platform.exit());
    }

    public void show() {
        this.mainView.show(this.model.getPrimaryStage());
    }
}
