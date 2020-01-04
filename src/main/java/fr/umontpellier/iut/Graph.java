package fr.umontpellier.iut;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Graph {
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    private LineChart<Number, Number> lineChart;


    public Graph(String title1, String unit1, String title2, String unit2, String color) {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis(); //plot against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(title1 + " (en " + unit1 + ")");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel(title2 + " (en " + unit2 + ")");
        yAxis.setAnimated(false); // axis animations are removed

        //creating the line chart with two axis created above
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title1.substring(0, 1).toUpperCase() + title1.substring(1) + " VS " + title2.substring(0, 1).toUpperCase() + title2.substring(1));
        lineChart.setAnimated(false); // disable animations

        lineChart.setCreateSymbols(false);

        //defining a series to display data
        series.setName(title2);



        // add series to chart
        lineChart.getData().add(series);


        // setup a scheduled executor to periodically put data into the chart
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        series.nodeProperty().get().setStyle("-fx-stroke:" + color + ";");
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }

    public void update(double time, double value){
        series.getData().add(new XYChart.Data<>(time, value));
    }

    public void reset() {
        series.getData().clear();
    }

}
