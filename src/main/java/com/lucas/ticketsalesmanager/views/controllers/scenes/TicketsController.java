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
import com.lucas.ticketsalesmanager.exception.ticket.*;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.controllers.TicketController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

import static com.lucas.ticketsalesmanager.Main.stageController;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 * Controller responsible for managing the ticket list of a user. It allows the user to view purchased tickets, cancel them,
 * or reactivate canceled tickets. The controller interacts with the TicketController to perform actions related to ticket management.
 * It updates the interface based on the user's actions and displays appropriate feedback messages.
 *
 * <ul>
 *     <li>Displays a list of tickets with columns for event name, price, seat, and status.</li>
 *     <li>Handles user actions for canceling and reactivating tickets.</li>
 *     <li>Updates the displayed ticket list after any action is performed.</li>
 *     <li>Provides feedback messages on the success or failure of ticket operations.</li>
 * </ul>
 */
public class TicketsController {

    @FXML
    private TableView<Ticket> ticketTable;
    @FXML
    private TableColumn<Ticket, String> eventColumn;
    @FXML
    private TableColumn<Ticket, String> priceColumn;
    @FXML
    private TableColumn<Ticket, String> seatColumn;
    @FXML
    private TableColumn<Ticket, String> statusColumn;
    @FXML
    private Label feedbackMessage;
    @FXML
    private Button btnCancelTicket;
    @FXML
    private Button btnReactiveTicket;

    private TicketController ticketController;
    private ObservableList<Ticket> observableTicketList;
    private User user;

    /**
     * Initializes the controller by setting up necessary components, including the TicketController,
     * observable ticket list, and the table columns.
     */
    private void initialize() {
        this.ticketController = new TicketController();
        this.observableTicketList = FXCollections.observableArrayList();
        configureTableColumns();
    }

    /**
     * Configures the columns in the ticket table to display ticket details such as event name, price,
     * seat, and status (active or canceled).
     */
    private void configureTableColumns() {
        eventColumn.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getEvent().getName())
        );

        priceColumn.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().getPrice()))
        );

        seatColumn.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getSeat())
        );

        statusColumn.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Canceled")
        );

        ticketTable.setItems(observableTicketList);
    }

    /**
     * Loads the user's tickets into the table by fetching them through the TicketController.
     */
    public void loadUserTickets() {
        List<Ticket> tickets = ticketController.listPurchasedTickets(LoginController.user);
        observableTicketList.setAll(tickets);
    }

    /**
     * Handles the action to cancel a selected ticket. Displays an alert message depending on the outcome.
     * If the ticket cannot be canceled, an appropriate error message is shown.
     */
    public void handleCancelTicket() {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(WARNING, "Warning", "No ticket selected.");
            return;
        }

        try {
            ticketController.cancelTicket(user, selectedTicket);
            stageController.showAlert(Alert.AlertType.INFORMATION, "Success", "Ticket successfully canceled.");
            observableTicketList.setAll(ticketController.listPurchasedTickets(user));
        } catch (TicketNotCancelableException | TicketAlreadyCanceledException | TicketNotFoundException e) {
            Main.stageController.showAlert(ERROR, "Error", e.getMessage());
        }
    }

    /**
     * Handles the action to reactivate a selected ticket. Displays an alert message depending on the outcome.
     * If the ticket cannot be reactivated, an appropriate error message is shown.
     *
     * @throws InvalidTicketException If there is an unexpected error while reactivating the ticket.
     */
    public void handleReactiveTicket() throws InvalidTicketException {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(Alert.AlertType.WARNING, "Warning", "No ticket selected.");
            return;
        }

        try {
            ticketController.reactivateTicket(user, selectedTicket);
            stageController.showAlert(Alert.AlertType.INFORMATION, "Success", "Ticket successfully reactivated.");
            observableTicketList.setAll(ticketController.listPurchasedTickets(user));
        } catch (TicketAlreadyReactivatedException | TicketNotFoundException e) {
            Main.stageController.showAlert(ERROR, "Error", e.getMessage());
        } catch (InvalidTicketException e) {
            Main.stageController.showAlert(ERROR, "Error", "An unexpected error occurred.");
        }
    }
}

