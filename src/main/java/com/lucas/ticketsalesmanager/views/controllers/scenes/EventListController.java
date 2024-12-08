package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.exception.event.EventNotFoundException;
import javafx.scene.layout.StackPane;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;

import static com.lucas.ticketsalesmanager.Main.screensController;
import static com.lucas.ticketsalesmanager.Main.stageController;
import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.EVENT_DETAILS;

public class EventListController {

    @FXML
    private ListView<String> listEvents;

    private EventController eventController;

    public StackPane stackPane;

    @FXML
    private void initialize() {
        eventController = new EventController();
        try {
            List<Event> events = eventController.listEvents();
            for (Event event : events) {
                listEvents.getItems().add(event.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Erro ao carregar eventos", "Não foi possível carregar a lista de eventos.");
        }
    }

    @FXML
    private void onSelectEvent(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selectedEventName = listEvents.getSelectionModel().getSelectedItem();
            if (selectedEventName != null) {
                try {
                    Event selectedEvent = eventController.getEventByName(selectedEventName);
                    showEventDetails(selectedEvent);
                } catch (EventNotFoundException e) {
                    showErrorMessage("Evento não encontrado", "Não foi possível encontrar o evento selecionado.");
                } catch (Exception e) {
                    showErrorMessage("Erro", "Ocorreu um erro inesperado ao selecionar o evento.");
                    e.printStackTrace();
                }
            }
        }
    }

    private void showEventDetails(Event event) {
        try {
            Parent eventDetailScreen = screensController.loadScreen(EVENT_DETAILS);
            stackPane.getChildren().setAll(eventDetailScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela de detalhes do evento.");
        }
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
