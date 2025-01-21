package com.example.idontknow;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class template {
    //------------------------------------------------------------------------------ Baran
    public static Label createStyledLabel(String text, int fontSize, String color) {
        // Erstelle ein neues Label mit dem übergebenen Text
        Label label = new Label(text);

        // Setze die Schriftart und -größe
        label.setFont(Font.font("Bernard MT Condensed", fontSize));

        // Setze die Schriftfarbe basierend auf dem übergebenen HEX-Wert
        label.setTextFill(Color.web(color));

        // Zentriere den Text im Label
        label.setAlignment(Pos.CENTER);

        // Gib das konfigurierte Label zurück
        return label;
    }
}
