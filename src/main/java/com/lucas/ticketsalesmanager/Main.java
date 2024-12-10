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
package com.lucas.ticketsalesmanager;

import com.lucas.ticketsalesmanager.controllers.LanguageController;
import com.lucas.ticketsalesmanager.models.Languages;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import com.lucas.ticketsalesmanager.views.controllers.scenes.DashboardController;
import com.lucas.ticketsalesmanager.views.controllers.scenes.EventDetailController;
import com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Main application class for the Ticket Sales Manager application.
 * This class is responsible for initializing the primary stage, loading the initial screen,
 * and setting up necessary controllers for managing different aspects of the application.
 * It extends the {@link Application} class from JavaFX and serves as the entry point for the application.
 *
 * <p>
 * The {@link Main} class initializes and configures several core controllers for the application, including
 * the stage controller, screen controller, language controller, and the main dashboard controller. It also
 * loads the login screen at the start of the application.
 * </p>
 */
public class Main extends Application {

    // Controllers for managing different aspects of the application.
    public static ScreensController screensController;
    public static StageController stageController;
    public static LanguageController languageController;
    public static DashboardController dashboardController;
    public static EventDetailController eventDetailController;

    // The primary stage (main window) of the application.
    public static Stage primaryStage;

    /**
     * The entry point for JavaFX applications. This method is automatically called when the application starts.
     * It initializes the primary stage, sets up the necessary controllers, and loads the login screen.
     *
     * @param primaryStage the primary stage (window) of the application
     * @throws Exception if there is an error during initialization or loading the initial screen
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stageController = new StageController(primaryStage);
        screensController = new ScreensController();
        languageController = new LanguageController(Languages.PT_BR);

        // Load the login screen and set it as the main content.
        Parent login = screensController.loadScreen(Scenes.LOGIN);
        stageController.changeStageContent(login);
    }

    /**
     * The main method, used to launch the JavaFX application.
     *
     * @param args command-line arguments (not used in this implementation)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
