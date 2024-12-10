/***********************************************************************************************
 Author: LUCAS DA CONCEIÇÃO DAMASCENO
 Curricular Component: EXA 863 - MI Programming - 2024.2 - TP01
 Completed on: 09/12/2024
 I declare that this code was prepared by me individually and does not contain any
 code snippet from another colleague or another author, such as from books and
 handouts, and web pages or electronic documents. Any piece of code
 by someone other than mine is highlighted with a citation for the author and source
 of the code, and I am aware that these excerpts will not be considered for evaluation purposes
 ************************************************************************************************/
package com.lucas.ticketsalesmanager.views.controllers;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller class for managing the main stage (window) of the application.
 * This class provides methods to change the content of the stage, show alerts, and close the stage.
 * <p>
 * It handles actions related to the primary window, such as updating the scene and showing different
 * types of alerts to the user. The controller is designed to be used within the context of a JavaFX
 * application with a main window represented by a Stage.
 * </p>
 */
public class StageController {

    private final Stage mainStage;

    /**
     * Constructs a new StageController with the specified main stage.
     * The stage is configured to be resizable, centered on the screen, and given a default title.
     *
     * @param mainStage the primary stage (window) of the application
     */
    public StageController(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setResizable(true);
        this.mainStage.centerOnScreen();
        this.mainStage.setTitle("Ticket Sales Manager");
    }

    /**
     * Changes the content of the main stage by setting a new scene.
     * The scene will be centered on the screen and displayed.
     *
     * @param content the new content (FXML) to set on the stage
     * @throws IllegalArgumentException if the provided content is null
     */
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

    /**
     * Displays an alert to the user with a given type, title, and message.
     * The alert is shown asynchronously on the JavaFX application thread.
     *
     * @param type    the type of alert (e.g., INFORMATION, ERROR, WARNING)
     * @param title   the title of the alert
     * @param message the message to display in the alert
     */
    public void showAlert(AlertType type, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Closes the main stage and exits the application.
     * This method is typically called when the application is being terminated.
     */
    public void closeStage() {
        Platform.exit();
    }
}

