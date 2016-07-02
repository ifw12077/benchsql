package edu.hm.cs.benchsql.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Jan on 02.07.2016.
 */
public class AusgabeView {

    private Scene scene;

    private GridPane grid;
    private Text scenetitle;

    private ListView<String> list;

    private Button backBtn;
    private HBox hbBtn;

    public AusgabeView() {
        // Layout
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Ueberschrift
        scenetitle = new Text("AxxG-Leser Übersicht");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // Liste
        list = new ListView<>();
        list.setMinWidth(200);
        grid.add(list, 0, 1);

        // Button
        backBtn = new Button("zurück");

        // Buttongruppe
        hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(backBtn);
        grid.add(hbBtn, 0, 2);

        scene = new Scene(grid, 300, 300);
    }

    public void show(Stage stage) {
        stage.setTitle("AxxG - JavaFX MVC Beispiel");
        stage.setScene(scene);
        stage.show();
    }

    public ListView<String> getList() {
        return list;
    }

    public Button getBackBtn() {
        return backBtn;
    }
}