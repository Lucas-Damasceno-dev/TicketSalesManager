package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.EVENT_LIST;

public class EventDetailController {


    public ImageView eventImage;
    public Label eventName;
    public Label eventDate;
    public Label eventStatus;
    public Label eventDescription;
    public ListView availableSeatsList;
    public Button btnReserveSeat;
    public ListView feedbackList;
    public Button btnAddFeedback;
    public Button btnBack;
    public StackPane stackPane;


    private StageController stageController;
    private ScreensController screensController;

    @FXML
    private void initialize() {
        stageController = Main.stageController;
        screensController = new ScreensController();
        btnBack.setOnAction(event -> {
            try {
                handleOnBack();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
        btnAddFeedback.setOnAction(event -> {
            try {
                handleAddFeedback();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
        btnReserveSeat.setOnAction(event -> {
            try {
                handleReserveSeat();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
    }


    public void handleReserveSeat() {
    }

    public void handleAddFeedback() {

    }

    public void handleOnBack() throws IOException {
        Parent eventList = screensController.loadScreen(EVENT_LIST);
        stackPane.getChildren().setAll(eventList);
    }
}
