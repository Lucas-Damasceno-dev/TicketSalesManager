package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Languages;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes;
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

    private StageController stageController;
    private ScreensController screensController;

    private User currentUser;
    private List<Event> events;

    @FXML
    private Button btnTickets;

    @FXML
    private ChoiceBox<Languages> choiceLanguage;
    @FXML
    private VBox vboxMenu;
    
    private List<Event> encountredEvents;

    public void initialize() throws IOException {
        stageController = Main.stageController;
        screensController = Main.screensController;
        Main.dashboardController = this;
        currentUser = LoginController.user;

        btnEvents.setVisible(currentUser.isAdmin());
        if(currentUser.isAdmin())
        {
            vboxMenu.getChildren().remove(btnTickets);
        }else{
            vboxMenu.getChildren().remove(btnEvents);
        }
        events = new EventController().listAvailableEvents();
        handleEventsClick();
        configureLangChoice();
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
                Parent eventList = screensController.loadScreen(EVENT_LIST);
                stackPane.getChildren().setAll(eventList);
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

    public void setParentAtStackPane(Parent parent) {
        stackPane.getChildren().setAll(parent);
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
        encountredEvents = new LinkedList();
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
            encountredEvents.clear();
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível carregar a tela de listagem de eventos.");
        }

    }
    
    public List<Event> encontredEvents(){
        return encountredEvents;
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
            updateInterfaceLang();
        });
    }

    private void updateInterfaceLang() {
        btnProfile.setText(Main.languageController.getLabel("profile"));
        btnTickets.setText(Main.languageController.getLabel("tickets"));
        btnEvents.setText(Main.languageController.getLabel("events"));
        searchField.setPromptText(Main.languageController.getLabel("search-events"));
        btnSearch.setText(Main.languageController.getLabel("search"));
        btnLogout.setText(Main.languageController.getLabel("logout"));
    }
}
