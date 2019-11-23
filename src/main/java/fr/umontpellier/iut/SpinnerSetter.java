package fr.umontpellier.iut;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class SpinnerSetter {
    double value;
    GridPane gridSlider;

    public SpinnerSetter(String nom, double minValue, double maxValue, double defaultValue, String unit) {
        //Create a slider with values
        Slider slider = new Slider(minValue, maxValue, defaultValue);
        //Slider settings
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit((int) maxValue/3);
        slider.setMinorTickCount((int) maxValue/15);

        //Setting default value for class variable
        value = defaultValue;

        //Title and value
        Label sliderCaption = new Label(nom + " value : ");
        Label sliderValue = new Label(Double.toString(slider.getValue()) + " " +unit);

        //Spinner for more precise tuning
        Spinner<Double> sliderSpinner = new Spinner<Double>();
        SpinnerValueFactory<Double> sliderSpinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(minValue, maxValue, defaultValue);
        sliderSpinner.setValueFactory(sliderSpinnerFactory);
        sliderSpinner.setEditable(true);

        //Hack to update values once the spinner lose focus
        sliderSpinner.focusedProperty().addListener((s, old_value, new_value) -> {
            if (new_value) return;
            commitEditorText(sliderSpinner);
        });

        //Slider event listener
        slider.valueProperty().addListener((
                ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) -> {
            sliderValue.setText(String.format("%.2f", new_val) + " " +unit);
            //Changes the text color once value is updated
            sliderValue.setTextFill(Color.RED);
            //Also update the spinner
            sliderSpinnerFactory.setValue(new_val.doubleValue());
            //Updating class value
            value = new_val.doubleValue();
        });

        //Spinner listener
        sliderSpinnerFactory.valueProperty().addListener((
                ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) -> {
            sliderValue.setText(String.format("%.2f", new_val) + " " +unit);
            //Changes the text color once value is updated
            sliderValue.setTextFill(Color.RED);
            //Change slider value
            slider.setValue(new_val.doubleValue());
            //Updating class value
            value = new_val.doubleValue();
        });

        gridSlider = new GridPane();

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(15);

        gridSlider.getColumnConstraints().add(col1);
        gridSlider.getColumnConstraints().add(col2);
        gridSlider.getColumnConstraints().add(col3);
        gridSlider.getColumnConstraints().add(col3);


        gridSlider.add(sliderCaption, 0, 0);
        gridSlider.add(slider, 1, 0);
        gridSlider.add(sliderSpinner, 2, 0);
        gridSlider.add(sliderValue, 3, 0);

        gridSlider.setHalignment(sliderCaption, HPos.RIGHT);

        gridSlider.setHgap(10);
        gridSlider.setVgap(20);
    }

    /**
     * Copy and pasted from the Spinner class
     */
    private static <T> void commitEditorText(Spinner<T> spinner) {
        if (!spinner.isEditable()) return;
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

    public GridPane getSpinnerSetter() {
        return gridSlider;
    }

    public double getValue() {
        return value;
    }




}
