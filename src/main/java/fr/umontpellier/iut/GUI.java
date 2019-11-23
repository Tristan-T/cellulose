package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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

    private static GridPane settingsPane() {
        GridPane settingsBox = new GridPane();
        settingsBox.setPadding(new Insets(10, 10, 10, 10));
        settingsBox.setVgap(10);
        settingsBox.setHgap(70);
        settingsBox.getColumnConstraints().add(new ColumnConstraints(400));

        SpinnerSetter cIni = new SpinnerSetter("cIni", 0, 200, 10, "toDefine");
        SpinnerSetter cMin = new SpinnerSetter("cMin", 0, 200, 10, "toDefine");
        SpinnerSetter vDiff = new SpinnerSetter("vDiff", 0, 200, 10, "toDefine");
        SpinnerSetter cellsPerSide = new SpinnerSetter("cellsPerSide", 0, 200, 10, "toDefine");
        SpinnerSetter halfLength = new SpinnerSetter("halfLength", 0, 200, 10, "toDefine");
        SpinnerSetter substratumRadius = new SpinnerSetter("substratumRadius", 0, 200, 10, "toDefine");
        SpinnerSetter bDiff = new SpinnerSetter("bDiff", 0, 200, 10, "toDefine");
        SpinnerSetter mIni = new SpinnerSetter("mIni", 0, 200, 10, "toDefine");
        SpinnerSetter vCons = new SpinnerSetter("vCons", 0, 200, 10, "toDefine");
        SpinnerSetter vd = new SpinnerSetter("vd", 0, 200, 10, "toDefine");
        SpinnerSetter kConv = new SpinnerSetter("kConv", 0, 200, 10, "toDefine");
        SpinnerSetter timeDelta = new SpinnerSetter("timeDelta", 0, 200, 10, "toDefine");
        SpinnerSetter timeDeltaSubdivision = new SpinnerSetter("timeDeltaSubdivision", 0, 200, 10, "toDefine");
        SpinnerSetter initialBacteriaAmount = new SpinnerSetter("initialBacteriaAmount", 0, 200, 10, "toDefine");
        SpinnerSetter maxDuration = new SpinnerSetter("maxDuration", 0, 200, 10, "toDefine");

        settingsBox.add(cIni.getSpinnerSetter(), 0, 0);
        settingsBox.add(cMin.getSpinnerSetter(), 0, 1);
        settingsBox.add(vDiff.getSpinnerSetter(), 0, 2);
        settingsBox.add(cellsPerSide.getSpinnerSetter(), 0, 3);
        settingsBox.add(halfLength.getSpinnerSetter(), 0, 4);
        settingsBox.add(substratumRadius.getSpinnerSetter(), 0, 5);
        settingsBox.add(bDiff.getSpinnerSetter(), 0, 6);
        settingsBox.add(mIni.getSpinnerSetter(), 0, 7);
        settingsBox.add(vCons.getSpinnerSetter(), 0, 8);
        settingsBox.add(vd.getSpinnerSetter(), 0, 9);
        settingsBox.add(kConv.getSpinnerSetter(), 0, 10);
        settingsBox.add(timeDelta.getSpinnerSetter(), 0, 11);
        settingsBox.add(timeDeltaSubdivision.getSpinnerSetter(), 0, 12);
        settingsBox.add(initialBacteriaAmount.getSpinnerSetter(), 0, 13);
        settingsBox.add(maxDuration.getSpinnerSetter(), 0, 14);

        return settingsBox;
    }

    private static void launchingPane(Scene scene) {

    }

    private static void celluloseModel(Scene scene) {

    }
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

        //Adding the scene to the stage
        stage.setScene(new Scene(settingsPane()));

        stage.show();
    }

    /*
     * NEEDS A FUNCTION TO COMMIT ALL VALUE INTO SETTINGS ONCE THE RUN BUTTON IS PRESSED
     */

    //Eventuellement des paramètres externes
    public static void main(String[] args) {
        Application.launch();
    }
}