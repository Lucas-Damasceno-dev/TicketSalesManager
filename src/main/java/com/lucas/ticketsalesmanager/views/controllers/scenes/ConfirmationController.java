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
import javafx.fxml.FXML;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The {@code ConfirmationController} class manages the confirmation screen for ticket purchases.
 * It allows users to view the event details and confirm or cancel the purchase.
 *
 * This controller handles the actions for confirmation and cancellation buttons,
 * as well as the display of event and payment details.
 */
public class ConfirmationController {

    /**
     * Label to display the event name.
     */
    @FXML
    private Label eventNameLabel;

    /**
     * Label to display the event date.
     */
    @FXML
    private Label eventDateLabel;

    /**
     * Label to display the event location.
     */
    @FXML
    private Label eventLocationLabel;

    /**
     * Label to display the selected payment method.
     */
    @FXML
    private Label paymentMethodLabel;

    /**
     * Button to cancel the purchase and close the confirmation window.
     */
    @FXML
    private Button btnCancel;

    /**
     * Button to confirm the purchase.
     */
    @FXML
    private Button btnConfirm;

    /**
     * The current {@code Stage} instance where this controller is displayed.
     */
    private Stage stage;

    /**
     * The event associated with the purchase confirmation.
     */
    private Event event;

    /**
     * Initializes the confirmation screen by setting up event listeners for the confirm and cancel buttons.
     * This method is automatically called by JavaFX when the FXML file is loaded.
     */
    public void initialize() {
        btnConfirm.setOnAction(event -> handlePurchaseConfirmation());
        btnCancel.setOnAction(event -> closeWindow());
    }

    /**
     * Sets the purchase details to be displayed on the confirmation screen.
     *
     * @param event The event associated with the purchase.
     * @param paymentMethod The payment method used for the purchase.
     */
    public void setPurchaseDetails(Event event, String paymentMethod) {
        this.event = event;
        eventNameLabel.setText(event.getName());
        eventDateLabel.setText(event.getDate().toString());
        paymentMethodLabel.setText(paymentMethod);
    }

    /**
     * Handles the purchase confirmation action.
     * Displays a success alert and closes the confirmation window.
     */
    public void handlePurchaseConfirmation() {
        Main.stageController.showAlert(INFORMATION, "Success", "Seat reserved successfully!");
        closeWindow();
    }

    /**
     * Closes the current stage (confirmation window).
     */
    private void closeWindow() {
        stage.close();
    }

    /**
     * Sets the stage where this controller is displayed.
     *
     * @param stage The {@code Stage} instance to be associated with this controller.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
