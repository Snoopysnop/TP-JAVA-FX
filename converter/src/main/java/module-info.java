module fr.istic.we.converter {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.istic.we.converter to javafx.fxml;
    exports fr.istic.we.converter;
}