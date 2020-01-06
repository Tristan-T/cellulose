package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.application.Platform;

public class App {
    public static void main(String[] args) {
        //Fake runnable to initialize JavaFX hidden routines
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        Platform.startup(runnable);
        GUI.start(args);
    }
}
