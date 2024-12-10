package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.models.Event;
import javafx.fxml.FXML;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmationController {

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventLocationLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    private Stage stage;
    
    private Event event;
    
    public void initialize() {
        btnConfirm.setOnAction(event -> handlePurchaseConfirmation());
        btnCancel.setOnAction(event -> closeWindow());
        
    }

    public void setPurchaseDetails(Event event, String paymentMethod) {
        this.event = event;
        eventNameLabel.setText(event.getName());
        eventDateLabel.setText(event.getDate().toString());
        paymentMethodLabel.setText(paymentMethod);

    }

    public void handlePurchaseConfirmation() {
        Main.stageController.showAlert(INFORMATION, "Sucesso", "Assento reservado com sucesso!");
        closeWindow();
    }

    private void closeWindow() {
        stage.close();
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
}
