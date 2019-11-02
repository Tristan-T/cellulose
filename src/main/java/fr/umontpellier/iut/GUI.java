package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.stage.Stage;

// During dev
// For Linux : install openjfx package (by default with OpenJDK)
// For Windows : Download JavaFX Windows SDK (>=11) from here : https://gluonhq.com/products/javafx/
// Extract it somewhere safe
// In IntelliJ IDEA : File -> Project Structure -> Modules -> Dependencies -> + (left side button) -> Jar or directory -> Select the libs folder inside the file you extracted
// Run the GUI.main();
// Once the app ships, it'll package everything

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
