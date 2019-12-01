package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

public class GUI extends Application {

    /**
     * NOTES :
     * We will not be using more than one scene, so that the software can be used without
     * having to click everywhere to get to a certain point, it will show everything in the main
     * window.
     */


    //Getting screen properties
    private static Rectangle2D screenBounds = Screen.getPrimary().getBounds();


    private TabPane settingTabs() {

        TabPane settingsBox = new TabPane();
        settingsBox.setPadding(new Insets(10, 10, 10, 10));

        // Simulation
        SpinnerSetter timeDelta = new SpinnerSetter("timeDelta", 0, 200, 10, "toDefine");
        SpinnerSetter initialBacteriaAmount = new SpinnerSetter("initialBacteriaAmount", 0, 200, 10, "toDefine");
        SpinnerSetter timeDeltaSubdivision = new SpinnerSetter("timeDeltaSubdivision", 0, 200, 10, "toDefine");
        SpinnerSetter maxDuration = new SpinnerSetter("maxDuration", 0, 200, 10, "toDefine");

        // Environment
        SpinnerSetter halfLength = new SpinnerSetter("halfLength", 0, 200, 10, "toDefine");
        SpinnerSetter cellsPerSide = new SpinnerSetter("cellsPerSide", 0, 200, 10, "toDefine");
        SpinnerSetter substratumRadius = new SpinnerSetter("substratumRadius", 0, 200, 10, "toDefine");

        // Cell
        SpinnerSetter cMin = new SpinnerSetter("cMin", 0, 200, 10, "toDefine");
        SpinnerSetter vDiff = new SpinnerSetter("vDiff", 0, 200, 10, "toDefine");
        SpinnerSetter cIni = new SpinnerSetter("cIni", 0, 200, 10, "toDefine");

        // Bacterium
        SpinnerSetter bDiff = new SpinnerSetter("bDiff", 0, 200, 10, "toDefine");
        SpinnerSetter mIni = new SpinnerSetter("mIni", 0, 200, 10, "toDefine");
        SpinnerSetter vCons = new SpinnerSetter("vCons", 0, 200, 10, "toDefine");
        SpinnerSetter kConv = new SpinnerSetter("kConv", 0, 200, 10, "toDefine");
        SpinnerSetter vd = new SpinnerSetter("vd", 0, 200, 10, "toDefine");

        // Creating pane to correctly place setters per categories
        GridPane simulationGridPane = new GridPane();
        simulationGridPane.maxWidth(Double.MAX_VALUE);
        GridPane environmentGridPane = new GridPane();
        GridPane cellGridPane = new GridPane();
        GridPane bacteriumGridPane = new GridPane();

        //List for easier aesthetic manipulations
        ArrayList<GridPane> gridPanes = new ArrayList<>();
        gridPanes.add(simulationGridPane);
        gridPanes.add(environmentGridPane);
        gridPanes.add(cellGridPane);
        gridPanes.add(bacteriumGridPane);

        for (GridPane g: gridPanes) {
            g.setVgap(10);
            g.setHgap(70);
            g.getColumnConstraints().add(new ColumnConstraints(600));
            g.setMinHeight(screenBounds.getHeight()/1.5);
            g.setMinWidth(screenBounds.getWidth()/4);
        }

        // Creating tabs to make changing settings easier
        Tab cellTab = new Tab("Cells", cellGridPane);
        Tab environmentTab = new Tab("Environment", environmentGridPane);
        Tab bacteriumTab = new Tab("Bacterium", bacteriumGridPane);
        Tab simulationTab = new Tab("Simulation", simulationGridPane);

        // Adding each element to their position in their tabs
        // Simulation
        simulationGridPane.add(timeDelta.getSpinnerSetter(), 0, 0);
        simulationGridPane.add(initialBacteriaAmount.getSpinnerSetter(), 0, 1);
        simulationGridPane.add(timeDeltaSubdivision.getSpinnerSetter(), 0, 2);
        simulationGridPane.add(maxDuration.getSpinnerSetter(), 0, 3);

        // Environment
        environmentGridPane.add(halfLength.getSpinnerSetter(), 0, 0);
        environmentGridPane.add(cellsPerSide.getSpinnerSetter(), 0, 1);
        environmentGridPane.add(substratumRadius.getSpinnerSetter(), 0, 2);

        // Cells
        cellGridPane.add(cMin.getSpinnerSetter(), 0, 0);
        cellGridPane.add(vDiff.getSpinnerSetter(), 0, 1);
        cellGridPane.add(cIni.getSpinnerSetter(), 0, 2);

        // Bacterium
        bacteriumGridPane.add(bDiff.getSpinnerSetter(), 0, 0);
        bacteriumGridPane.add(mIni.getSpinnerSetter(), 0, 1);
        bacteriumGridPane.add(vCons.getSpinnerSetter(), 0, 2);
        bacteriumGridPane.add(kConv.getSpinnerSetter(), 0, 3);
        bacteriumGridPane.add(vd.getSpinnerSetter(), 0, 4);

        settingsBox.getTabs().add(simulationTab);
        settingsBox.getTabs().add(environmentTab);
        settingsBox.getTabs().add(cellTab);
        settingsBox.getTabs().add(bacteriumTab);

        settingsBox.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        return settingsBox;
    }

    private GridPane launcherBox(Stage stage) throws IOException {
        GridPane launcher = new GridPane();

        SpinnerSetter numberRun = new SpinnerSetter("Nombre d'exécutions", 0, 100, 1, "tours");

        InputStream streamRun = getClass().getResourceAsStream("/run.png");
        Image runImage = new Image(streamRun);
        Button runButton = new Button("Lancer", new ImageView(runImage));
        runButton.setDefaultButton(true);

        InputStream streamStop = getClass().getResourceAsStream("/stop.png");
        Image stopImage = new Image(streamStop);
        Button stopButton = new Button("Arrêter", new ImageView(stopImage));
        stopButton.setCancelButton(true);

        //File filter
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("Configuration files (*.json)", "*.json");

        Button importButton = new Button("Import config");

        importButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser settingImporter = new FileChooser();
                //Set filter
                settingImporter.getExtensionFilters().add(jsonFilter);
                File selectedFile = settingImporter.showOpenDialog(null);
                settingImporter.setTitle("Choose a configuration file");


                if (selectedFile != null) {


                    Alert importConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    importConfirmation.setTitle("Confirmation Dialog");
                    importConfirmation.setHeaderText("Importing this configuration will erase your previous configuration");

                    Optional<ButtonType> result = importConfirmation.showAndWait();
                    if (result.get() == ButtonType.OK){
                        Alert importSuccessful = new Alert(Alert.AlertType.INFORMATION);
                        importSuccessful.setTitle("Configuration imported");
                        importSuccessful.setHeaderText(null);
                        importSuccessful.setContentText("File imported : " + selectedFile.getName());
                        /*
                        * MUST IMPORT THE CONFIGURATION HERE
                         */

                        importSuccessful.showAndWait();
                    } else {
                        Alert importCancel = new Alert(Alert.AlertType.INFORMATION);
                        importCancel.setTitle("Cancelled");
                        importCancel.setHeaderText(null);
                        importCancel.setContentText("Import was cancelled");

                        importCancel.showAndWait();
                    }

                }
                else {
                    Alert importCancel = new Alert(Alert.AlertType.INFORMATION);
                    importCancel.setTitle("Cancelled");
                    importCancel.setHeaderText(null);
                    importCancel.setContentText("Import was cancelled");

                    importCancel.showAndWait();
                }
            }
        });


        Button exportButton = new Button("Save configuration");
        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser settingExporter = new FileChooser();
                settingExporter.setTitle("Save your configuration");
                //Set extension filter for text files
                settingExporter.getExtensionFilters().add(jsonFilter);
                File savedFile = settingExporter.showSaveDialog(stage);



                if (savedFile != null) {
                    /*
                     * EXPORTING THE CONFIGURATION NEEDS TO BE HERE
                     * MUST ADD THE CONFIGURATION RETRIEVE PROCESS
                     */
                    Settings.exportConfig(savedFile.getName());
                    /* Doesn't want to catch IOException, must check later
                    try {

                    }

                    catch(IOException e) {

                    e.printStackTrace();
                    Alert saveError = new Alert(Alert.AlertType.ERROR);
                    saveError.setTitle("Export error");
                    saveError.setHeaderText(null);
                    saveError.setContentText("Can't export configuration");

                    saveError.showAndWait();

                    throw new IOException("Something happened");
                    }
                    */

                    Alert saveSuccessful = new Alert(Alert.AlertType.INFORMATION);
                    saveSuccessful.setTitle("Configuration exported");
                    saveSuccessful.setHeaderText(null);
                    saveSuccessful.setContentText("Your configuration was exported");

                    saveSuccessful.showAndWait();

                } else {
                    Alert saveCancel = new Alert(Alert.AlertType.INFORMATION);
                    saveCancel.setTitle("Cancelled");
                    saveCancel.setHeaderText(null);
                    saveCancel.setContentText("Export was cancelled");

                    saveCancel.showAndWait();
                }
            }
        });



        launcher.add(numberRun.getSpinnerSetter(), 0, 0);
        launcher.add(importButton, 0, 1);
        launcher.add(exportButton, 1, 1);
        launcher.add(runButton, 0, 2);
        launcher.add(stopButton, 1, 2);



        return launcher;
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

        //Setting window size
        stage.setX(screenBounds.getWidth()/16);
        stage.setY(screenBounds.getHeight()/16);
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

        VBox leftSide = new VBox();
        leftSide.getChildren().add(settingTabs());
        leftSide.getChildren().add(launcherBox(stage));



        //Creating and adding settings to the scene
        Scene mainScene = new Scene(leftSide);



        //Adding the scene to the stage
        stage.setScene(mainScene);


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