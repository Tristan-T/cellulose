package fr.umontpellier.iut;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class SpinnerSetterDouble extends SpinnerSetter {
    protected Spinner<Double> spinner;

    public SpinnerSetterDouble(String nom, double minValue, double maxValue, double defaultValue, String unit) {
        super(nom, minValue, maxValue, defaultValue, unit);
    }

    @Override
    protected void instantiateSpinner() {
        //Spinner for more precise tuning
        spinner = new Spinner<>();
        SpinnerValueFactory<Double> spinnerFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(minValue, Integer.MAX_VALUE, defaultValue);
        spinner.setValueFactory(spinnerFactory);
        spinner.setEditable(true);

        //Hack to update values once the spinner lose focus
        spinner.focusedProperty().addListener((s, old_value, new_value) -> {
            if (new_value) return;
            commitEditorText(spinner);
        });

        //Slider event listener
        slider.valueProperty().addListener((
                ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) -> {
            sliderValue.setText(String.format("%.2f", new_val.floatValue()) + " " +unit);
            //Changes the text color once value is updated
            sliderValue.setTextFill(Color.RED);
            //Also update the spinner
            spinnerFactory.setValue(new_val.doubleValue());
            //Updating class value
            value = new_val.doubleValue();
        });

        //Spinner listener
        spinnerFactory.valueProperty().addListener((
                ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) -> {
            sliderValue.setText(String.format("%.2f", new_val.floatValue()) + " " +unit);
            //Changes the text color once value is updated
            sliderValue.setTextFill(Color.RED);
            //Change slider value
            //Update when current value is superior to max only (as set by the spinner for example)
            if(new_val.doubleValue()>=slider.getMax())   {
                slider.setMax(new_val.doubleValue());
                slider.setMajorTickUnit((int) new_val.doubleValue() / 3);
                slider.setMinorTickCount((int) new_val.doubleValue() / 15);
            }
            slider.setValue(new_val.doubleValue());

            //Updating class value
            value = new_val.doubleValue();
        });

        look(spinner);
    }

}
