package com.example.connect4protocoltopg;

import javafx.scene.paint.Color;

public class LogicModule {

    public int pitch [][] = new int [6][7];				//pitch[row][column] = Player occupys place
    public int points [] = new int [7];					//points[column] = amount of points in column
    private int coordinateX, coordinateY;
    private int player=0;
    private String firstPlayer, secondPlayer;
    private int countGames=1, winsFirstPlayer=0, winsSecondPlayer=0;

    //ensure that no points are in the columns
    private void initColumns() {
        for (int i=0; i<points.length; i++) {
            points[i]=0;
        }
        setCountGames(getCountGames()+1);
    }

    //ensure that no one has points on playground
    private void initPitch() {
        for (int i=0; i<6; i++) {
            for (int j=0; j<7; j++) {
                pitch [i][j] = 0;
            }
        }
    }

    //count the points, save which Player place points in rectangle
    public void refreshPitch(int column, int player) {
        points[column]++;
        int row = 6-points[column];
        pitch[row][column]=player;
        setCoordinateX(column);
        setCoordinateY(row);
    }

    //tie game
    public boolean lookingForDraw() {
        int counter = 0;
        for (int column=0; column<7; column++) {
            if (pitch[0][column]!=0) {
                counter++;
            }
        }
        if (counter==7) {
            return true;
        }
        else {
            return false;
        }
    }


    //looks for four points in a row
    public boolean searchingWinner() {
        // Horizontal prüfen
        System.out.println("Checking for winner...");
        System.out.println(" for winner...");
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <= 3; col++) { // Bis zur 4. letzten Spalte
                if (pitch[row][col] != 0 &&
                        pitch[row][col] == pitch[row][col + 1] &&
                        pitch[row][col] == pitch[row][col + 2] &&
                        pitch[row][col] == pitch[row][col + 3]) {
                    return true;
                }
            }
        }

        // Vertikal prüfen
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row <= 2; row++) { // Bis zur 3. letzten Reihe
                if (pitch[row][col] != 0 &&
                        pitch[row][col] == pitch[row + 1][col] &&
                        pitch[row][col] == pitch[row + 2][col] &&
                        pitch[row][col] == pitch[row + 3][col]) {
                    return true;
                }
            }
        }

        // Diagonal ↘ prüfen
        for (int row = 0; row <= 2; row++) { // Bis zur 3. letzten Reihe
            for (int col = 0; col <= 3; col++) { // Bis zur 4. letzten Spalte
                if (pitch[row][col] != 0 &&
                        pitch[row][col] == pitch[row + 1][col + 1] &&
                        pitch[row][col] == pitch[row + 2][col + 2] &&
                        pitch[row][col] == pitch[row + 3][col + 3]) {
                    return true;
                }
            }
        }

        // Diagonal ↙ prüfen
        for (int row = 3; row < 6; row++) { // Ab der 4. Reihe
            for (int col = 0; col <= 3; col++) { // Bis zur 4. letzten Spalte
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


    public void incrementScore() {
        System.out.println("IncrementScore called. Current player: " + player);
        if (player == 1) {
            winsFirstPlayer++;
            System.out.println("Player 1 score incremented. Total wins: " + winsFirstPlayer);
        } else if (player == 2) {
            winsSecondPlayer++;
            System.out.println("Player 2 score incremented. Total wins: " + winsSecondPlayer);
        } else {
            System.out.println("Error: Player value is invalid (" + player + ")");
        }
    }


//reset game
public void resetGame() {
    initColumns();
    initPitch();
    initPlayer();
    InterfaceManager interfaceManager = new InterfaceManager();
    interfaceManager.updateScoreDisplay(); // Score aktualisieren
}



//close all windows
public void exitGame() {
    System.exit(0);
}

//Player Point Color
public Color pointColor(int player) {
    if(player == 1) {
        return Color.RED;
    }else {
        return Color.YELLOW;
    }
}

//Player change
public void nextPlayer() {
    int previousPlayer = player;
    player = (player % 2) + 1; // Spieler 1 und 2 wechseln
    System.out.println("Player switched from " + previousPlayer + " to " + player);
}

//alternately Player start

public void initPlayer() {
    if (getCountGames()%2==0)
    {
        player=1;
    }
    if (getCountGames()%2==1)  {
        player=2;
    }
}

//Getter & Setter
public int getCoordinateX() {
    return coordinateX;
}
public void setCoordinateX(int column1) {
    this.coordinateX = column1;
}
public int getCoordinateY() {
    return coordinateY;
}
public void setCoordinateY(int row1) {
    this.coordinateY = row1;
}
public int getPlayer() {
    return player;
}
public String getFirstPlayer() {
    return firstPlayer;
}
public void setFirstPlayer(String firstPlayer) {
    this.firstPlayer = firstPlayer;
}
public String getSecondPlayer() {
    return secondPlayer;
}
public void setSecondPlayer(String secondPlayer) {
    this.secondPlayer = secondPlayer;
}

public int getCountGames() {
    return countGames;
}

public void setCountGames(int countGames) {
    this.countGames = countGames;
}

public int getWinsFirstPlayer() {
    return winsFirstPlayer;
}

public void setWinsFirstPlayer(int winsFirstPlayer) {
    this.winsFirstPlayer = winsFirstPlayer;
}

public int getWinsSecondPlayer() {
    return winsSecondPlayer;
}

public void setWinsSecondPlayer(int winsSecondPlayer) {
    this.winsSecondPlayer = winsSecondPlayer;
}

}