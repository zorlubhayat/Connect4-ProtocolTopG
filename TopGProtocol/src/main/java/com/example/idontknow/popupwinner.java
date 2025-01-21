package com.example.idontknow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class popupwinner {

    // Diese Methode zeigt ein Popup-Fenster an, um den Gewinner anzuzeigen
    public static void popupWinner(LogicModule game) {
        // Erstellt ein neues Popup-Fenster mit dem Titel "Connect Four"
        Stage window = firstwindow.createPopupWindow("Connect Four");

        // Erstes Bild hinzufügen (z. B. Connect Four-Logo)
        Image image1 = new Image("/com/example/Idontknow/Daco_1087247.png"); // Pfad zum ersten Bild
        ImageView imageView1 = new ImageView(image1);
        imageView1.setFitWidth(300); // Setzt die Breite des Bildes auf 300 Pixel
        imageView1.setPreserveRatio(true); // Behält das Seitenverhältnis des Bildes bei

        // Zweites Bild hinzufügen (z. B. Trophäenbild)
        Image image2 = new Image("/com/example/Idontknow/Trophe.png"); // Pfad zum zweiten Bild
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(200); // Setzt die Breite des Bildes auf 200 Pixel
        imageView2.setPreserveRatio(true); // Behält das Seitenverhältnis des Bildes bei

        // Titel-Label (Anzeige von "CONGRATULATIONS")
        Label title = template.createStyledLabel("CONGRATULATIONS", 24, "#0000FF");

        // Gewinner-Label (Anzeige des Siegers basierend auf dem aktuellen Spieler)
        String winnerText = (game.getPlayer() == 1)
                ? game.getFirstPlayer() + " wins the game" // Spieler 1 hat gewonnen
                : game.getSecondPlayer() + " wins the game"; // Spieler 2 hat gewonnen
        Label winnerLabel = template.createStyledLabel(winnerText, 20, "#0000FF");

        // Layout konfigurieren:
        // VBox arrangiert die Elemente vertikal
        VBox layout = new VBox(20, imageView1, imageView2, title, winnerLabel); // Elemente hinzufügen
        layout.setAlignment(Pos.TOP_CENTER); // Zentriert alle Elemente oben
        layout.setStyle("-fx-background-color: #ADD8E6;"); // Setzt den Hintergrund des Layouts auf Hellblau
        layout.setPadding(new Insets(20)); // Fügt Innenabstände von 20 Pixeln hinzu

        // Szene erstellen und dem Fenster zuweisen
        Scene scene = new Scene(layout, 500, 600); // Setzt die Größe der Szene auf 500x600 Pixel
        window.setScene(scene);

        // Event-Handler hinzufügen, um das Fenster mit der ENTER-Taste zu schließen
        window.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) { // Wenn die ENTER-Taste gedrückt wird
                window.close(); // Fenster schließen
            }
        });

        // Zeigt das Fenster an
        window.show();
    }
}
