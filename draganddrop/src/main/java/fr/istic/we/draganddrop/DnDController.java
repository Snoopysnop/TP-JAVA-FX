package fr.istic.we.draganddrop;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class DnDController implements Initializable {

    @FXML
    private Pane textsPane;

    @FXML
    private Parent root;

    @FXML
    private Slider textCountSlider;

    private Random randomGenerator;
    private IntegerProperty initialNumberOfTexts;

    @FXML
    void generateTexts(ActionEvent event) {
        textsPane.getChildren().clear();
        for (int i = 0; i < initialNumberOfTexts.getValue(); i++) {
            int x = randomGenerator.nextInt((int) textsPane.getWidth());
            int y = randomGenerator.nextInt((int) textsPane.getHeight());
            Text newText = new Text(x, y, Integer.toHexString(randomGenerator.nextInt()));
            newText.setFill(Color.BLACK);
            newText.setOnDragDetected(de -> onDragDetected(de, newText));
            newText.setOnDragEntered(de -> onDragEntered(de, newText));
            newText.setOnDragExited(de -> onDragExited(de, newText));
            newText.setOnDragOver(this::onDragOver);

            newText.setOnDragDropped(de -> onDragDropped(de, newText));
            newText.setOnDragDone(de -> onDragDone(de, newText));
            textsPane.getChildren().add(newText);
        }

    }

    @FXML
    void closeApplication(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void showAboutScene(ActionEvent event) {
        Logger.getGlobal().warning("Unimplemented About feature");
    }

    private void onDragDetected(MouseEvent mouseEvent, Text source) {

        Dragboard dragBoard = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());
        dragBoard.setContent(content);
        mouseEvent.consume();
    }

    private void onDragDone(DragEvent dragEvent, Text source) {
        if (dragEvent.getTransferMode() == TransferMode.MOVE) {
            textsPane.getChildren().remove(source);
            dragEvent.getDragboard().clear();
        }
        dragEvent.consume();
    }

    private void onDragEntered(DragEvent dragEvent, Text target) {
        if (target != dragEvent.getGestureSource()) {
            target.setFill(Color.GREEN);
        }
        dragEvent.consume();
    }

    private void onDragExited(DragEvent dragEvent, Text target) {
        target.setFill(Color.BLACK);
        dragEvent.consume();
    }

    private void onDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.MOVE);     // State that a drop is possible
        dragEvent.consume();
    }

    private void onDragDropped(DragEvent dragEvent, Text target) {
        target.setText(target.getText() + dragEvent.getDragboard().getString());
        dragEvent.setDropCompleted(true);
        dragEvent.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        randomGenerator = new Random(System.currentTimeMillis());
        initialNumberOfTexts = new SimpleIntegerProperty();
        initialNumberOfTexts.bind(textCountSlider.valueProperty());
    }
}
