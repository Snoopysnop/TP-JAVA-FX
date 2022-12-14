package fr.istic.we.converter;

import javafx.beans.binding.Binding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ConverterController implements Initializable {

    // The attributes tagged with @FXML will be set by FXMLLoader
    // when loading sample.fxml, using the fx:id FXML attributes in that file.
    @FXML
    private TextField valueField1;

    @FXML
    private TextField valueField2;

    @FXML
    private TextField conversionFactorField;

    // Model attributes in the controller (just for simplicity, in real life they will be in the model...)

    private DoubleProperty value1;

    private Binding<String> value2;

    private DoubleProperty conversionFactor;


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialization of model data
        value1 = new SimpleDoubleProperty();
        conversionFactor = new SimpleDoubleProperty();
        // Needed for two ways conversion String <-> Number
        StringConverter<Number> converter = new NumberStringConverter();

        // Synchronize the text property in the first GUI text field item with the data
        // This is a two ways synchronization between the text field value and the data
        valueField1.textProperty().bindBidirectional(value1, converter);
        // Idem for the conversion factor text field
        conversionFactorField.textProperty().bindBidirectional((Property<Number>) conversionFactor, converter);
        // Create an expression that is automatically computed using one way synchronisation
        // The asString() operation ensures that we get a StringProperty and not a NumberProperty
        value2 = value1.multiply(conversionFactor).asString();
        // Synchronize the expression with the second text field value (one way synchronization)
        valueField2.textProperty().bind(value2);

    }
}