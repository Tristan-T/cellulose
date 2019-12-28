package fr.umontpellier.iut;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;

public class SpinnerSetterInteger extends SpinnerSetter {
    protected Spinner<Integer> spinner;

    public SpinnerSetterInteger(String nom, double minValue, double maxValue, double defaultValue, String unit) {
        super(nom, minValue, maxValue, defaultValue, unit);
    }

    @Override
    protected void instantiateSpinner() {
        //Setting text value
        this.sliderValue.setText(String.format("%d",(int) this.getValue()) + " " +unit);
        //Spinner for more precise tuning
        spinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory((int) minValue, Integer.MAX_VALUE, (int) defaultValue);
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
            sliderValue.setText(String.format("%d", new_val.intValue()) + " " +unit);
            //Changes the text color once value is updated
            sliderValue.setTextFill(Color.RED);
            //Also update the spinner
            spinnerFactory.setValue(new_val.intValue());
            //Updating class value
            value = new_val.intValue();
        });

        //Spinner listener
        spinnerFactory.valueProperty().addListener((
                ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) -> {
            sliderValue.setText(String.format("%d", new_val.intValue()) + " " +unit);
            //Changes the text color once value is updated
            sliderValue.setTextFill(Color.RED);
            //Change slider value
            //Update when current value is superior to max only (as set by the spinner for example)
            if(new_val.intValue()>=slider.getMax())   {
                slider.setMax(new_val.intValue());
                slider.setMajorTickUnit((int) new_val / 3);
                slider.setMinorTickCount((int) new_val / 15);
            }
            slider.setValue(new_val.intValue());

            //Updating class value
            value = new_val.intValue();
        });

        look(spinner);
    }
}
