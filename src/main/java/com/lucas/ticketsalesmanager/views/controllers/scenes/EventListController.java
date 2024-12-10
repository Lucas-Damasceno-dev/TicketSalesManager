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
import com.lucas.ticketsalesmanager.exception.event.EventNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.EVENT_DETAILS;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * Controller responsible for managing the event list screen.
 * This screen displays a list of recent and upcoming events, allowing users to
 * view details of each event.
 *
 * <ul>
 *     <li>Recent Events: Displays a list of events recently added or featured.</li>
 *     <li>Upcoming Events: Displays a list of events scheduled for the future.</li>
 * </ul>
 *
 * The controller interacts with the EventController to retrieve event data and
 * updates the user interface accordingly.
 */
public class EventListController {

    private EventController eventController;

    @FXML
    private Label lblRecentEvents;

    @FXML
    private ListView<String> listViewRecentEvents;

    @FXML
    private Label lblNextEvents;

    @FXML
    private ListView<String> listViewUpcomingEvents;

    private List<Event> events;

    /**
     * Initializes the controller by setting up the event list view.
     * Loads events from the EventController and populates the Recent Events list.
     * If an error occurs while loading events, an error message is displayed.
     */
    public void initialize() {
        eventController = new EventController();
        try {
            events = Main.dashboardController.encontredEvents();
            events = (events == null || events.isEmpty())
                    ? eventController.listEvents()
                    : events;

            for (Event event : events) {
                listViewRecentEvents.getItems().add(event.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error loading events", "Unable to load the event list.");
        }
    }

    /**
     * Opens the details of the selected event.
     * Loads the event details screen and passes the selected event to it.
     *
     * @param event The event to display in detail.
     * @throws IOException if an error occurs while loading the event details screen.
     */
    private void showEventDetails(Event event) throws IOException {
        FXMLLoader loader = Main.screensController.getLoader(EVENT_DETAILS);
        Parent eventDetails = loader.load();
        ((EventDetailController) loader.getController()).setEventAndUser(event);
        Main.dashboardController.setParentAtStackPane(eventDetails);
    }

    /**
     * Displays an error message in an alert window.
     *
     * @param title The title of the alert window.
     * @param message The content of the alert message.
     */
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the mouse click event on the Recent Events list.
     * If the user double-clicks on an event, it loads the details for that event.
     *
     * @param event The mouse click event triggered by the user.
     */
    @FXML
    public void onMouseClickedRecentEvents(MouseEvent event) {
        if (event.getClickCount() < 2) {
            return;
        }

        String selectedEventName = listViewRecentEvents.getSelectionModel().getSelectedItem();
        if (selectedEventName == null) {
            return;
        }

        try {
            Event selectedEvent = events.stream()
                    .filter(ev -> ev.getName().equals(selectedEventName))
                    .findAny()
                    .orElseThrow(() -> new EventNotFoundException(selectedEventName));

            showEventDetails(selectedEvent);
        } catch (EventNotFoundException e) {
            showErrorMessage("Event not found", "The selected event could not be found.");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Error", "An unexpected error occurred while selecting the event.");
        }
    }
}
