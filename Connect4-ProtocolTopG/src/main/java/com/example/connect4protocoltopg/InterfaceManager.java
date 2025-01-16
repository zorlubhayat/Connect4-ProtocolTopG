package com.example.connect4protocoltopg;

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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

public class InterfaceManager {

    private BorderPane root;
    private Circle[][] circle;
    private Button[] button;
    private Label[] text;



    LogicModule game = new LogicModule();
    firstwindow pop = new firstwindow(game);


    public InterfaceManager() {
        this.root = new BorderPane();
        this.circle = new Circle[7][6];
        this.button = new Button[7];
        this.text = new Label[4];
    }

    public void setupLayout() {
        root.setTop(createTopPane());
        root.setCenter(createCenterPane());
        root.setLeft(getLeftHBox());
        root.setRight(getRightHBox());
        root.setBottom(createBottomPane());
        root.setStyle("-fx-background-color: #ADD8E6;"); // Hell blau
    }

    public Scene setupScene(Stage stage) {
        if (stage.getScene() != null) {
            return stage.getScene(); // Wenn bereits eine Szene existiert, zurückgeben
        }
        Scene scene = new Scene(root, 1000, 780);
        stage.setTitle("Connect Four");
        //stage.setScene(scene);
        //stage.getIcons().add(new Image("file:///path/to/icon.png"));
        return scene;
    }

    private Pane createTopPane() {

        // Bild hinzufügen
        Image image = new Image(getClass().getResource("/com/example/Idontknow/Daco_1087247.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        // Größe des Bildes anpassen
        imageView.setFitWidth(300); // Breite des Bildes
        imageView.setPreserveRatio(true); // Seitenverhältnis beibehalten
        imageView.setSmooth(true); // Glatte Kanten
        imageView.setCache(true); // Bild cachen für bessere Performance


        // Das Bild zentrieren
        HBox topPane = new HBox(imageView);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(10)); // Optionaler Abstand

        return topPane;
    }
    //-------------------------------------------------------------

    Pane createCenterPane() {
        GridPane gpane = new GridPane(); // Layoutmanager der ein Gitter erstellt
        // Jede Zelle des Gitters wird mit einem Rechteck (Rectangle) und einem Kreis (Circle) belegt.
        for (int column = 0; column < 6; column++) {
            for (int row = 0; row < 7; row++) {
                Rectangle rect = new Rectangle(70, 70);//Jedes Feld im Raster ist ein Rechteck mit: Größe: 70 × 70 Pixel.
                rect.setStroke(Color.BLUE);// Rahmenfarbe
                rect.setFill(null);// Keine Füllfarbe
                rect.setStrokeWidth(3);// Rahmendicke
                gpane.add(rect, row, column);

                circle[row][column] = new Circle(0, 0, 25);// Kreis mit Radius 25
                circle[row][column].setStroke(Color.TRANSPARENT);// Unsichtbare Umrandung
                circle[row][column].setStrokeWidth(5);// Breite der (unsichtbaren) Umrandung
                gpane.add(circle[row][column], row, column);
                circle[row][column].setVisible(false);// Standardmäßig unsichtbar
                GridPane.setHalignment(circle[row][column], HPos.CENTER);
            }
        }

        gpane.setAlignment(Pos.TOP_CENTER);

        // Buttons initialisieren
        for (int i = 0; i < 7; i++) {
            if (button[i] == null) {
                button[i] = new Button("#" + (i + 1));
                button[i].setFont(Font.font("Bernard MT Condensed", 15));
                button[i].setTextFill(Color.web("#5D4E84"));
                button[i].setStyle(
                        "-fx-background-color: #79CDF0;" + // Lila Hintergrund
                                "-fx-text-fill: #0000FF;" +        // Dunkellila Schrift
                                "-fx-border-color: transparent;" +// Keine Umrandung standardmäßig
                                "-fx-border-width: 2;" +          // Rahmenbreite (unsichtbar)
                                "-fx-border-radius: 5;"           // Abgerundete Ecken
                );
                button[i].setMinSize(50, 25);

                int column = i;
                button[i].setOnAction(event -> setOnAction(column));
            }
        }

        // MouseClick-Handler hinzufügen
        gpane.setOnMouseClicked(event -> {
            double gridStartX = gpane.localToScene(0, 0).getX();
            int column = (int) ((event.getSceneX() - gridStartX) / 85);

            if (column >= 0 && column < 7 && game.points[column] < 6) {
                setOnAction(column);
            }

        });

        // MouseHover für Spalten-Highlight (genaue Berechnung mit LayoutX)
        gpane.setOnMouseMoved(event -> {
            double gridStartX = gpane.localToScene(0, 0).getX();
            int column = (int) ((event.getSceneX() - gridStartX) / 90);

            // Alle Buttons zurücksetzen Diese Schleife setzt das Styling aller Buttons auf den ursprünglichen Zustand zurück (lila Hintergrund ohne Umrandung).
            for (int i = 0; i < 7; i++) {
                button[i].setStyle("-fx-background-color: #79CDF0 ");
            }
            // Aktuelle Spalte hervorheben
            if (column >= 0 && column < 7) {

                button[column].setStyle(
                        "-fx-background-color: #79CDF0 ;" + // Lila Hintergrund bleibt gleich
                                "-fx-text-fill: #0000FF;" +        // Dunkellila Schrift
                                "-fx-border-color: #0000FF;" +     // Dunkelblaue Umrandung
                                "-fx-border-width: 2;" +           // Rahmenbreite (2 Pixel)
                                "-fx-border-radius: 5;"            // Abgerundete Ecken
                );
            }
        });

        HBox hbox = new HBox(22);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20, 10, 10, 10));
        for (int i = 0; i < 7; i++) {
            hbox.getChildren().add(button[i]);
        }
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hbox, gpane);

        return vbox;
    }
    //-------------------------------------------------------------
    VBox getLeftHBox()	{// Alles was auf der Linken Seite passiert

        text[0] = new Label("Player 1:");
        text[1] = new Label(game.getFirstPlayer());
        text[2] = new Label("Player 2:");
        text[3] = new Label(game.getSecondPlayer());

        for (int i=0; i<4; i++) {
            text[i].setPadding(new Insets(10, 10, 10, 10));
            text[i].setFont(new Font("Bernard MT Condensed", 20));
            text[i].setTextFill(Color.web("#0000FF"));
        }

        text[1].setFont(Font.font("Bernard MT Condensed",30));
        text[1].setTextFill(Color.RED);
        text[3].setFont(Font.font("Bernard MT Condensed",30));
        text[3].setTextFill(Color.YELLOW);

        //Erstellen des Layouts:
        VBox vbox = new VBox(text[0], text[1] , text[2], text[3]);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMinWidth(200);
        return vbox;
    }
    //-------------------------------------------------------------
    public GridPane getRightHBox() {
        // Spielerinformationen erstellen
        Label player1Label = template.createStyledLabel(game.getFirstPlayer(),20,"#0000FF");
        Label separator1 = template.createStyledLabel("  :  ",20,"#0000FF");
        Label player2Label = template.createStyledLabel(game.getSecondPlayer(),20,"#0000FF");
        Label player1Wins = template.createStyledLabel(String.valueOf(game.getWinsFirstPlayer()),20,"#0000FF"); // Immer aktuellen Wert holen
        Label separator2 = template.createStyledLabel("  :  ",20,"#0000FF");
        Label player2Wins = template.createStyledLabel(String.valueOf(game.getWinsSecondPlayer()),20,"#0000FF"); // Immer aktuellen Wert holen

        // Layout konfigurieren
        GridPane gridPane = new GridPane();
        gridPane.add(player1Label, 0, 0);
        gridPane.add(separator1, 1, 0);
        gridPane.add(player2Label, 2, 0);
        gridPane.add(player1Wins, 0, 1);
        gridPane.add(separator2, 1, 1);
        gridPane.add(player2Wins, 2, 1);

        // Zentriere jeden Inhalt in der Zelle
        GridPane.setHalignment(player1Label, HPos.CENTER);
        GridPane.setHalignment(separator1, HPos.CENTER);
        GridPane.setHalignment(player2Label, HPos.CENTER);
        GridPane.setHalignment(player1Wins, HPos.CENTER);
        GridPane.setHalignment(separator2, HPos.CENTER);
        GridPane.setHalignment(player2Wins, HPos.CENTER);

        // Zentriere das gesamte GridPane im rechten Bereich
        gridPane.setAlignment(Pos.CENTER);

        // Mindestgröße und Abstand
        gridPane.setMinSize(200, 500);
        gridPane.setPadding(new Insets(20));

        return gridPane;
    }
    //-------------------------------------------------------------
    public HBox createBottomPane() {
        // Button for new Game
        Button newGame = new Button("new Game");
        newGame.setFont(Font.font("Bernard MT Condensed", 20));
        newGame.setStyle(
                "-fx-background-color: #79CDF0;" + // Lila Hintergrund
                        "-fx-text-fill: #0000FF;" +        // Dunkellila Schrift
                        "-fx-border-color: transparent;" +// Keine Umrandung im Standard
                        "-fx-border-width: 2;" +           // Rahmenbreite (2 Pixel)
                        "-fx-border-radius: 5;"            // Abgerundete Ecken
        );

        newGame.setOnAction(event -> {
            game.resetGame();
            resetPitch();
            for (int i = 0; i < 7; i++) {
                button[i].setDisable(false);
            }
            //root.setRight(getRightHBox()); // Rechte Anzeige neu setzen
            markPlayer();
        });


        newGame.setOnKeyPressed(event -> { // Wenn du Enter tippst dann mach zu halb reset
            if (event.getCode() == KeyCode.ENTER) {
                game.resetGame();
                resetPitch();
                for (int i = 0; i < 7; i++) {
                    button[i].setDisable(false);
                }
                //root.setRight(getRightHBox());
                markPlayer();
            }
        });

        // Hover-Effekt für newGame, Wenn du deine Maus darüber hälst dann Hover es und wenn du weg gehts dann nicht
        newGame.setOnMouseEntered(e -> newGame.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: #0000FF;" + // Dunkelblaue Umrandung beim Hover
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));
        newGame.setOnMouseExited(e -> newGame.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" + // Keine Umrandung zurücksetzen
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));

        // Button für Exit Game
        Button exit = new Button("Exit");
        exit.setFont(Font.font("Bernard MT Condensed", 20));
        exit.setTextFill(Color.web("#5D4E84"));
        exit.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        );

        //Beendet das Spiel und schließt die Anwendung
        exit.setOnAction(event -> game.exitGame());

        //Führt dieselbe Aktion wie ein Mausklick aus.
        exit.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                game.exitGame();
            }
        });

        // Hover-Effekt für exit, Wenn du deine Maus darüber hälst dann Hover es und wenn du weg gehts dann nicht
        exit.setOnMouseEntered(e -> exit.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: #0000FF;" + // Dunkelblaue Umrandung beim Hover
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));
        exit.setOnMouseExited(e -> exit.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" + // Keine Umrandung zurücksetzen
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));

        // Initials of the Crew, Erstellt ein Label mit einer Signatur ("Designed & Programmed by Protocol Top-G").
        Label initial = new Label("Designed & Programmed by Protocol Top-G");
        initial.setFont(Font.font("Bernard MT Condensed", 15));
        initial.setTextFill(Color.web("0000FF"));
        initial.setPadding(new Insets(0, 20, 0, 0)); // Abstand nach rechts
        initial.setAlignment(Pos.CENTER_LEFT);
        initial.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(initial, javafx.scene.layout.Priority.ALWAYS);

        //Ordnet die Elemente horizontal an.
        HBox hbox = new HBox(20, initial, newGame, exit);
        hbox.setPadding(new Insets(20, 20, 20, 20));
        hbox.setAlignment(Pos.CENTER_RIGHT);
        return hbox;
    }
