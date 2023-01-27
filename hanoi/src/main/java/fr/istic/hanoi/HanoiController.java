package fr.istic.hanoi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HanoiController implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    protected void onFileNew() {
        Logger.getGlobal().info("Unimplemented");
    }

    private HanoiModel model;
    private Map<Integer, Text> discViews = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
        // Create the disc views from stack number 1

        Logger.getGlobal().info("Controller initialisation");

        //Create a model just for testing
        model = new HanoiModelImpl(3);

        Font textFont = new Font("Helvetica", 20);
        for (Integer i : model.getStack(1)) {
            Text t = new Text(i.toString());
            t.setFont(textFont);
            t.setTextAlignment(TextAlignment.CENTER);
            discViews.put(i, t);
            grid.add(t, 0, i - 1);
            Logger.getGlobal().info(String.format("Added a new text %s", t));
        }

    }
}