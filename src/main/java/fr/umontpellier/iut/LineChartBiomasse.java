package fr.umontpellier.iut;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LineChartBiomasse extends Application {
    //final int WINDOW_SIZE = 10;
    private ScheduledExecutorService scheduledExecutorService;
    private static Simulation simulation;

    public static void main(String[] args) {
        launch(args);
    }

    public static void setEnvironment(Simulation simulation) {
        LineChartBiomasse.simulation = simulation;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Biomasse VS Temps");

        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); //plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps (en s)");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Biomasse (en mg)");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Biomasse VS Temps");
        lineChart.setAnimated(false); // disable animations

        //defining a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Biomasse");

        // add series to chart
        lineChart.getData().add(series);

        // setup scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();

        // this is used to display time in HH:mm:ss format
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


        // put dummy data onto graph per second
        scheduledExecutorService.scheduleAtFixedRate(() -> {


            // Update the chart
            Platform.runLater(() -> {
                // get current time
                Date now = new Date();
                // put random number with current time
                series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), 2/*simulation.environment.getBiomass()*/));

                /*if (series.getData().size() > WINDOW_SIZE)
                    series.getData().remove(0);*/
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}