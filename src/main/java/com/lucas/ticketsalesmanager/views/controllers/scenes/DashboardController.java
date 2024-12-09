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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.*;
import java.util.LinkedList;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DashboardController {

    @FXML
    public TextField searchField;
    @FXML
    public Button btnDashboard;
    @FXML
    public Button btnEvents;
    @FXML
    public Button btnProfile;
    @FXML
    public Button btnLogout;
    @FXML
    public Button btnSearch;
    @FXML
    public StackPane stackPane;
    @FXML
    public HBox hboxRoot;

    @FXML
    public ListView<Event> eventListView;

    private StageController stageController;
    private ScreensController screensController;
    private EventController eventController;
    private User currentUser;
    private List<Event> events;
    @FXML
    private Button btnTickets;
    @FXML
    private ChoiceBox<Languages> choiceLanguage;

    public void initialize() throws IOException {
        stageController = Main.stageController;
        screensController = Main.screensController;
        eventController = new EventController();

        currentUser = LoginController.user;

        btnEvents.setVisible(currentUser.isAdmin());

        loadEventList();
        configureLangChoice();
    }

    private void loadEventList() {
        events = eventController.listEvents();
        handleDashboardClick();
        if (!events.isEmpty()) {
            return;
        }
        Platform.runLater(() -> eventListView.getItems().setAll(events));
        
        eventListView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                Event selectedEvent = eventListView.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    handleEventDetailClick(selectedEvent);
                }
            }
        });
    }

    private void handleEventDetailClick(Event selectedEvent) {
        try {
            FXMLLoader loader = screensController.getLoader(EVENT_DETAILS);
            Parent eventDetailScreen = loader.load();
            EventDetailController controller = loader.getController();
            controller.setEventAndUser(selectedEvent);
            stackPane.getChildren().setAll(eventDetailScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar os detalhes do evento.");
        }
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void setScreensController(ScreensController screensController) {
        this.screensController = screensController;
    }

    @FXML
    public void handleDashboardClick() {
        try {
            Parent dashboardScreen = screensController.loadScreen(EVENT_LIST);
            stackPane.getChildren().setAll(dashboardScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar a dashboard.");
        }
    }

    @FXML
    public void handleProfileClick() {
        try {
            Parent profileScreen = screensController.loadScreen(PROFILE);
            stackPane.getChildren().setAll(profileScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar a tela de perfil.");
        }
    }

    @FXML
    public void handleEventsClick() {
        try {
            if (currentUser.isAdmin()) {
                Parent eventDetailScreen = screensController.loadScreen(EVENT_DETAILS);
                stackPane.getChildren().setAll(eventDetailScreen);
            } else {
                loadEventList();
            }
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar a tela de eventos.");
        }
    }

    @FXML
    public void handleTicketsClick() {
        try {
            Parent ticketScreen = screensController.loadScreen(TICKETS);
            stackPane.getChildren().setAll(ticketScreen);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar a tela de ingressos.");
        }
    }

    @FXML
    public void handleLogoutClick() {
        try {
            LoginController.user = null;
            currentUser = null;
            stageController.changeStageContent(screensController.loadScreen(LOGIN));
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar a tela de ingressos.");
        }
    }

    @FXML
    public void handleSearchClick() {
        String searchText = searchField.getText();
        List<Event> encountredEvents = new LinkedList();
        if (searchText.isBlank()) {
            return;
        }

        for (Event event : events) {
            if (event.getName().contains(searchText)) {
                encountredEvents.add(event);
            }
        }

        if (encountredEvents.isEmpty()) {
            stageController.showAlert(Alert.AlertType.INFORMATION, "Information", "Nenhum evento foi encontrado.");
            return;
        }
        try {
            Parent eventList = screensController.loadScreen(EVENT_LIST);
            stackPane.getChildren().setAll(eventList);
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar a tela de listagem de eventos.");
        }

    }

    @FXML
    private void shearchOnKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.handleSearchClick();
        }
    }

    private void configureLangChoice() {
        this.choiceLanguage.getItems().addAll(Languages.values());
        this.choiceLanguage.getSelectionModel().select(Languages.PT_BR);
        this.choiceLanguage.valueProperty().addListener((ov, old, newValue) -> {
            Main.languageController.updateLanguage(newValue);
        });
    }
}
