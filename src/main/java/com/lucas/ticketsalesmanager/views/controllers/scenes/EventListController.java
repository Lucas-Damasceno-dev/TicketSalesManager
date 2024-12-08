package com.lucas.ticketsalesmanager.views.controllers.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class EventListController {

    @FXML
    private ListView<String> listEvents;

    @FXML
    private void initialize() {
        // Exemplo de preenchimento da lista
        listEvents.getItems().addAll("Evento 1", "Evento 2", "Evento 3");
    }

    @FXML
    private void onSelectEvent() {
        // Lógica para selecionar evento
        String selectedEvent = listEvents.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            System.out.println("Evento selecionado: " + selectedEvent);
        }
    }
}
