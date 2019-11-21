package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

// During dev
// For Linux : install openjfx package (by default with OpenJDK)
// For Windows : Download JavaFX Windows SDK (>=11) from here : https://gluonhq.com/products/javafx/
// Extract it somewhere safe
// In IntelliJ IDEA : File -> Project Structure -> Modules -> Dependencies -> + (left side button) -> Jar or directory -> Select the libs folder inside the file you extracted
// Run the GUI.main();
// Once the app ships, it'll package everything

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Setting title
        primaryStage.setTitle("Laboratoire Virtuel - IUT Montpellier");

        //Custom icon
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.show();
    }

    public static void main() {
        //Starting the GUI through main, allow for parameters to pass
        Application.launch();
    }
}
