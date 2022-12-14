module fr.istic.we.draganddrop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens fr.istic.we.draganddrop to javafx.fxml;
    exports fr.istic.we.draganddrop;
}