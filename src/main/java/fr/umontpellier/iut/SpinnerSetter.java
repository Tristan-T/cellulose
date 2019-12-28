package fr.umontpellier.iut;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.StringConverter;

public abstract class SpinnerSetter {
    protected String nom;
    protected double minValue, maxValue, defaultValue;
    protected String unit;
    protected double value;
    protected GridPane gridSlider;
    protected Slider slider;
    protected Label sliderCaption;
    protected Label sliderValue;

    public SpinnerSetter(String nom, double minValue, double maxValue, double defaultValue, String unit) {
        this.nom = nom;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.unit = unit;

        //Creating slider part
        this.slider = new Slider(minValue, maxValue, defaultValue);

        //Setting default value for class variable
        this.value = defaultValue;

        //Title and value
        this.sliderCaption = new Label(nom + " value : ");
        this.sliderValue = new Label(Double.toString(slider.getValue()) + " " +unit);

        this.instantiateSpinner();
    }

    abstract protected void instantiateSpinner();

    void look(Spinner spinner) {
        //Slider settings
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit((int) maxValue/3);
        slider.setMinorTickCount((int) maxValue/15);

        //Package in a gridpane
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
        gridSlider.add(spinner, 2, 0);
        gridSlider.add(sliderValue, 3, 0);

        gridSlider.setHalignment(sliderCaption, HPos.RIGHT);

        gridSlider.setHgap(10);
        gridSlider.setVgap(20);

        gridSlider.maxWidth(Double.MAX_VALUE);

        gridSlider.setHgrow(sliderCaption, Priority.ALWAYS);
        gridSlider.setHgrow(slider, Priority.ALWAYS);
        gridSlider.setHgrow(spinner, Priority.ALWAYS);
        gridSlider.setHgrow(sliderValue, Priority.ALWAYS);
    }

    /**
     * Copy and pasted from the Spinner class
     */
    protected static <T> void commitEditorText(Spinner<T> spinner) {
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
