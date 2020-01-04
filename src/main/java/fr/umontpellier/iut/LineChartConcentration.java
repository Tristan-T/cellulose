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

public class LineChartConcentration extends Application {
    private ScheduledExecutorService scheduledExecutorService;
    private XYChart.Series<String, Number> series = new XYChart.Series<>();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Concentration totale VS Temps");

        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis(); //plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Temps (en s)");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Concentration totale (en UNITE)");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Concentration totale VS Temps");
        lineChart.setAnimated(false); // disable animations

        //defining a series to display data
        series.setName("Concentration totale");

        // add series to chart
        lineChart.getData().add(series);

        // setup scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();
        series.nodeProperty().get().setStyle("-fx-stroke: #A000A0;");

        // setup a scheduled executor to periodically put data into the chart
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    }

    public void update(double value){
        Date now = new Date();
        series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), value));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        scheduledExecutorService.shutdownNow();
    }
}