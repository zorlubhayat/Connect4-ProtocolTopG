package com.example.idontknow;

import javafx.scene.paint.Color;

public class LogicModule {
    //------------------------------------------------------------------------------ Josef
    // Spielfeld-Daten:
    public int pitch[][] = new int[6][7]; // pitch[row][column]: Zeigt, welcher Spieler ein Feld besetzt
    public int points[] = new int[7];    // points[column]: Anzahl der gesetzten Punkte in jeder Spalte

    // Spieler- und Spiel-Daten:
    private int coordinateX, coordinateY; // Letzte gesetzte Koordinaten
    private int player = 0;               // Aktueller Spieler (1 oder 2)
    private String firstPlayer, secondPlayer; // Namen der Spieler
    private int countGames = 1;              // Anzahl der gespielten Spiele
    private int winsFirstPlayer = 0, winsSecondPlayer = 0; // Gewonnene Spiele pro Spieler

    // Initialisiert die Anzahl der Punkte in jeder Spalte auf 0
    private void initColumns() {
        for (int i = 0; i < points.length; i++) {
            points[i] = 0;
        }
        // Zählt die Spiele hoch, da ein neues Spiel gestartet wird
        setCountGames(getCountGames() + 1);
    }

    // Setzt das Spielfeld zurück, indem alle Felder auf 0 gesetzt werden
    private void initPitch() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                pitch[i][j] = 0;
            }
        }
    }

    // Aktualisiert das Spielfeld, wenn ein Spieler einen Punkt in einer Spalte setzt
    public void refreshPitch(int column, int player) {
        points[column]++; // Erhöhe die Anzahl der Punkte in der Spalte
        int row = 6 - points[column]; // Berechne die Zeile, in der der Punkt gesetzt wird
        pitch[row][column] = player; // Setze den Spieler in das Feld
        setCoordinateX(column); // Speichere die Koordinaten
        setCoordinateY(row);
    }

    // Prüft, ob das Spiel unentschieden ist (alle Spalten sind voll)
    public boolean lookingForDraw() {
        int counter = 0;
        for (int column = 0; column < 7; column++) {
            if (pitch[0][column] != 0) { // Wenn die oberste Reihe einer Spalte belegt ist
                counter++;
            }
        }
        return counter == 7; // Unentschieden, wenn alle Spalten voll sind
    }

    // Überprüft, ob ein Spieler gewonnen hat (4 in einer Reihe)
    public boolean searchingWinner() {
        // Horizontale Prüfung
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <= 3; col++) {
                if (pitch[row][col] != 0 &&
                        pitch[row][col] == pitch[row][col + 1] &&
                        pitch[row][col] == pitch[row][col + 2] &&
                        pitch[row][col] == pitch[row][col + 3]) {
                    return true;
                }
            }
        }

        // Vertikale Prüfung
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row <= 2; row++) {
                if (pitch[row][col] != 0 &&
                        pitch[row][col] == pitch[row + 1][col] &&
                        pitch[row][col] == pitch[row + 2][col] &&
                        pitch[row][col] == pitch[row + 3][col]) {
                    return true;
                }
            }
        }

        // Diagonale Prüfung ↘
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col <= 3; col++) {
                if (pitch[row][col] != 0 &&
                        pitch[row][col] == pitch[row + 1][col + 1] &&
                        pitch[row][col] == pitch[row + 2][col + 2] &&
                        pitch[row][col] == pitch[row + 3][col + 3]) {
                    return true;
                }
            }
        }

        // Diagonale Prüfung ↙
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col <= 3; col++) {
                if (pitch[row][col] != 0 &&
                        pitch[row][col] == pitch[row - 1][col + 1] &&
                        pitch[row][col] == pitch[row - 2][col + 2] &&
                        pitch[row][col] == pitch[row - 3][col + 3]) {
                    return true;
                }
            }
        }

        return false; // Kein Gewinner gefunden
    }

    // Erhöht den Punktestand des aktuellen Spielers
    public void incrementScore() {
        if (player == 1) {
            winsFirstPlayer++;
        } else if (player == 2) {
            winsSecondPlayer++;
        }
    }

    // Setzt das Spiel zurück, inklusive Spielfeld, Spalten und Spieler
    public void resetGame() {
        initColumns();
        initPitch();
        initPlayer();
        InterfaceManager interfaceManager = new InterfaceManager();
        interfaceManager.updateScoreDisplay(); // Aktualisiere die Anzeige des Punktestands
    }

    // Beendet das Spiel und schließt die Anwendung
    public void exitGame() {
        System.exit(0);
    }

    // Gibt die Farbe eines Spielers zurück (1 = rot, 2 = gelb)
    public Color pointColor(int player) {
        return (player == 1) ? Color.RED : Color.YELLOW;
    }

    // Wechselt den Spieler nach jedem Zug
    public void nextPlayer() {
        int previousPlayer = player;
        player = (player % 2) + 1; // Spieler 1 und 2 wechseln sich ab
    }

    // Legt fest, welcher Spieler das nächste Spiel beginnt
    public void initPlayer() {
        if (getCountGames() % 2 == 0) {
            player = 1; // Spieler 1 startet bei gerader Spielanzahl
        } else {
            player = 2; // Spieler 2 startet bei ungerader Spielanzahl
        }
    }

    // Getter und Setter für verschiedene Variablen
    public int getCoordinateX() { return coordinateX; }
    public void setCoordinateX(int column1) { this.coordinateX = column1; }
    public int getCoordinateY() { return coordinateY; }
    public void setCoordinateY(int row1) { this.coordinateY = row1; }
    public int getPlayer() { return player; }
    public String getFirstPlayer() { return firstPlayer; }
    public void setFirstPlayer(String firstPlayer) { this.firstPlayer = firstPlayer; }
    public String getSecondPlayer() { return secondPlayer; }
    public void setSecondPlayer(String secondPlayer) { this.secondPlayer = secondPlayer; }
    public int getCountGames() { return countGames; }
    public void setCountGames(int countGames) { this.countGames = countGames; }
    public int getWinsFirstPlayer() { return winsFirstPlayer; }
    public void setWinsFirstPlayer(int winsFirstPlayer) { this.winsFirstPlayer = winsFirstPlayer; }
    public int getWinsSecondPlayer() { return winsSecondPlayer; }
    public void setWinsSecondPlayer(int winsSecondPlayer) { this.winsSecondPlayer = winsSecondPlayer; }
}
