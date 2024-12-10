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
package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Languages;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.*;
import java.util.LinkedList;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * The {@code DashboardController} class manages the main dashboard screen of the ticket sales system.
 * It handles navigation to other screens, displays the list of events, and manages the user search functionality.
 */
public class DashboardController {

    /**
     * Text field for user input to search for events.
     */
    @FXML
    public TextField searchField;

    /**
     * Button to navigate to the dashboard screen.
     */
    @FXML
    public Button btnDashboard;

    /**
     * Button to navigate to the events screen.
     */
    @FXML
    public Button btnEvents;

    /**
     * Button to navigate to the user profile screen.
     */
    @FXML
    public Button btnProfile;

    /**
     * Button to log out the user and return to the login screen.
     */
    @FXML
    public Button btnLogout;

    /**
     * Button to initiate the event search.
     */
    @FXML
    public Button btnSearch;

    /**
     * Stack pane for managing and switching between screens.
     */
    @FXML
    public StackPane stackPane;

    /**
     * Root HBox layout containing the dashboard content.
     */
    @FXML
    public HBox hboxRoot;

    /**
     * Reference to the current stage controller.
     */
    private StageController stageController;

    /**
     * Reference to the screen controller to manage screen transitions.
     */
    private ScreensController screensController;

    /**
     * Current logged-in user.
     */
    private User currentUser;

    /**
     * List of all available events.
     */
    private List<Event> events;

    /**
     * Button to view the user's tickets.
     */
    @FXML
    private Button btnTickets;

    /**
     * ChoiceBox to allow the user to select the application language.
     */
    @FXML
    private ChoiceBox<Languages> choiceLanguage;

    /**
     * VBox layout for the menu.
     */
    @FXML
    private VBox vboxMenu;

    /**
     * List of events that match the user's search query.
     */
    private List<Event> encountredEvents;

    /**
     * Initializes the dashboard screen, sets up button actions,
     * and populates the list of available events.
     *
     * @throws IOException If an I/O error occurs while loading event data.
     */
    public void initialize() throws IOException {
        stageController = Main.stageController;
        screensController = Main.screensController;
        Main.dashboardController = this;
        currentUser = LoginController.user;

        btnEvents.setVisible(currentUser.isAdmin());

        if (currentUser.isAdmin()) {
            vboxMenu.getChildren().remove(btnTickets);
        } else {
            vboxMenu.getChildren().remove(btnEvents);
        }

        events = new EventController().listAvailableEvents();
        handleEventsClick();
        configureLangChoice();
    }

    /**
     * Sets the reference to the stage controller.
     *
     * @param stageController The {@code StageController} instance.
     */
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    /**
     * Sets the reference to the screen controller.
     *
     * @param screensController The {@code ScreensController} instance.
     */
    public void setScreensController(ScreensController screensController) {
        this.screensController = screensController;
    }

    /**
     * Handles the click event for the dashboard button.
     * Loads the event list screen into the stack pane.
     */
    @FXML
    public void handleDashboardClick() {
        try {
            Parent dashboardScreen = screensController.loadScreen(EVENT_LIST);
            stackPane.getChildren().setAll(dashboardScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Error", "Could not load the dashboard.");
        }
    }

    /**
     * Handles the click event for the profile button.
     * Loads the user profile screen into the stack pane.
     */
    @FXML
    public void handleProfileClick() {
        try {
            Parent profileScreen = screensController.loadScreen(PROFILE);
            stackPane.getChildren().setAll(profileScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Error", "Could not load the profile screen.");
        }
    }

    /**
     * Handles the click event for the events button.
     * Loads the event details screen for administrators and the event list for regular users.
     */
    @FXML
    public void handleEventsClick() {
        try {
            Parent eventScreen = currentUser.isAdmin()
                    ? screensController.loadScreen(EVENT_DETAILS)
                    : screensController.loadScreen(EVENT_LIST);
            stackPane.getChildren().setAll(eventScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Error", "Could not load the events screen.");
        }
    }

    /**
     * Handles the click event for the tickets button.
     * Loads the tickets screen into the stack pane.
     */
    @FXML
    public void handleTicketsClick() {
        try {
            Parent ticketScreen = screensController.loadScreen(TICKETS);
            stackPane.getChildren().setAll(ticketScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Error", "Could not load the tickets screen.");
        }
    }

    /**
     * Replaces the current content of the stack pane with the given parent.
     *
     * @param parent The new content for the stack pane.
     */
    public void setParentAtStackPane(Parent parent) {
        stackPane.getChildren().setAll(parent);
    }

    /**
     * Handles the click event for the logout button.
     * Logs out the user and returns to the login screen.
     */
    @FXML
    public void handleLogoutClick() {
        try {
            LoginController.user = null;
            currentUser = null;
            stageController.changeStageContent(screensController.loadScreen(LOGIN));
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Error", "Could not load the login screen.");
        }
    }

    /**
     * Handles the click event for the search button.
     * Searches for events that match the search query and loads the event list screen.
     */
    @FXML
    public void handleSearchClick() {
        String searchText = searchField.getText();
        encountredEvents = new LinkedList<>();

        if (searchText.isBlank()) {
            return;
        }

        for (Event event : events) {
            if (event.getName().contains(searchText)) {
                encountredEvents.add(event);
            }
        }

        if (encountredEvents.isEmpty()) {
            stageController.showAlert(Alert.AlertType.INFORMATION, "Information", "No events found.");
            return;
        }

        try {
            Parent eventList = screensController.loadScreen(EVENT_LIST);
            stackPane.getChildren().setAll(eventList);
            encountredEvents.clear();
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Error", "Could not load the event list screen.");
        }
    }

    /**
     * Returns the list of events found during the search.
     *
     * @return List of found events.
     */
    public List<Event> encontredEvents() {
        return encountredEvents;
    }

    /**
     * Handles the "Enter" key press on the search field.
     *
     * @param event The key event.
     */
    @FXML
    private void shearchOnKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            handleSearchClick();
        }
    }

    /**
     * Configures the language choice box, allowing the user to switch the application language.
     */
    private void configureLangChoice() {
        choiceLanguage.getItems().addAll(Languages.values());
        choiceLanguage.getSelectionModel().select(Languages.PT_BR);
        choiceLanguage.valueProperty().addListener((ov, old, newValue) -> {
            Main.languageController.updateLanguage(newValue);
            updateInterfaceLang();
        });
    }

    /**
     * Updates the text of interface components to match the selected language.
     */
    private void updateInterfaceLang() {
        btnProfile.setText(Main.languageController.getLabel("profile"));
    }
}
