package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.exception.ticket.*;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.exception.user.UserUpdateException;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.controllers.TicketController;
import com.lucas.ticketsalesmanager.controllers.UserController;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.lucas.ticketsalesmanager.Main.stageController;
import static javafx.scene.control.Alert.AlertType.ERROR;

public class ProfileController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TableView<Ticket> tableTickets;
    @FXML
    private TableColumn<Ticket, String> columnEvent;
    @FXML
    private TableColumn<Ticket, String> columnDate;
    @FXML
    private TableColumn<Ticket, String> columnSeat;
    @FXML
    private TableColumn<Ticket, String> columnPrice;
    @FXML
    private TableColumn<Ticket, String> columnStatus;

    @FXML
    private Button btnCancelTicket;
    @FXML
    private Button btnReactiveTicket;

    private ObservableList<Ticket> ticketList;
    private TicketController ticketController;
    private UserController userController;
    private ScreensController screensController;
    private User user;

    @FXML
    private void initialize() {
        ticketController = new TicketController();
        userController = new UserController();
        screensController = new ScreensController();

        configureTableColumns();
        loadUserProfile(LoginController.user);
        setupButtonActions();
    }

    private void configureTableColumns() {
        columnEvent.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEvent().getName())
        );

        columnDate.setCellValueFactory(cellData -> {
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(cellData.getValue().getEvent().getDate());
            return new SimpleStringProperty(formattedDate);
        });

        columnSeat.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSeat())
        );

        columnPrice.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("R$ %.2f", cellData.getValue().getPrice()))
        );

        columnStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isActive() ? "Ativo" : "Cancelado")
        );

        ticketList = FXCollections.observableArrayList();
        tableTickets.setItems(ticketList);
    }

    public void loadUserProfile(User user) {
        fillUserInfo(user);
        refreshTicketList();
    }

    public void fillUserInfo(User user) {
        txtName.setText(user.getName());
        txtCpf.setText(user.getCpf());
        txtEmail.setText(user.getEmail());
        txtLogin.setText(user.getLogin());
        txtPassword.setText(user.getPassword());
    }

    public void handleCancel() {
        fillUserInfo(user);
        setEditableTextField(false);
    }

    public void handleSave() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String login = txtLogin.getText();

        try {
            userController.updateUser(user, "name", name);
            userController.updateUser(user, "email", email);
            userController.updateUser(user, "login", login);
            stageController.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Informações atualizadas com sucesso.");
        } catch (UserNotFoundException | UserUpdateException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível atualizar as informações do usuário.");
        } catch (Exception e) {
            stageController.showAlert(ERROR, "Erro", "Ocorreu um erro inesperado.");
        }

        setEditableTextField(false);
    }

    public void handleEdit() {
        setEditableTextField(true);
    }

    public void setEditableTextField(boolean bool) {
        txtName.setEditable(bool);
        txtEmail.setEditable(bool);
        txtLogin.setEditable(bool);
    }

    public void handleCancelTicket() {
        Ticket selectedTicket = tableTickets.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(Alert.AlertType.WARNING, "Aviso", "Nenhum ticket selecionado.");
            return;
        }

        try {
            ticketController.cancelTicket(user, selectedTicket);
            stageController.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Ticket cancelado com sucesso.");
            refreshTicketList();
        } catch (TicketNotCancelableException | TicketAlreadyCanceledException | TicketNotFoundException e) {
            stageController.showAlert(ERROR, "Erro", e.getMessage());
        } catch (Exception e) {
            stageController.showAlert(ERROR, "Erro", "Ocorreu um erro inesperado.");
        }
    }

    public void handleReactiveTicket() {
        Ticket selectedTicket = tableTickets.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(Alert.AlertType.WARNING, "Aviso", "Nenhum ticket selecionado.");
            return;
        }

        try {
            ticketController.reactivateTicket(user, selectedTicket);
            stageController.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Ticket reativado com sucesso.");
            refreshTicketList();
        } catch (TicketAlreadyReactivatedException | TicketNotFoundException e) {
            stageController.showAlert(ERROR, "Erro", e.getMessage());
        } catch (Exception e) {
            stageController.showAlert(ERROR, "Erro", "Ocorreu um erro inesperado.");
        }
    }

    private void refreshTicketList() {
        try {
            List<Ticket> tickets = ticketController.listPurchasedTickets(LoginController.user);
            ticketList.setAll(tickets);
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(ERROR, "Erro", "Erro ao atualizar a lista de tickets.");
        }
    }

    private void setupButtonActions() {
        btnCancelTicket.setOnAction(event -> {
            try {
                handleCancelTicket();
            } catch (Exception e) {
                stageController.showAlert(Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        });

        btnReactiveTicket.setOnAction(event -> {
            try {
                handleReactiveTicket();
            } catch (Exception e) {
                stageController.showAlert(Alert.AlertType.ERROR, "Erro", e.getMessage());
            }
        });
    }
}

