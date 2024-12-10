package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.PaymentMethod;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PurchaseController {

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private ChoiceBox<String> paymentMethodChoiceBox;

    @FXML
    private Button btnPurchase;

    private Event reservedEvent;
    private String reservedSeat;
    
    public void initialize() {
        for (PaymentMethod value : PaymentMethod.values()) {
            paymentMethodChoiceBox.getItems().add(value.getDisplayName());
        }
        paymentMethodChoiceBox.getSelectionModel().selectFirst();
        btnPurchase.setOnAction(event -> openConfirmationScreen());
        this.reservedEvent = Main.eventDetailController.getReservedEvent();
        this.reservedSeat = Main.eventDetailController.getReservedSeat();
        
        this.setEventDetails(reservedEvent.getName(), reservedEvent.getDate().toString(), reservedEvent.getDescription());
    }

    public void setEventDetails(String eventName, String eventDate, String eventDescription) {
        eventNameLabel.setText(eventName);
        eventDateLabel.setText(eventDate);
        eventDescriptionLabel.setText(eventDescription);
    }

    private void openConfirmationScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lucas/ticketsalesmanager/views/Confirmation.fxml"));
            Parent confirmationRoot = loader.load();

            ConfirmationController controller = loader.getController();
            controller.setPurchaseDetails(
                    reservedEvent,
                    paymentMethodChoiceBox.getValue()
            );

            Stage stage = new Stage();
            stage.setScene(new Scene(confirmationRoot));
            stage.show();
            controller.setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
