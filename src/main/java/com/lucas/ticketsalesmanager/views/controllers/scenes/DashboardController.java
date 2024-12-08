package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.exception.event.EventNotFoundException;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.*;

public class DashboardController {

    public TextField searchField;
    public Button btnDashboard;
    public Button btnEvents;
    public Button btnProfile;
    public Button btnLogout;
    public Button btnSearch;
    public StackPane stackPane;
    public HBox hboxRoot;

    @FXML
    public ListView<Event> eventListView;

    private StageController stageController;
    private ScreensController screensController;
    private EventController eventController;
    private User currentUser;

    @FXML
    private void initialize() throws IOException {
        stageController = Main.stageController;
        screensController = new ScreensController();
        eventController = new EventController();

        currentUser = LoginController.user;

        btnEvents.setVisible(currentUser.isAdmin());

        loadEventList();

        btnDashboard.setOnAction(event -> {
            try {
                handleDashboardClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        });

        btnEvents.setOnAction(event -> {
            try {
                handleEventsClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        });

        btnProfile.setOnAction(event -> {
            try {
                handleProfileClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        });

        btnLogout.setOnAction(event -> {
            try {
                handleLogoutClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        });

        btnSearch.setOnAction(event -> {
            try {
                handleSearchClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        });
    }

    private void loadEventList() {
        try {
            List<Event> events = eventController.listEvents();
            handleDashboardClick();
            if (!events.isEmpty()) {
                eventListView.getItems().setAll(events);
                eventListView.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() == 1) {
                        Event selectedEvent = eventListView.getSelectionModel().getSelectedItem();
                        if (selectedEvent != null) {
                            handleEventDetailClick(selectedEvent);
                        }
                    }
                });
            }
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Nenhum evento cadastrado.");
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a lista de eventos.");
        }
    }

    private void handleEventDetailClick(Event selectedEvent) {
        try {
            Parent eventDetailScreen = screensController.loadScreen(EVENT_DETAILS);
            stackPane.getChildren().setAll(eventDetailScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os detalhes do evento.");
        }
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void setScreensController(ScreensController screensController) {
        this.screensController = screensController;
    }

    public void handleDashboardClick() {
        try {
            Parent dashboardScreen = screensController.loadScreen(EVENT_LIST);
            stackPane.getChildren().setAll(dashboardScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a dashboard.");
        }
    }

    public void handleProfileClick() {
        try {
            Parent profileScreen = screensController.loadScreen(PROFILE);
            stackPane.getChildren().setAll(profileScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela de perfil.");
        }
    }

    public void handleEventsClick() {
        try {
            if (currentUser.isAdmin()) {
                Parent eventDetailScreen = screensController.loadScreen(EVENT_DETAILS);
                stackPane.getChildren().setAll(eventDetailScreen);
            } else {
                loadEventList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela de eventos.");
        }
    }

    public void handleTicketsClick() {
        try {
            Parent ticketScreen = screensController.loadScreen(TICKETS);
            stackPane.getChildren().setAll(ticketScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro", "Não foi possível carregar a tela de ingressos.");
        }
    }

    public void handleLogoutClick() {
        stageController.closeStage();
    }

    public void handleSearchClick() throws EventNotFoundException {
        String searchText = searchField.getText();
        System.out.println("Buscando eventos com o nome: " + searchText);
        eventController.getEventByName(searchText);
    }
}
