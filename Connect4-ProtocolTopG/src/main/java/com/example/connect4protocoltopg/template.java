package com.example.connect4protocoltopg;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class template {
    public static Label createStyledLabel(String text, int fontSize, String color) {
        Label label = new Label(text);
        label.setFont(Font.font("Bernard MT Condensed", fontSize));
        label.setTextFill(Color.web(color));
        label.setAlignment(Pos.CENTER);
        return label;
    }
}
