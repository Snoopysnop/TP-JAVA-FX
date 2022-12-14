module fr.istic.we.lists {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.istic.we.lists to javafx.fxml;
    exports fr.istic.we.lists;
}