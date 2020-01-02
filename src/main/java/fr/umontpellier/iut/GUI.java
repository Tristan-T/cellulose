package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class GUI extends Application {

    /**
     * NOTES :
     * We will not be using more than one scene, so that the software can be used without
     * having to click everywhere to get to a certain point, it will show everything in the main
     * window.
     */

    // Simulation
    private static SpinnerSetterDouble timeDelta = new SpinnerSetterDouble("timeDelta", 0, 200, 10, "h");
    private static SpinnerSetterInteger initialBacteriaAmount = new SpinnerSetterInteger("initialBacteriaAmount", 0, 200, 10, "bacteria"); //INT
    private static SpinnerSetterInteger timeDeltaSubdivision = new SpinnerSetterInteger("timeDeltaSubdivision", 0, 200, 10, "subdivision"); //INT
    private static SpinnerSetterDouble maxDuration = new SpinnerSetterDouble("maxDuration", 0, 200, 10, "h");

    // Environment
    private static SpinnerSetterDouble halfLength = new SpinnerSetterDouble("halfLength", 0, 200, 10, "µm");
    private static SpinnerSetterInteger cellsPerSide = new SpinnerSetterInteger("cellsPerSide", 0, 200, 10, "cell(s)"); //INT
    private static SpinnerSetterDouble substratumRadius = new SpinnerSetterDouble("substratumRadius", 0, 200, 10, "µm");

    // Cell
    private static SpinnerSetterDouble cMin = new SpinnerSetterDouble("cMin", 0, 200, 10, "pg/µm²");
    private static SpinnerSetterDouble vDiff = new SpinnerSetterDouble("vDiff", 0, 200, 10, "µm²/h");
    private static SpinnerSetterDouble cIni = new SpinnerSetterDouble("cIni", 0, 200, 10, "pg/µm²");

    // Bacterium
    private static SpinnerSetterDouble bDiff = new SpinnerSetterDouble("bDiff", 0, 200, 10, "µm/√h");
    private static SpinnerSetterDouble mIni = new SpinnerSetterDouble("mIni", 0, 200, 10, "pg");
    private static SpinnerSetterDouble vCons = new SpinnerSetterDouble("vCons", 0, 200, 10, "pg/h");
    private static SpinnerSetterDouble kConv = new SpinnerSetterDouble("kConv", 0, 100, 10, "%"); //Don't forget to divide by 100
    private static SpinnerSetterDouble vd = new SpinnerSetterDouble("vd", 0, 200, 10, "µm⁴/pgh");



    //Getting screen properties
    private static Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    //Image of the model
    private static ImageView imageView;

    //Scene
    private static Scene mainScene;

    //All the buttons
    private static Button runButton;
    private static Button stopButton;
    private static Button importButton;
    private static Button exportButton;

    //Nom du fichier de log
    private static String outputFilename;

    //Thread de calcul
    private static Service<Void> calculateService;

    //Should reload for config
    private static boolean shouldReload = false;






    private static TabPane settingTabs() {

        TabPane settingsBox = new TabPane();
        settingsBox.setPadding(new Insets(10, 10, 10, 10));

        // Creating pane to correctly place setters per categories
        GridPane simulationGridPane = new GridPane();
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

        //Disable ability to close tabs
        settingsBox.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        return settingsBox;
    }

    private static GridPane launcherBox(Stage stage) throws IOException {
        GridPane launcher = new GridPane();
        launcher.setHgap(100);
        launcher.setVgap(5);
        launcher.setPadding(new Insets(10, 10, 10, 10));

        //START BUTTON
        InputStream streamRun = GUI.class.getResourceAsStream("/run.png");
        Image runImage = new Image(streamRun);
        runButton = new Button("Lancer", new ImageView(runImage));
        runButton.setDefaultButton(true);
        //Function triggered by start
        runButton.setOnAction((ActionEvent event) -> calculate());

        //STOP BUTTON
        InputStream streamStop = GUI.class.getResourceAsStream("/stop.png");
        Image stopImage = new Image(streamStop);
        stopButton = new Button("Arrêter", new ImageView(stopImage));
        stopButton.setCancelButton(true);
        //Function triggered by stop
        stopButton.setOnAction((ActionEvent event) -> stopCalculate());
        //Disable it by default
        stopButton.setDisable(true);

        //File filter
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("Configuration files (*.json)", "*.json");

        importButton = new Button("Import config");

        importButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser settingImporter = new FileChooser();
                //Set filter
                settingImporter.getExtensionFilters().add(jsonFilter);
                settingImporter.setTitle("Choose a configuration file");
                //Setting initial directory
                String currentDirectoryString = System.getProperty("user.dir");
                File currentDirectory = new File(currentDirectoryString);
                if(currentDirectory.canRead()) {
                    settingImporter.setInitialDirectory(currentDirectory);
                }

                File selectedFile = settingImporter.showOpenDialog(stage);

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

                        //Configuration is imported here
                        //Exception is handled by Settings
                        try {
                            importSettings(selectedFile);
                        } catch (IOException e){
                            Alert importError = new Alert(Alert.AlertType.ERROR);
                            importError.setTitle("import error");
                            importError.setHeaderText(null);
                            importError.setContentText("Can't import configuration");

                            importError.showAndWait();
                        }

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


        exportButton = new Button("Save configuration");
        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser settingExporter = new FileChooser();
                settingExporter.setTitle("Save your configuration");
                //Set extension filter for text files
                settingExporter.getExtensionFilters().add(jsonFilter);
                //Setting initial directory
                String currentDirectoryString = System.getProperty("user.dir");
                File currentDirectory = new File(currentDirectoryString);
                if(currentDirectory.canRead()) {
                    settingExporter.setInitialDirectory(currentDirectory);
                }

                File savedFile = settingExporter.showSaveDialog(stage);

                if (savedFile != null) {
                    //Exporting the configuration
                    try {
                        exportSettings(savedFile);
                    } catch(IOException e) {
                        Alert saveError = new Alert(Alert.AlertType.ERROR);
                        saveError.setTitle("Export error");
                        saveError.setHeaderText(null);
                        saveError.setContentText("Can't export configuration");

                        saveError.showAndWait();
                    }

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



        launcher.add(importButton, 0, 0);
        launcher.add(exportButton, 1, 0);
        launcher.add(runButton, 0, 1);
        launcher.add(stopButton, 1, 1);

        return launcher;
    }

    private static StackPane celluloseModel() {
        StackPane imagePane = new StackPane();
        imagePane.setPrefWidth(700);
        imageView = new ImageView();

        InputStream defaultImage = GUI.class.getResourceAsStream("/default.png");

        Image img = new Image(defaultImage);
        imageView.setImage(img);
        imagePane.getChildren().add(imageView);
        imagePane.setAlignment(imageView, Pos.CENTER);

        //Centering the image
        double w = 0;
        double h = 0;

        double ratioX = imageView.getFitWidth() / img.getWidth();
        double ratioY = imageView.getFitHeight() / img.getHeight();

        double reducCoeff = 0;
        reducCoeff = Math.min(ratioX, ratioY);

        w = img.getWidth() * reducCoeff;
        h = img.getHeight() * reducCoeff;

        imageView.setX((imageView.getFitWidth() - w) / 2);
        imageView.setY((imageView.getFitHeight() - h) / 2);

        return imagePane;
    }

    public static void updateModel(Image img) {
        imageView.setImage(img);
    }

    private static void importSettings(File file) throws IOException{
        Settings.loadConfig(file.getAbsolutePath());

        setSpinnerFromSettings();
    }

    private static void setSpinnerFromSettings() {
        timeDelta.setValue(Settings.getSimulation_timeDelta());
        initialBacteriaAmount.setValue(Settings.getSimulation_initialBacteriaAmount());
        timeDeltaSubdivision.setValue(Settings.getSimulation_timeDeltaSubdivision());
        maxDuration.setValue(Settings.getSimulation_maxDuration());

        halfLength.setValue(Settings.getEnvironment_halfLength());
        cellsPerSide.setValue(Settings.getEnvironment_cellsPerSide());
        substratumRadius.setValue(Settings.getEnvironment_substratumRadius());

        cMin.setValue(Settings.getCell_cMin());
        vDiff.setValue(Settings.getCell_vDiff());
        cIni.setValue(Settings.getCell_cIni());

        bDiff.setValue(Settings.getBacterium_bDiff());
        mIni.setValue(Settings.getBacterium_mIni());
        vCons.setValue(Settings.getBacterium_vCons());
        kConv.setValue(Settings.getBacterium_kConv());
        vd.setValue(Settings.getBacterium_vd());
    }

    //Get the spinner values and set them for the rest of the program
    private static void setSettingsFromSpinner() {
        Settings.setSimulation_timeDelta(timeDelta.getValue());
        Settings.setSimulation_initialBacteriaAmount((int) initialBacteriaAmount.getValue());
        Settings.setSimulation_timeDeltaSubdivision((int) timeDeltaSubdivision.getValue());
        Settings.setSimulation_maxDuration(maxDuration.getValue());

        Settings.setEnvironment_halfLength(halfLength.getValue());
        Settings.setEnvironment_cellsPerSide((int) cellsPerSide.getValue());
        Settings.setEnvironment_substratumRadius(substratumRadius.getValue());

        Settings.setCell_cMin(cMin.getValue());
        Settings.setCell_vDiff(vDiff.getValue());
        Settings.setCell_cIni(cIni.getValue());

        Settings.setBacterium_bDiff(bDiff.getValue());
        Settings.setBacterium_mIni(mIni.getValue());
        Settings.setBacterium_vCons(vCons.getValue());
        Settings.setBacterium_kConv(kConv.getValue());
        Settings.setBacterium_vd(vd.getValue());
    }
    
    private static void exportSettings(File file) throws IOException{
        setSettingsFromSpinner();

        try {
            Settings.exportConfig(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String outputFileNamer() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy-HH'h'mm'm'ss's'SSS'ms'") ;
        outputFilename = "runCellulose-" + dateFormat.format(date) + ".json";
        System.out.println(outputFilename);
        return outputFilename;
    }

    private static void calculate() {
        //Properly set all the settings to run the simulation
        setSettingsFromSpinner();
        //Change the cursor to loading
        mainScene.setCursor(Cursor.WAIT);
        //Disable the possibility to change settings mid simulation
        disableAll();
        stopButton.setDisable(false);
        //Set the output file name
        outputFileNamer();

        calculateService = new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        final int maxIterations = 5000000;
                        for (int iterations = 0; iterations < maxIterations; iterations ++) {
                            if (isCancelled()) {
                                break;
                            }
                            System.out.println(iterations);
                        }
                        //Simulation simulation = new Simulation(outputFilename);
                        //simulation.run();
                        return null;
                    }
                };
            }
        };

        //Handle the end cases
        calculateService.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
            switch (newValue) {
                case FAILED:
                    Alert taskFailed = new Alert(Alert.AlertType.ERROR);
                    taskFailed.setTitle("Task error");
                    taskFailed.setHeaderText("Error : " + calculateService.getException());
                    taskFailed.setContentText("Task has failed please try again");

                    taskFailed.showAndWait();
                case CANCELLED:
                    Alert taskCancelled = new Alert(Alert.AlertType.INFORMATION);
                    taskCancelled.setTitle("Task cancelled");
                    taskCancelled.setHeaderText(null);
                    taskCancelled.setContentText("Task was cancelled by the user");

                    taskCancelled.showAndWait();
                case SUCCEEDED:
                    stopCalculate();
                    Alert taskFinished = new Alert(Alert.AlertType.INFORMATION);
                    taskFinished.setTitle("Task complete");
                    taskFinished.setHeaderText(null);
                    taskFinished.setContentText("Cellulose degradation complete");

                    taskFinished.showAndWait();
                    break;
            }
        });
        calculateService.restart();
    }

    private static void stopCalculate() {
        //Set the cursor back to normal
        mainScene.setCursor(Cursor.DEFAULT);
        //Disable the stop button
        stopButton.setDisable(true);
        //Cancel the calculation
        calculateService.cancel();
        //Reenable settings change
        enableAll();
    }

    private static void disableAll() {
        timeDelta.disable();
        initialBacteriaAmount.disable();
        timeDeltaSubdivision.disable();
        maxDuration.disable();
        halfLength.disable();
        cellsPerSide.disable();
        substratumRadius.disable();
        cMin.disable();
        vDiff.disable();
        cIni.disable();
        bDiff.disable();
        mIni.disable();
        vCons.disable();
        kConv.disable();
        vd.disable();

        runButton.setDisable(true);
        exportButton.setDisable(true);
        importButton.setDisable(true);
    }

    private static void enableAll() {
        timeDelta.enable();
        initialBacteriaAmount.enable();
        timeDeltaSubdivision.enable();
        maxDuration.enable();
        halfLength.enable();
        cellsPerSide.enable();
        substratumRadius.enable();
        cMin.enable();
        vDiff.enable();
        cIni.enable();
        bDiff.enable();
        mIni.enable();
        vCons.enable();
        kConv.enable();
        vd.enable();

        runButton.setDisable(false);
        exportButton.setDisable(false);
        importButton.setDisable(false);
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

        BorderPane mainBox  = new BorderPane();

        VBox leftSide = new VBox();

        TabPane settingsTab = settingTabs();
        GridPane launcherBox = launcherBox(stage);
        launcherBox.setAlignment(Pos.CENTER);

        leftSide.getChildren().add(settingsTab);
        leftSide.getChildren().add(launcherBox);

        if(shouldReload) {
            setSpinnerFromSettings();
        }


        mainBox.setLeft(leftSide);

        StackPane modelPane = celluloseModel();
        mainBox.setCenter(modelPane);

        StackPane graphPane = null;
        mainBox.setRight(graphPane);



        //Creating and adding settings to the scene
        mainScene = new Scene(mainBox);



        //Adding the scene to the stage
        stage.setScene(mainScene);


        stage.show();
    }

    //Eventuellement des paramètres externes
    public static void main(String[] args) {
        int argsLength = args.length;
        if (argsLength==0) {
            Application.launch();
        } else if (argsLength==1) {
            if(args[0].equals("--noGui") || args[0].equals("-ng")) {
                System.out.println("Launching in noGUI");
                Simulation simulation = new Simulation(outputFileNamer());
                simulation.run();
                System.exit(0);
            } else {
                //Means the user should have inputted a file name
                File tempFile = new File(args[0]);
                if(tempFile.exists()) {
                    if (args[0].endsWith(".json")) {
                        shouldReload = true;
                        try {
                            Settings.loadConfig(args[0]);
                        } catch (IOException e) {
                            Alert importError = new Alert(Alert.AlertType.ERROR);
                            importError.setTitle("Import error");
                            importError.setHeaderText(null);
                            importError.setContentText("Couldn't import configuration, using default settings");

                            importError.showAndWait();
                        }
                        Application.launch();
                    } else {
                        Alert importError = new Alert(Alert.AlertType.ERROR);
                        importError.setTitle("Import error");
                        importError.setHeaderText(null);
                        importError.setContentText("Not a JSON file, using default settings");

                        importError.showAndWait();
                    }
                } else {
                    Alert importError = new Alert(Alert.AlertType.ERROR);
                    importError.setTitle("Import error");
                    importError.setHeaderText(null);
                    importError.setContentText("File doesn't exist, using default settings");

                    importError.showAndWait();
                }
            }
        } else if (argsLength==2) {
            if (args[1].equals("--noGui") || args[1].equals("-ng")) {
                File tempFile = new File(args[0]);
                try {
                    Settings.loadConfig(args[0]);
                } catch (Exception e) {
                    System.out.println("Couldn't open or find file");
                    System.exit(1);
                }
                System.out.println("Launching in noGUI");
                Simulation simulation = new Simulation(outputFileNamer());
                simulation.run();
                System.exit(0);
            } else {
                System.out.println("Wrong argument : cellulose settingsName <--noGUI or -ng>");
                System.exit(1);
            }
        }
    }
}