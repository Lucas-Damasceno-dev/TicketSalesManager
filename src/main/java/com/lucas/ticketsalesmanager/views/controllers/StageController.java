package com.lucas.ticketsalesmanager.views.controllers;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class StageController {

    private final Stage mainStage;

    public StageController(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setResizable(true);
        this.mainStage.centerOnScreen();
        this.mainStage.setTitle("Ticket Sales Manager");
    }

    public void changeStageContent(Parent content) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null.");
        }
        Platform.runLater(() -> {
            this.mainStage.setScene(new Scene(content));
            this.mainStage.centerOnScreen();
            this.mainStage.show();
        });
    }

    public void showAlert(Alert.AlertType type, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void closeStage() {
        Platform.exit();
    }
}
