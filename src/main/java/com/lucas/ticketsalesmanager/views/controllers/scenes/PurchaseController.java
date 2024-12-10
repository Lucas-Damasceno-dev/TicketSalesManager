/***********************************************************************************************
 Author: LUCAS DA CONCEIÇÃO DAMASCENO
 Curricular Component: EXA 863 - MI Programming - 2024.2 - TP01
 Completed on: 09/12/2024
 I declare that this code was prepared by me individually and does not contain any
 code snippet from another colleague or another author, such as from books and
 handouts, and web pages or electronic documents. Any piece of code
 by someone other than mine is highlighted with a citation for the author and source
 of the code, and I am aware that these excerpts will not be considered for evaluation purposes
 ************************************************************************************************/
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

/**
 * Controller for handling the purchase process in the ticket sales system.
 * This controller manages the display of event details, allows users to select
 * a payment method, and proceeds to the confirmation screen for finalizing
 * the ticket purchase.
 *
 * <ul>
 *     <li>Displays the event name, date, and description.</li>
 *     <li>Allows the user to select a payment method from available options.</li>
 *     <li>Handles the transition to the confirmation screen to review and finalize the purchase.</li>
 * </ul>
 */
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

    /**
     * Initializes the purchase controller by populating the payment methods
     * in the choice box, selecting the first payment method by default,
     * and setting up the event details and purchase button action.
     */
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

    /**
     * Sets the event details on the UI labels.
     *
     * @param eventName The name of the event.
     * @param eventDate The date of the event.
     * @param eventDescription A description of the event.
     */
    public void setEventDetails(String eventName, String eventDate, String eventDescription) {
        eventNameLabel.setText(eventName);
        eventDateLabel.setText(eventDate);
        eventDescriptionLabel.setText(eventDescription);
    }

    /**
     * Opens the confirmation screen to review the purchase details.
     * The method loads the confirmation screen and passes the event and
     * payment method details to the confirmation controller.
     */
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
