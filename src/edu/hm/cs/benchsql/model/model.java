package edu.hm.cs.benchsql.model;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private final Stage primaryStage;
    private final Map<String, String> namePwMap;

    public Model(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.namePwMap = new HashMap<>();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Map<String, String> getNamePwMap() {
        return namePwMap;
    }
}
