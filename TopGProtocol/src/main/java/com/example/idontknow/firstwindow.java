package com.example.idontknow;

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class firstwindow {
    //------------------------------------------------------------------------------ Baran

    private LogicModule game; // Instanz der Spiellogik (nicht static, damit es spezifisch für ein Spiel ist)

    // Konstruktor der Klasse, der eine Referenz auf die Spiellogik (`LogicModule`) entgegennimmt
    public firstwindow(LogicModule game) {
        this.game = game;
    }


    /**
     * Erstellt ein Popup-Fenster mit einem gegebenen Titel.
     * @param title Der Titel des Fensters.
     * @return Das erstellte `Stage`-Objekt.
     */
    public static Stage createPopupWindow(String title) {
        Stage window = new Stage(); // Neues Fenster erstellen
        window.initModality(Modality.APPLICATION_MODAL); // Blockiert andere Fenster, bis dieses geschlossen wird
        window.setTitle(title); // Setzt den Titel des Fensters
        //window.getIcons().add(new Image("file:///Pfad/zum/Icon.png")); // Optional: Fenstericon setzen
        return window;
    }

    /**
     * Zeigt ein Popup-Fenster für ein Unentschieden an.
     */
    void popupDraw() {
        // GridPane wird für das Layout des Fensters verwendet
        GridPane gpane = new GridPane();
        Stage window = new Stage(); // Neues Fenster erstellen
        window.initModality(Modality.APPLICATION_MODAL); // Blockiert andere Fenster, bis dieses geschlossen wird
        window.setTitle("Connect Four"); // Setzt den Titel des Fensters

        // Label für "Game Over"
        Label label1 = new Label("Game Over");
        label1.setFont(new Font("Bernard MT Condensed", 45)); // Schriftart und -größe setzen
        label1.setTextFill(Color.web("#5D4E84")); // Schriftfarbe setzen
        gpane.add(label1, 0, 0); // Label zum GridPane hinzufügen (erste Zeile, erste Spalte)

        // Label für "It's a draw game"
        Label label2 = new Label("It's a draw game");
        label2.setFont(new Font("Bernard MT Condensed", 20)); // Schriftart und -größe setzen
        label2.setTextFill(Color.web("#5D4E84")); // Schriftfarbe setzen
        gpane.add(label2, 0, 1); // Label zum GridPane hinzufügen (zweite Zeile, erste Spalte)

        // Hintergrundfarbe des GridPane setzen
        gpane.setStyle("-fx-background-color: #e5e5ff;");
        gpane.setAlignment(Pos.CENTER); // Zentriert alle Elemente im GridPane
        GridPane.setHalignment(label1, HPos.CENTER); // Zentriert das erste Label horizontal
        GridPane.setHalignment(label2, HPos.CENTER); // Zentriert das zweite Label horizontal

        // Event-Handler: Schließt das Fenster, wenn die ENTER-Taste gedrückt wird
        window.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (KeyCode.ENTER == event.getCode()) { // Prüft, ob die ENTER-Taste gedrückt wurde
                window.close(); // Schließt das Fenster
            }
        });

        // Erstellt eine Szene mit dem GridPane und setzt sie für das Fenster
        Scene windowScene = new Scene(gpane, 450, 300); // Fenstergröße 450x300 Pixel
        window.setScene(windowScene);

        // Zeigt das Fenster an
        window.show();
    }



}
