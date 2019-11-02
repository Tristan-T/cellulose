package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Laboratoire Virtuel - IUT Montpellier");

        primaryStage.show();
    }

    public static void main() {
        Application.launch();
    }
}
