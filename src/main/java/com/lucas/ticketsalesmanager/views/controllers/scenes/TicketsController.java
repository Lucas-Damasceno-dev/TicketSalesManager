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
    
    
    private void initialize() {
        this.ticketController = new TicketController();
        this.observableTicketList = FXCollections.observableArrayList();

        configureTableColumns();

    }

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
                -> new SimpleStringProperty(cellData.getValue().isActive() ? "Ativo" : "Cancelado")
        );

        ticketTable.setItems(observableTicketList);
    }

    public void loadUserTickets() {
        List<Ticket> tickets = ticketController.listPurchasedTickets(LoginController.user);
        observableTicketList.setAll(tickets);
    }

    public void handleCancelTicket() {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(WARNING, "Aviso", "Nenhum ticket selecionado.");
            return;
        }

        try {
            ticketController.cancelTicket(user, selectedTicket);
            stageController.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Ticket cancelado com sucesso.");
            observableTicketList.setAll(ticketController.listPurchasedTickets(user));
        } catch (TicketNotCancelableException | TicketAlreadyCanceledException | TicketNotFoundException e) {
            Main.stageController.showAlert(ERROR, "Erro", e.getMessage());
        }
    }

    public void handleReactiveTicket() throws InvalidTicketException {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(Alert.AlertType.WARNING, "Aviso", "Nenhum ticket selecionado.");
            return;
        }

        try {
            ticketController.reactivateTicket(user, selectedTicket);
            stageController.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Ticket reativado com sucesso.");
            observableTicketList.setAll(ticketController.listPurchasedTickets(user));
        } catch (TicketAlreadyReactivatedException | TicketNotFoundException e) {
            Main.stageController.showAlert(ERROR, "Erro", e.getMessage());
        } catch (InvalidTicketException e) {
            Main.stageController.showAlert(ERROR, "Erro", "Ocorreu um erro inesperado.");
        }
    }
}
