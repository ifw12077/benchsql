package edu.hm.cs.benchsql;

import edu.hm.cs.benchsql.controller.MainVC;
import edu.hm.cs.benchsql.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model(primaryStage);
        MainVC mainVC = new MainVC(model);
        mainVC.show();
    }
}
