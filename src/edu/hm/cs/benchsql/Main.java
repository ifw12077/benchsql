package edu.hm.cs.benchsql;

import edu.hm.cs.benchsql.controller.MainVC;
import edu.hm.cs.benchsql.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final Model model = new Model(primaryStage);
        final MainVC mainVC = new MainVC(model);
        mainVC.show();
    }
}
