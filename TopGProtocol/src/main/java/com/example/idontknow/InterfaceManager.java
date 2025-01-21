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

    private BorderPane root;          // Hauptlayout-Manager, der die UI-Bereiche (Top, Center, Left, Right, Bottom) verwaltet
    private Circle[][] circle;        // 2D-Array für die Spielsteine (Kreise) im Spielfeld
    private Button[] button;          // Array für die Spalten-Buttons, die den Spieleraktionen zugeordnet sind
    private Label[] text;             // Array für die Labels, die Informationen über die Spieler anzeigen

    LogicModule game = new LogicModule(); // Die Spiellogik-Instanz, die das Spiel verwaltet
    firstwindow pop = new firstwindow(game); // Erstellt ein `firstwindow`-Objekt, das mit der Spiellogik verknüpft ist


    /**
     * Konstruktor der Klasse `InterfaceManager`.
     * Initialisiert die Hauptkomponenten wie das Layout (BorderPane), das Spielfeld (Kreise) und die Buttons.
     */
    public InterfaceManager() {
        this.root = new BorderPane();       // Erstellt ein neues `BorderPane` als Hauptlayout
        this.circle = new Circle[7][6];    // Initialisiert das 2D-Array für die Spielsteine mit 7 Spalten und 6 Reihen
        this.button = new Button[7];       // Initialisiert das Array für die Buttons (eine Schaltfläche pro Spalte)
        this.text = new Label[4];           // Initialisiert das Array für die Labels (z. B. "Player 1", "Player 2", usw.)
    }

    /**
     * Richtet das Layout der Benutzeroberfläche ein.
     * Fügt die verschiedenen Bereiche (Top, Center, Left, Right, Bottom) zum `root`-Layout hinzu.
     */
    public void setupLayout() {
        root.setTop(createTopPane());       // Oberer Bereich mit einem Logo oder Titel
        root.setCenter(createCenterPane()); // Mittlerer Bereich mit dem Spielfeld (Raster und Spielsteine)
        root.setLeft(getLeftHBox());        // Linker Bereich mit Spielerinformationen
        root.setRight(getRightHBox());      // Rechter Bereich mit Punktestand und weiteren Infos
        root.setBottom(createBottomPane()); // Unterer Bereich mit Buttons für Aktionen wie "New Game" und "Exit"

        // Setzt den Hintergrund des gesamten Layouts auf hellblau
        root.setStyle("-fx-background-color: #ADD8E6;");
    }

    /**
     * Konfiguriert die Szene für die Benutzeroberfläche des Spiels und verknüpft sie mit dem angegebenen `Stage`.
     *
     * @param stage Das Hauptfenster (Stage), auf dem die Szene angezeigt wird.
     * @return Die konfigurierte Szene für das Spiel.
     */
    public Scene setupScene(Stage stage) {
        // Überprüft, ob die Szene bereits mit dem `Stage` verbunden ist
        if (stage.getScene() != null) {
            return stage.getScene(); // Gibt die bestehende Szene zurück, falls vorhanden
        }

        // Erstellt eine neue Szene mit dem `root`-Layout und den angegebenen Abmessungen
        Scene scene = new Scene(root, 1000, 780); // Größe: Breite 1000 Pixel, Höhe 780 Pixel

        // Setzt den Titel des Fensters (Stage)
        stage.setTitle("Connect Four");

        // Gibt die neu erstellte Szene zurück
        return scene;
    }
    /**
     * Erstellt den oberen Bereich der Benutzeroberfläche mit einem Bild (Logo).
     *
     * @return Ein `Pane` (HBox), das das Bild enthält.
     */
    private Pane createTopPane() {
        // Lädt das Bild aus den Ressourcen (im Ordner `com/example/Idontknow/`)
        Image image = new Image(getClass().getResource("/com/example/Idontknow/Daco_1087247.png").toExternalForm());
        ImageView imageView = new ImageView(image); // Erstellt ein ImageView für die Anzeige des Bildes

        // Anpassung der Bildgröße und Qualität
        imageView.setFitWidth(300);        // Setzt die Breite des Bildes auf 300 Pixel
        imageView.setPreserveRatio(true); // Beibehaltung des Seitenverhältnisses
        imageView.setSmooth(true);        // Aktiviert Kantenglättung für eine bessere Darstellung
        imageView.setCache(true);         // Cacht das Bild, um die Performance zu verbessern

        // Platziert das Bild in einer HBox (horizontaler Layout-Manager)
        HBox topPane = new HBox(imageView);
        topPane.setAlignment(Pos.CENTER); // Zentriert das Bild horizontal in der HBox
        topPane.setPadding(new Insets(10)); // Fügt einen Innenabstand von 10 Pixeln hinzu

        // Gibt den konfigurierten oberen Bereich zurück
        return topPane;
    }

    /**
     * Erstellt den zentralen Bereich der Benutzeroberfläche, der das Spielfeld und die Buttons enthält.
     *
     * @return Ein `Pane` (VBox), das das Spielfeld (GridPane) und die Steuerungs-Buttons (HBox) kombiniert.
     */
    Pane createCenterPane() {
        // Erstelle ein GridPane als Layout für das Spielfeld
        GridPane gpane = new GridPane();

        // Initialisiere das Spielfeld mit Rechtecken (Raster) und unsichtbaren Kreisen (Spielsteine)
        for (int column = 0; column < 6; column++) { // 6 Reihen
            for (int row = 0; row < 7; row++) {      // 7 Spalten
                // Rechteck für die aktuelle Zelle erstellen
                Rectangle rect = new Rectangle(70, 70); // Rechteck mit Breite und Höhe von 70 Pixel
                rect.setStroke(Color.BLUE);            // Setzt die Rahmenfarbe auf Blau
                rect.setFill(null);                    // Keine Füllfarbe
                rect.setStrokeWidth(3);                // Rahmenbreite von 3 Pixeln
                gpane.add(rect, row, column);          // Fügt das Rechteck an die Position (row, column) hinzu

                // Kreis für die Spielsteine in der aktuellen Zelle erstellen
                circle[row][column] = new Circle(0, 0, 25); // Kreis mit Radius 25 Pixel
                circle[row][column].setStroke(Color.TRANSPARENT); // Unsichtbare Umrandung
                circle[row][column].setVisible(false);           // Standardmäßig unsichtbar
                gpane.add(circle[row][column], row, column);     // Fügt den Kreis an die gleiche Position hinzu
                GridPane.setHalignment(circle[row][column], HPos.CENTER); // Zentriert den Kreis horizontal
            }
        }

        // Zentriere das GridPane im zentralen Bereich
        gpane.setAlignment(Pos.TOP_CENTER);

        // Initialisiere die Buttons für die Spaltensteuerung
        for (int i = 0; i < 7; i++) { // 7 Spalten
            if (button[i] == null) {
                // Erstelle einen Button für die aktuelle Spalte
                button[i] = new Button("#" + (i + 1)); // Beschriftung: "#1", "#2", ...
                button[i].setFont(Font.font("Bernard MT Condensed", 15)); // Schriftart und -größe
                button[i].setTextFill(Color.web("#5D4E84")); // Schriftfarbe
                button[i].setStyle(
                        "-fx-background-color: #79CDF0;" + // Hintergrundfarbe (hellblau)
                                "-fx-text-fill: #0000FF;" +        // Schriftfarbe (blau)
                                "-fx-border-color: transparent;" +// Standardmäßig keine Umrandung
                                "-fx-border-width: 2;" +          // Rahmenbreite (unsichtbar)
                                "-fx-border-radius: 5;"           // Abgerundete Ecken
                );
                button[i].setMinSize(50, 25);         // Mindestgröße des Buttons

                // Hinzufügen einer Aktion für den Button
                int column = i;                       // Speichert die aktuelle Spalte
                button[i].setOnAction(event -> setOnAction(column)); // Führt die Aktion aus, wenn der Button geklickt wird
            }
        }

        // Event-Handler für Klicks auf das Spielfeld
        gpane.setOnMouseClicked(event -> {
            // Berechnet die Spalte basierend auf der Klickposition
            double gridStartX = gpane.localToScene(0, 0).getX();
            int column = (int) ((event.getSceneX() - gridStartX) / 85);

            // Überprüft, ob die Spalte gültig ist und noch Platz hat
            if (column >= 0 && column < 7 && game.points[column] < 6) {
                setOnAction(column); // Führt die Aktion für die geklickte Spalte aus
            }
        });

        // Event-Handler für das Hervorheben von Spalten beim Bewegen der Maus
        gpane.setOnMouseMoved(event -> {
            // Berechnet die aktuelle Spalte basierend auf der Mausposition
            double gridStartX = gpane.localToScene(0, 0).getX();
            int column = (int) ((event.getSceneX() - gridStartX) / 90);

            // Setzt das Styling aller Buttons zurück (Standardstil)
            for (int i = 0; i < 7; i++) {
                button[i].setStyle("-fx-background-color: #79CDF0 ");
            }

            // Hebt die aktuelle Spalte hervor, falls sie gültig ist
            if (column >= 0 && column < 7) {
                button[column].setStyle(
                        "-fx-background-color: #79CDF0;" + // Hintergrundfarbe bleibt hellblau
                                "-fx-text-fill: #0000FF;" +        // Schriftfarbe bleibt blau
                                "-fx-border-color: #0000FF;" +    // Umrandung wird dunkelblau
                                "-fx-border-width: 2;" +          // Rahmenbreite (2 Pixel)
                                "-fx-border-radius: 5;"           // Abgerundete Ecken
                );
            }
        });

        // Erstelle eine HBox für die Buttons
        HBox hbox = new HBox(22);             // Abstand zwischen Buttons: 22 Pixel
        hbox.setAlignment(Pos.CENTER);       // Zentriere die Buttons
        hbox.setPadding(new Insets(20, 10, 10, 10)); // Innenabstände: oben, rechts, unten, links
        for (int i = 0; i < 7; i++) {
            hbox.getChildren().add(button[i]); // Füge jeden Button zur HBox hinzu
        }

        // Kombiniere die Buttons und das Spielfeld in einer VBox
        VBox vbox = new VBox();             // Vertikales Layout
        vbox.getChildren().addAll(hbox, gpane); // Buttons oben, Spielfeld darunter

        // Gib den zentralen Bereich zurück
        return vbox;
    }
    //-------------------------------------------------------------
    /**
     * Erstellt den linken Bereich der Benutzeroberfläche mit Spielerinformationen.
     *
     * @return Eine `VBox`, die Labels mit Informationen über die Spieler enthält.
     */
    VBox getLeftHBox() {
        // Initialisiere die Labels für die Spielerinformationen
        text[0] = new Label("Player 1:");                     // Überschrift für Spieler 1
        text[1] = new Label(game.getFirstPlayer());           // Name von Spieler 1
        text[2] = new Label("Player 2:");                     // Überschrift für Spieler 2
        text[3] = new Label(game.getSecondPlayer());          // Name von Spieler 2

        // Stil für alle Labels einheitlich festlegen
        for (int i = 0; i < 4; i++) {
            text[i].setPadding(new Insets(10, 10, 10, 10));   // Abstand innerhalb des Labels
            text[i].setFont(new Font("Bernard MT Condensed", 20)); // Schriftart und -größe
            text[i].setTextFill(Color.web("#0000FF"));        // Schriftfarbe: Blau
        }

        // Spezieller Stil für den Namen von Spieler 1 (rot und größer)
        text[1].setFont(Font.font("Bernard MT Condensed", 30)); // Größere Schriftgröße
        text[1].setTextFill(Color.RED);                         // Schriftfarbe: Rot

        // Spezieller Stil für den Namen von Spieler 2 (gelb und größer)
        text[3].setFont(Font.font("Bernard MT Condensed", 30)); // Größere Schriftgröße
        text[3].setTextFill(Color.YELLOW);                      // Schriftfarbe: Gelb

        // Erstelle das vertikale Layout (VBox) und füge die Labels hinzu
        VBox vbox = new VBox(text[0], text[1], text[2], text[3]); // Labels in vertikaler Anordnung
        vbox.setAlignment(Pos.CENTER);                            // Zentriert die Labels in der VBox
        vbox.setMinWidth(200);                                    // Mindestbreite für den linken Bereich

        // Gib die konfigurierte VBox zurück
        return vbox;
    }

    //-------------------------------------------------------------
    /**
     * Erstellt den rechten Bereich der Benutzeroberfläche, der den Punktestand der Spieler anzeigt.
     *
     * @return Ein `GridPane`, das die Spielerinformationen und Punktestände enthält.
     */
    public GridPane getRightHBox() {
        // Labels für die Spielerinformationen erstellen
        Label player1Label = template.createStyledLabel(game.getFirstPlayer(), 20, "#0000FF"); // Spieler 1 Name
        Label separator1 = template.createStyledLabel("  :  ", 20, "#0000FF");               // Separator (z. B. ":")
        Label player2Label = template.createStyledLabel(game.getSecondPlayer(), 20, "#0000FF"); // Spieler 2 Name

        // Labels für die Punktestände erstellen
        Label player1Wins = template.createStyledLabel(String.valueOf(game.getWinsFirstPlayer()), 20, "#0000FF"); // Punkte Spieler 1
        Label separator2 = template.createStyledLabel("  :  ", 20, "#0000FF");                                   // Separator (z. B. ":")
        Label player2Wins = template.createStyledLabel(String.valueOf(game.getWinsSecondPlayer()), 20, "#0000FF"); // Punkte Spieler 2

        // Ein GridPane-Layout erstellen
        GridPane gridPane = new GridPane();

        // Spielerinformationen (erste Zeile) hinzufügen
        gridPane.add(player1Label, 0, 0); // Spieler 1 Name in Spalte 0, Zeile 0
        gridPane.add(separator1, 1, 0);  // Separator (:) in Spalte 1, Zeile 0
        gridPane.add(player2Label, 2, 0); // Spieler 2 Name in Spalte 2, Zeile 0

        // Punktestände (zweite Zeile) hinzufügen
        gridPane.add(player1Wins, 0, 1); // Spieler 1 Punktestand in Spalte 0, Zeile 1
        gridPane.add(separator2, 1, 1); // Separator (:) in Spalte 1, Zeile 1
        gridPane.add(player2Wins, 2, 1); // Spieler 2 Punktestand in Spalte 2, Zeile 1

        // Zentriere jeden Inhalt in den entsprechenden Zellen
        GridPane.setHalignment(player1Label, HPos.CENTER);
        GridPane.setHalignment(separator1, HPos.CENTER);
        GridPane.setHalignment(player2Label, HPos.CENTER);
        GridPane.setHalignment(player1Wins, HPos.CENTER);
        GridPane.setHalignment(separator2, HPos.CENTER);
        GridPane.setHalignment(player2Wins, HPos.CENTER);

        // Zentriere das gesamte GridPane im rechten Bereich
        gridPane.setAlignment(Pos.CENTER);

        // Setze Mindestgröße und füge Innenabstand hinzu
        gridPane.setMinSize(200, 500);  // Mindestgröße: Breite 200, Höhe 500
        gridPane.setPadding(new Insets(20)); // Abstand innerhalb des GridPane

        // Gib das konfigurierte GridPane zurück
        return gridPane;
    }
    //-------------------------------------------------------------
    /**
     * Erstellt den unteren Bereich der Benutzeroberfläche mit den Schaltflächen "New Game" und "Exit".
     *
     * @return Ein `HBox`, das die Schaltflächen und eine Signatur horizontal anordnet.
     */
    public HBox createBottomPane() {
        // Schaltfläche zum Neustart des Spiels erstellen
        Button newGame = new Button("New Game");
        newGame.setFont(Font.font("Bernard MT Condensed", 20)); // Schriftart und -größe
        newGame.setStyle(
                "-fx-background-color: #79CDF0;" + // Hellblauer Hintergrund
                        "-fx-text-fill: #0000FF;" +        // Blaue Schrift
                        "-fx-border-color: transparent;" +// Keine sichtbare Umrandung
                        "-fx-border-width: 2;" +          // Rahmenbreite (unsichtbar)
                        "-fx-border-radius: 5;"           // Abgerundete Ecken
        );

        // Aktion für die "New Game"-Schaltfläche
        newGame.setOnAction(event -> {
            game.resetGame();          // Setzt die Spiellogik zurück
            resetPitch();              // Leert das Spielfeld
            for (int i = 0; i < 7; i++) {
                button[i].setDisable(false); // Aktiviert alle Buttons
            }
            markPlayer();              // Markiert den aktuellen Spieler
        });

        // Aktion, wenn die Eingabetaste gedrückt wird (gleiches Verhalten wie Klick)
        newGame.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                game.resetGame();
                resetPitch();
                for (int i = 0; i < 7; i++) {
                    button[i].setDisable(false);
                }
                markPlayer();
            }
        });

        // Stiländerung bei Hover-Effekt (Maus über der Schaltfläche)
        newGame.setOnMouseEntered(e -> newGame.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: #0000FF;" + // Dunkelblaue Umrandung bei Hover
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));
        newGame.setOnMouseExited(e -> newGame.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" + // Zurück zur ursprünglichen Umrandung
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));

        // Schaltfläche zum Beenden des Spiels erstellen
        Button exit = new Button("Exit");
        exit.setFont(Font.font("Bernard MT Condensed", 20)); // Schriftart und -größe
        exit.setTextFill(Color.web("#5D4E84"));             // Schriftfarbe
        exit.setStyle(
                "-fx-background-color: #79CDF0;" + // Hellblauer Hintergrund
                        "-fx-text-fill: #0000FF;" +        // Blaue Schrift
                        "-fx-border-color: transparent;" +// Keine sichtbare Umrandung
                        "-fx-border-width: 2;" +          // Rahmenbreite (unsichtbar)
                        "-fx-border-radius: 5;"           // Abgerundete Ecken
        );

        // Aktion für die "Exit"-Schaltfläche
        exit.setOnAction(event -> game.exitGame()); // Beendet das Spiel und schließt die Anwendung

        // Aktion, wenn die Eingabetaste gedrückt wird (gleiches Verhalten wie Klick)
        exit.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                game.exitGame();
            }
        });

        // Stiländerung bei Hover-Effekt (Maus über der Schaltfläche)
        exit.setOnMouseEntered(e -> exit.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: #0000FF;" + // Dunkelblaue Umrandung bei Hover
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));
        exit.setOnMouseExited(e -> exit.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" + // Zurück zur ursprünglichen Umrandung
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));

        // Label für eine Signatur erstellen
        Label initial = new Label("Designed & Programmed by Protocol Top-G");
        initial.setFont(Font.font("Bernard MT Condensed", 15)); // Schriftart und -größe
        initial.setTextFill(Color.web("#0000FF"));             // Schriftfarbe
        initial.setPadding(new Insets(0, 20, 0, 0));           // Abstand innerhalb des Labels
        initial.setAlignment(Pos.CENTER_LEFT);                // Ausrichtung links
        initial.setMaxWidth(Double.MAX_VALUE);                // Maximale Breite für Flexibilität
        HBox.setHgrow(initial, javafx.scene.layout.Priority.ALWAYS); // Platzanpassung innerhalb der HBox

        // HBox erstellen und alle Komponenten hinzufügen
        HBox hbox = new HBox(20, initial, newGame, exit); // Abstand von 20 Pixeln zwischen Elementen
        hbox.setPadding(new Insets(20, 20, 20, 20));      // Innenabstand
        hbox.setAlignment(Pos.CENTER_RIGHT);             // Schaltflächen rechtsbündig
        return hbox;                                      // Gibt die HBox zurück
    }

    //-------------------------------------------------------------//------------------------------------------------------------------------------ Teilen
    /**
     * Markiert den aktuellen Spieler durch Anpassen der Schriftgröße und Farbe der Spielernamen.
     */
    void markPlayer() {
        if (game.getPlayer() == 2) {
            // Spieler 2 ist am Zug: Name von Spieler 2 hervorheben
            text[1].setFont(Font.font("Bernard MT Condensed", 30)); // Spieler 1 in kleinerer Schrift
            text[1].setTextFill(Color.RED);                        // Spieler 1 bleibt in Rot
            text[3].setFont(Font.font("Bernard MT Condensed", 20)); // Spieler 2 in kleinerer Schrift
        }
        if (game.getPlayer() == 1) {
            // Spieler 1 ist am Zug: Name von Spieler 1 hervorheben
            text[1].setFont(Font.font("Bernard MT Condensed", 20)); // Spieler 1 in kleinerer Schrift
            text[3].setFont(Font.font("Bernard MT Condensed", 30)); // Spieler 2 in größerer Schrift
            text[3].setTextFill(Color.YELLOW);                      // Spieler 2 bleibt in Gelb
        }
    }

    /**
     * Setzt das Spielfeld zurück, indem alle Spielsteine unsichtbar gemacht werden.
     */
    public void resetPitch() {
        for (int i = 0; i < 7; i++) {       // Schleife über alle Spalten
            for (int j = 0; j < 6; j++) {   // Schleife über alle Reihen
                circle[i][j].setVisible(false); // Mache jeden Spielstein unsichtbar
            }
        }
    }
    /**
     * Behandelt das Spielende, indem der Gewinner oder ein Unentschieden festgestellt wird.
     *
     * @param winner Gibt an, ob ein Gewinner gefunden wurde (true) oder ein Unentschieden vorliegt (false).
     */
    public void handleGameEnd(boolean winner) {
        // Debug-Ausgabe: Zeigt an, welcher Spieler aktuell ist
        System.out.println("Current Player (handleGameEnd): " + game.getPlayer());

        stopButton(); // Deaktiviert alle Buttons, um weitere Eingaben zu verhindern

        if (winner) { // Überprüft, ob ein Gewinner ermittelt wurde
            if (game.searchingWinner()) { // Verifiziert, ob der Gewinner tatsächlich 4 in einer Reihe hat
                game.incrementScore(); // Erhöht die Punktzahl des Gewinners
                updateScoreDisplay(); // Aktualisiert die Punktestandsanzeige
                popupwinner.popupWinner(game); // Zeigt ein Popup-Fenster mit dem Gewinner an
            }
        } else {
            // Wenn kein Gewinner gefunden wurde, zeige ein Popup für ein Unentschieden
            pop.popupDraw();
        }
    }

    //-------------------------------------------------------------
    /**
     * Deaktiviert alle Buttons, um weitere Eingaben zu verhindern.
     * Diese Methode wird verwendet, wenn das Spiel beendet ist
     * (entweder durch einen Gewinner oder ein Unentschieden).
     */
    void stopButton() {
        // Deaktiviere alle Spalten-Buttons
        button[0].setDisable(true);
        button[1].setDisable(true);
        button[2].setDisable(true);
        button[3].setDisable(true);
        button[4].setDisable(true);
        button[5].setDisable(true);
        button[6].setDisable(true);
    }

    /**
     * Behandelt die Aktion, wenn ein Button geklickt wird.
     * Führt einen Zug aus, aktualisiert den Spielzustand und überprüft auf Gewinner oder Unentschieden.
     *
     * @param buttonNumber Die Nummer des Buttons, der geklickt wurde (entspricht der Spalte).
     */
    void setOnAction(int buttonNumber) {
        // Wechsel zum nächsten Spieler
        game.nextPlayer();

        // Aktualisiere das Spielfeld mit der Aktion des aktuellen Spielers
        game.refreshPitch(buttonNumber, game.getPlayer());

        // Debugging: Zeige den aktuellen Zustand des Spielfelds in der Konsole
        for (int[] row : game.pitch) {
            System.out.println(java.util.Arrays.toString(row));
        }

        // Aktualisiere den visuellen Kreis für den Spielstein
        updateCircle(buttonNumber);

        // Deaktiviere den Button, wenn die Spalte voll ist
        disableButtonIfNeeded(buttonNumber);

        // Überprüfe, ob ein Gewinner ermittelt wurde
        if (game.searchingWinner()) {
            System.out.println("Winner: " + game.searchingWinner()); // Debugging: Gewinner gefunden
            handleGameEnd(true); // Behandle den Gewinner
            return; // Beende die Methode, da das Spiel vorbei ist
        }

        // Überprüfe, ob das Spiel mit einem Unentschieden endet
        if (game.lookingForDraw()) {
            handleGameEnd(false); // Behandle das Unentschieden
            return; // Beende die Methode, da das Spiel vorbei ist
        }

        // Markiere den nächsten Spieler für den nächsten Zug
        markPlayer();
    }

    //-------------------------------------------------------------
    /**
     * Aktualisiert die Anzeige des Spielsteins in der entsprechenden Spalte und Zeile.
     *
     * @param buttonNumber Die Nummer der Spalte, in der der Spielstein platziert wurde.
     */
    private void updateCircle(int buttonNumber) {
        // Hole die Y-Koordinate (Reihe) aus der Spiellogik
        int y = game.getCoordinateY();

        // Greife auf den entsprechenden Kreis im 2D-Array `circle` zu
        Circle currentCircle = circle[buttonNumber][y];

        // Mache den Kreis sichtbar und setze seine Farben basierend auf dem aktuellen Spieler
        currentCircle.setVisible(true); // Sichtbar machen
        currentCircle.setStroke(game.pointColor(game.getPlayer())); // Umrandungsfarbe basierend auf Spielerfarbe
        currentCircle.setFill(game.pointColor(game.getPlayer()));   // Füllfarbe basierend auf Spielerfarbe
    }

    /**
     * Deaktiviert den Button, wenn die Spalte voll ist, um weitere Eingaben zu verhindern.
     *
     * @param buttonNumber Die Nummer der Spalte, die überprüft wird.
     */
    private void disableButtonIfNeeded(int buttonNumber) {
        // Überprüfe, ob die oberste Reihe in der angegebenen Spalte bereits belegt ist
        if (game.getCoordinateY() == 0) {
            // Wenn die Y-Koordinate 0 ist, bedeutet das, dass die Spalte keine freien Felder mehr hat
            button[buttonNumber].setDisable(true); // Deaktiviere den Button für diese Spalte
        }
    }

    //-------------------------------------------------------------
    /**
     * Zeigt ein modales Popup-Fenster an, in dem die Namen der Spieler eingegeben werden können.
     */


    void popupSetNames() {
        // Erstellt ein neues modales Fenster
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // Das Fenster blockiert die Interaktion mit dem Hauptfenster
        window.setTitle("Player Input"); // Titel des Fensters

        // Bild hinzufügen (optional)
        Image image = new Image(getClass().getResource("/com/example/Idontknow/Daco_1087247.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500); // Setzt die Breite des Bildes
        imageView.setPreserveRatio(true); // Beibehaltung des Seitenverhältnisses
        imageView.setSmooth(true); // Glättet die Kanten des Bildes
        imageView.setCache(true); // Caching für bessere Performance

        // Hauptüberschrift des Fensters
        Label label1 = new Label("Enter name of player");
        label1.setFont(new Font("Bernard MT Condensed", 20)); // Schriftart und -größe
        label1.setTextFill(Color.web("#5D4E84")); // Farbe der Schrift
        label1.setAlignment(Pos.CENTER); // Zentrierung des Textes

        // Label und Textfeld für Spieler 1
        Label label2 = new Label("Player 1: ");
        label2.setFont(new Font("Bernard MT Condensed", 20));
        label2.setTextFill(Color.web("#5D4E84"));

        TextField textField1 = new TextField();
        textField1.setPromptText("name player 1"); // Platzhaltertext
        textField1.setFont(Font.font("Bernard MT Condensed", 20));

        // Label und Textfeld für Spieler 2
        Label label3 = new Label("Player 2: ");
        label3.setFont(new Font("Bernard MT Condensed", 20));
        label3.setTextFill(Color.web("#5D4E84"));

        TextField textField2 = new TextField();
        textField2.setPromptText("name player 2");
        textField2.setFont(Font.font("Bernard MT Condensed", 20));

        // "Save"-Button
        Button button1 = new Button("Save");
        button1.setFont(Font.font("Bernard MT Condensed", 20));
        button1.setTextFill(Color.web("#5D4E84"));
        button1.setStyle(
                "-fx-background-color: #79CDF0;" + // Hintergrundfarbe
                        "-fx-text-fill: #0000FF;" + // Schriftfarbe
                        "-fx-border-color: transparent;" + // Keine Rahmenfarbe
                        "-fx-border-width: 2;" + // Rahmenbreite
                        "-fx-border-radius: 5;" // Abgerundete Ecken
        );

        // Aktivierung des "Save"-Buttons nur, wenn beide Textfelder ausgefüllt sind
        button1.disableProperty().bind(
                Bindings.isEmpty(textField1.textProperty())
                        .or(Bindings.isEmpty(textField2.textProperty()))
        );

        // Aktion bei Betätigung des "Save"-Buttons
        button1.setOnAction(event -> {
            game.setFirstPlayer(textField1.getText()); // Setze den Namen von Spieler 1
            game.setSecondPlayer(textField2.getText()); // Setze den Namen von Spieler 2
            window.close(); // Schließe das Fenster
            root.setLeft(getLeftHBox()); // Aktualisiere die linke Anzeige
            root.setRight(getRightHBox()); // Aktualisiere die rechte Anzeige
        });

        // Hover-Effekte für "Save"-Button
        button1.setOnMouseEntered(e -> button1.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: #0000FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));
        button1.setOnMouseExited(e -> button1.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));

        // "Exit"-Button
        Button button2 = new Button("Exit");
        button2.setFont(Font.font("Bernard MT Condensed", 20));
        button2.setTextFill(Color.web("#5D4E84"));
        button2.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        );

        // Aktion bei Betätigung des "Exit"-Buttons
        button2.setOnAction(event -> game.exitGame());

        // Hover-Effekte für "Exit"-Button
        button2.setOnMouseEntered(e -> button2.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: #0000FF;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));
        button2.setOnMouseExited(e -> button2.setStyle(
                "-fx-background-color: #79CDF0;" +
                        "-fx-text-fill: #0000FF;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 5;"
        ));

        // Layout für Textfelder und Labels
        GridPane grid = new GridPane();
        grid.add(label2, 0, 0);
        grid.add(textField1, 1, 0);
        grid.add(label3, 0, 1);
        grid.add(textField2, 1, 1);
        grid.setHgap(10); // Horizontaler Abstand
        grid.setVgap(10); // Vertikaler Abstand
        grid.setAlignment(Pos.CENTER); // Zentrierung

        // Buttons in einer horizontalen Box
        HBox hbox = new HBox(10, button1, button2);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);

        // Gesamtlayout
        VBox vbox = new VBox(20, imageView, label1, grid, hbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        // Hauptlayout mit Hintergrundfarbe
        StackPane pane = new StackPane(vbox);
        pane.setStyle("-fx-background-color: #ADD8E6;");
        pane.setAlignment(Pos.CENTER);

        // Szene und Fenster anzeigen
        Scene windowScene = new Scene(pane, 550, 530);
        window.setScene(windowScene);
        window.show();
    }

    public void updateScoreDisplay() {
        root.setRight(getRightHBox());
    }

}
