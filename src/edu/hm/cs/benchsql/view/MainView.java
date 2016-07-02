package edu.hm.cs.benchsql.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainView {

    public void show(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlPath = getClass().getResource("MainView.fxml");
            String cssPath = getClass().getResource("MainView.css").toString();
            Parent root = FXMLLoader.load(xmlPath);
            Scene scene = new Scene(root, 300, 275);
            scene.getStylesheets().add(cssPath);
            primaryStage.setTitle("BenchSQL");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
