package com.lucas.ticketsalesmanager;

import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    public static ScreensController screensController;
    public static StageController stageController;
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stageController = new StageController(primaryStage);
        screensController = new ScreensController();

        Parent login = screensController.loadScreen(Scenes.LOGIN);
        stageController.changeStageContent(login);
    }

    public Stage getPrimaryStage(Stage primaryStage) {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
