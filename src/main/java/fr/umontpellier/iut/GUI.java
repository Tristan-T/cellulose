package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.InputStream;

public class GUI extends Application {

    /**
     * NOTES :
     * We will not be using more than one scene, so that the software can be used without
     * having to click everywhere to get to a certain point, it will show everything in the main
     * window.
     */

    @Override
    public void start(Stage stage) throws Exception {
        //Setting title
        stage.setTitle("IUT Montpellier - Laboratoire virtuel (Cellulose)");

        //Import icon
        InputStream iconStream = getClass().getResourceAsStream("/icon.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);

        //Getting screen properties
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        //Setting window size
        stage.setX(screenBounds.getWidth()/4);
        stage.setY(screenBounds.getHeight()/4);
        stage.setMaxWidth(screenBounds.getWidth());
        stage.setMaxHeight(screenBounds.getHeight());
        stage.setMinHeight(screenBounds.getHeight()/4);
        stage.setMinWidth(screenBounds.getWidth()/4);
        stage.setResizable(true); //True by default but for good measures

        //Ability to go fullscreen
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });

        //Checking if JavaFX works
        Label helloWorldLabel = new Label("Hello world!");
        Scene scene = new Scene(helloWorldLabel);
        helloWorldLabel.setAlignment(Pos.CENTER);

        //Adding the scene to the stage
        stage.setScene(scene);

        stage.show();
    }

    //Eventuellement des paramètres externes
    public static void main(String[] args) {
        Application.launch();
    }
}
