package com.example.connect4protocoltopg;

import javafx.scene.paint.Color;

public class LogicModule {
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