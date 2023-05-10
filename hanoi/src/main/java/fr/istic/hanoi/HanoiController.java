package fr.istic.hanoi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import java.net.URL;
import java.util.Deque;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class HanoiController implements Initializable {
    @FXML
    private GridPane grid;
    private HanoiModel model;
    private final int numberOfRows = 8;
    private final int numberOfDisks = 4;
    private final int HEIGHT = 30;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Logger.getGlobal().info("Controller initialisation");
        createGame();
    }
    
    /**
     * Initialisation de la grille
     */

    private void setGrid() {
        for(int i = 0; i < grid.getColumnCount(); i++) {
            for(int j = 0; j < grid.getRowCount(); j++) {
                Rectangle observableNode = new Rectangle(100, HEIGHT+1 , Color.rgb(255, 0, 0, 0));
                observableNode.setOnDragOver(this::onDragOver);
                observableNode.setOnDragDropped(de -> onDragDropped(de, observableNode));
                grid.add(observableNode, i, j);
            }
        }
    }

    /**
     * Initialisation d'une partie
     */

    private void createGame() {
        setGrid();
        model = new HanoiModelImpl(numberOfDisks);

        for (Integer i : model.getStack(1)) {
            Rectangle disk = createRectangle(i);
            setDragAndDrop(disk);
            grid.add(disk, 0, numberOfRows - (numberOfDisks - (i - 1)));
        }
    }

    /**
     * Créer un rectangle qui est un des anneaux des tours d'Hanoi
     * @param val une variable qui modifiera la longueur du rectangle
     * @return un rectangle de couleur, d'une certaine taille avec une bordure.
     */

    private Rectangle createRectangle(int val) {

        Rectangle rectangle = new Rectangle(10+(val*30), HEIGHT,Color.rgb(199, 89, 255));
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1);

        return rectangle;
    }

    /**
     * Partie DRAG AND DROP
     */

    private void setDragAndDrop(Rectangle source) {
        source.setOnDragDetected(de -> onDragDetected(de, source));
        source.setOnDragDone(de -> onDragDone(de, source));
    }

    private void onDragDetected(MouseEvent mouseEvent, Rectangle source) {
        Integer indexColumn = GridPane.getColumnIndex(source);

        //On vérifie avant que le déplacement est valide.
        int diskNumber = (((int)source.getWidth() - 10) / 30);

        if (diskNumber == model.getStack(indexColumn+1).getFirst()) {
            Dragboard dragBoard = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(diskNumber + "" + indexColumn);
            dragBoard.setContent(content);
            mouseEvent.consume();
        }
    }

    private void onDragDone(DragEvent dragEvent, Rectangle source) {
        if(dragEvent.getTransferMode() == TransferMode.MOVE) {
            grid.getChildren().remove(source);
            dragEvent.getDragboard().clear();
        }
        dragEvent.consume();
    }

    private void onDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.MOVE);    // State that a drop is possible
        dragEvent.consume();
    }

    private void onDragDropped(DragEvent dragEvent, Node target) {

        boolean dragEventCompleted = true;

        Integer indexColumn = GridPane.getColumnIndex(target);
        String[] data = dragEvent.getDragboard().getString().split("");
        Rectangle source = createRectangle(Integer.parseInt(data[0]));
        setDragAndDrop(source);


        // Si le coup est autorisé, on ajoute un disque à la tour
        try {
            model.move(Integer.parseInt(data[1])+1, indexColumn+1);
            Deque<Integer> stack = model.getStack(indexColumn+1);
            grid.add(source, indexColumn, numberOfRows - stack.size());

            // Implémentation d'un système permettant de relancer le jeu à chaque victoire

            if(model.getStack(grid.getColumnCount()).size() == numberOfDisks) {
                System.out.println("Vous avez gagné ! \nRecommencez une partie !");
                grid.getChildren().clear();
                createGame();
            }
        } catch (IllegalMoveException e) {
            dragEventCompleted = false;
        }

        dragEvent.setDropCompleted(dragEventCompleted);
        dragEvent.consume();
    }

    /**
       Getters
    */

    public int getPrefHeight() {
        return HEIGHT;
    }
}
