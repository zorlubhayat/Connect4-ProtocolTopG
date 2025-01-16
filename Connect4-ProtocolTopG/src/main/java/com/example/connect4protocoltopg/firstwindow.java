package com.example.connect4protocoltopg;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class firstwindow {

    private static LogicModule game;

    public firstwindow(LogicModule game) {
        this.game = game;

    }

    public static Stage createPopupWindow(String title) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        //window.getIcons().add(new Image("file:///C:/Users/kiki1/git/VierGewinnt/resources/images/iconfinder_Games_BoardGames_Artboard_28_3828857.png"));
        return window;
    }

    public static String getWinnerText() {
        if (game.getPlayer() == 1) {
            return game.getFirstPlayer() + " wins the game";
        } else if (game.getPlayer() == 2) {
            game.incrementScore(); // Score für Player 2 erhöhen
            return game.getSecondPlayer() + " wins the game";
        }
        return "Unknown winner";
    }

    void popupDraw() {
        GridPane gpane = new GridPane();
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Connect Four");

        //Set the application icon
        window.getIcons().add(new Image("file:///C:/Users/kiki1/git/VierGewinnt/resources/images/iconfinder_Games_BoardGames_Artboard_28_3828857.png"));

        Label label1 = new Label("Game Over");
        label1.setFont(new Font("Bernard MT Condensed", 45));
        label1.setTextFill(Color.web("#5D4E84"));
        gpane.add(label1, 0, 0);

        Label label2 = new Label("It's a draw game");
        label2.setFont(new Font("Bernard MT Condensed", 20));
        label2.setTextFill(Color.web("#5D4E84"));
        gpane.add(label2, 0, 1);

        //set Background color
        gpane.setStyle("-fx-background-color: #e5e5ff;");
        gpane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(label1, HPos.CENTER);
        GridPane.setHalignment(label2, HPos.CENTER);

        //Closes Window with Enter Key
        window.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (KeyCode.ENTER == event.getCode()) {
                window.close();
            }
        });

        Scene windowScene = new Scene(gpane, 450, 300);
        window.setScene(windowScene);
        window.show();

    }
}