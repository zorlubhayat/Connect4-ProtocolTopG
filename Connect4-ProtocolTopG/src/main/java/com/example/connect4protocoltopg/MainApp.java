package com.example.connect4protocoltopg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // InterfaceManager-Instanz erstellen
        InterfaceManager interfaceManager = new InterfaceManager();

        // Layout initialisieren
        interfaceManager.setupLayout();


        // Szene erstellen und setzen
        Scene scene = interfaceManager.setupScene(primaryStage);
        primaryStage.setScene(scene);

        // Fenster anzeigen
        primaryStage.show();

        // Popup-Fenster für Namenseingabe anzeigen
        interfaceManager.popupSetNames();
    }

    public static void main(String[] args) {
        launch(args);
    }
}