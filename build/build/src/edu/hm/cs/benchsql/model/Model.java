package edu.hm.cs.benchsql.model;

import javafx.stage.Stage;

public class Model {
    private final Stage primaryStage;

    public Model(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
}
