package fr.umontpellier.iut;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class SpinnerSetterInteger extends SpinnerSetter {
    protected Spinner<Integer> spinner;
    protected SpinnerValueFactory<Integer> spinnerFactory;

    public SpinnerSetterInteger(String nom, double minValue, double maxValue, double defaultValue, String unit) {
        super(nom, minValue, maxValue, defaultValue, unit);
    }

    @Override
    protected void instantiateSpinner() {
        //Setting text value
        this.sliderValue.setText(String.format("%d",(int) this.getValue()) + " " +unit);
        //Spinner for more precise tuning
        spinner = new Spinner<>();
        spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory((int) minValue, Integer.MAX_VALUE, (int) defaultValue, 1);
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

            if(new_val.doubleValue()==defaultValue) {
                sliderValue.setTextFill(Color.BLACK);
            }

            //Also update the spinner
            spinnerFactory.setValue(new_val.intValue());
            //Updating class value
            value = new_val.intValue();
        });

        //Spinner listener
        spinnerFactory.valueProperty().addListener((
                ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) -> {

            //If the user inputs nothing or something incorrect, we reset to the default value
            if(new_val==null) {
                new_val= (int) defaultValue;
            } else {
                new_val = new_val.intValue();
            }

            sliderValue.setText(String.format("%d", new_val.intValue()) + " " +unit);
            //Changes the text color once value is updated
            sliderValue.setTextFill(Color.RED);
            //Change slider value
            //Update when current value is superior to max only (as set by the spinner for example)
            if(new_val.intValue()>=slider.getMax())   {
                slider.setMax(new_val.intValue());
                slider.setMajorTickUnit(new_val.intValue() / 4);
                slider.setMinorTickCount(5);
            }
            if(new_val.doubleValue()==defaultValue) {
                sliderValue.setTextFill(Color.BLACK);
            }

            slider.setValue(new_val.intValue());

            //Updating class value
            value = new_val.intValue();
        });

        //Prevent the user from inputting whatever inside the spinner
        TextFormatter<Integer> formatter = new TextFormatter<>(spinnerFactory.getConverter(), spinnerFactory.getValue());
        spinner.getEditor().setTextFormatter(formatter);
        spinnerFactory.valueProperty().bindBidirectional(formatter.valueProperty());

        look(spinner);
    }

    protected void updateValue(double value) {
        defaultValue = value;
        maxValue = value*2;
        slider.setMax(maxValue);
        slider.setMajorTickUnit(maxValue / 4);
        slider.setMinorTickCount(5);

        slider.setValue(value);
        spinner.getValueFactory().setValue((int) value);
    }

    protected void disable() {
        spinner.setDisable(true);
        slider.setDisable(true);
    }

    protected void enable() {
        spinner.setDisable(false);
        slider.setDisable(false);
    }
}
