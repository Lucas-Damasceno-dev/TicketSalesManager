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

    public void initialize() {
        eventController = new EventController();
        try {
            events = Main.dashboardController.encontredEvents();
            events =  events == null || events.isEmpty()
                    ? eventController.listEvents()
                    : events;
            
            for (Event event : events) {
                listViewRecentEvents.getItems().add(event.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Erro ao carregar eventos", "Não foi possível carregar a lista de eventos.");
        }
    }

    private void showEventDetails(Event event) throws IOException {
        FXMLLoader loader = Main.screensController.getLoader(EVENT_DETAILS);
        Parent eventDetails = loader.load();
        ((EventDetailController) loader.getController()).setEventAndUser(event);
        Main.dashboardController.setParentAtStackPane(eventDetails);
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
            showErrorMessage("Evento não encontrado", "Não foi possível encontrar o evento selecionado.");
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Erro", "Ocorreu um erro inesperado ao selecionar o evento.");
        }
    }
}
