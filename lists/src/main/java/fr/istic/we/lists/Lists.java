package fr.istic.we.lists;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Lists extends Application {

    private final ObservableList<String> modelList = FXCollections.observableArrayList("1");

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu listMenu = new Menu("List");
        MenuItem addMenuItem = new MenuItem("Add");
        // Append some item to the modelList, to demo the automatic view refresh
        addMenuItem.setOnAction(event -> modelList.add(Integer.toString(modelList.size())));
        listMenu.getItems().add(addMenuItem);

        // Ditto for remove
        MenuItem removeMenuItem = new MenuItem("Remove");
        listMenu.getItems().add(removeMenuItem);
        removeMenuItem.setOnAction(event ->
        {
            // No removing on an empty modelList
            if (!modelList.isEmpty()) {
                modelList.remove(0);
            }
        });
        menuBar.getMenus().add(listMenu);
        root.setTop(menuBar);

        // Now the view: set up the one way dependency modelList -> listView contents
        ListView<String> listView = new ListView<>(modelList);
        root.setCenter(listView);

        primaryStage.setTitle("Lists");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
