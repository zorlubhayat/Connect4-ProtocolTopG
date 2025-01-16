package com.example.connect4protocoltopg;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class popupwinner {

    public static void popupWinner() {


        //ganz oben als Erstes
        Stage window = firstwindow.createPopupWindow("Connect Four");

        // Titel-Label und Anordnung
        //Label title = createStyledLabel("CONGRATULATIONS", 50, "#0000FF");
        Label title = template.createStyledLabel("CONGRATULATIONS", 24, "#0000FF");
        title.setScaleY(1);

        // Gewinner-Label,der Name wird Hier angezeigt der gewonnen hat
        Label winnerLabel = template.createStyledLabel(firstwindow.getWinnerText(), 20, "#0000FF");

        // Layout konfigurieren
        VBox layout = new VBox(20, title, winnerLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ADD8E6;");
        layout.setPadding(new Insets(20));

        //Erstellung und Anzeige der Szene:
        Scene scene = new Scene(layout, 500, 450);
        window.setScene(scene);

        // Fenster schließen mit Enter-Taste
        window.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                window.close();
            }
        });

        window.show();
    }

}

