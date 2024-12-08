package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.lucas.ticketsalesmanager.controllers.TicketController;

import static com.lucas.ticketsalesmanager.Main.stageController;

public class TicketsController {
    public TableView ticketTable;
    public TableColumn eventColumn;
    public TableColumn priceColumn;
    public TableColumn seatColumn;
    public TableColumn statusColumn;
    public Label feedbackMessage;
    public Button btnCancelTicket;
    public Button btnReactiveTicket;
    public Button btnUpdateList;

    private TicketController ticketController;
    private User user;

    @FXML
    private void initialize() {
        TicketController ticketController = new TicketController();
        TicketController userController = new TicketController();
        btnCancelTicket.setOnAction(event -> {
            try {
                handleCancelTicket();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
        btnReactiveTicket.setOnAction(event -> {
            try {
                handleCancelTicket();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
        btnUpdateList.setOnAction(event -> {
            try {
                handleCancelTicket();
            } catch (Exception e) {
                stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Erro",
                        e.getMessage());
            }
        });
    }

    public void handleCancelTicket() {
        try {
            /*ticketController.cancelTicket(user, ticket);*/
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Não foi possível carregar a tela de perfil.");
        }
    }

    public void handleReactiveTicket() {
    }

    public void handleUpdateTicket() {
    }
}
