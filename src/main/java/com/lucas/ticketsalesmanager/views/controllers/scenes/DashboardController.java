package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.exception.event.EventNotFoundException;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.*;

public class DashboardController {

    public TextField searchField;
    public Button btnTickets;
    public Button btnEvents;
    public Button btnProfile;
    public Button btnLogout;
    public Button btnSearch;
    public StackPane stackPane;
    public HBox hboxRoot;
    private StageController stageController;
    private ScreensController screensController;
    private EventController eventController;

    @FXML
    private void initialize() throws IOException {
        stageController = Main.stageController;
        screensController = new ScreensController();
        eventController = new EventController();

        Parent eventList = screensController.loadScreen(EVENT_LIST);
        stackPane.getChildren().setAll(eventList);

        btnEvents.setOnAction(event -> {
            try {
                handleEventsClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
        btnProfile.setOnAction(event -> {
            try {
                handleProfileClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
        btnLogout.setOnAction(event -> {
            try {
                handleLogoutClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
        btnSearch.setOnAction(event -> {
            try {
                handleEventsClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });

        btnTickets.setOnAction(event -> {
            try {
                handleTicketsClick();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void setScreensController(ScreensController screensController) {
        this.screensController = screensController;
    }

    public void handleProfileClick() {
        try {
            Parent profileScreen = screensController.loadScreen(PROFILE);
            stackPane.getChildren().setAll(profileScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Não foi possível carregar a tela de eventos.");
        }
    }

    public void handleEventsClick() {
        try {
            Parent eventDetailScreen = screensController.loadScreen(EVENT_DETAILS);
            stackPane.getChildren().setAll(eventDetailScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Não foi possível carregar a tela de eventos.");
        }
    }


    public void handleTicketsClick() {
        try {
            Parent ticketScreen = screensController.loadScreen(TICKETS);
            stackPane.getChildren().setAll(ticketScreen);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Não foi possível carregar a tela de ingressos.");
        }
    }


    public void handleLogoutClick() {
        System.out.println("Saindo do sistema...");
        stageController.closeStage();
    }

    public void handleSearchClick() throws EventNotFoundException {
        String searchText = searchField.getText();
        System.out.println("Buscando eventos com o nome: " + searchText);
        eventController.getEventByName(searchText);

    }
}
