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
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 * Controller for managing the user's profile.
 * This controller allows users to view and edit their personal information,
 * as well as manage their ticket status (cancelling or reactivating tickets).
 *
 * <ul>
 *     <li>Displays and allows modification of user profile information (name, CPF, email, login, and password).</li>
 *     <li>Handles ticket management by allowing users to cancel or reactivate tickets.</li>
 *     <li>Handles actions like saving and editing profile details, and cancelling or reactivating tickets from a table view.</li>
 * </ul>
 */
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
    private Button btnEdit;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;


    /**
     * Initializes the profile controller by setting up necessary controllers,
     * configuring the ticket table columns, and loading the user's profile data.
     */
    public void initialize() {
        ticketController = new TicketController();
        userController = new UserController();
        screensController = new ScreensController();
        user = LoginController.user;
        configureTableColumns();
        loadUserProfile(user);
        setupButtonActions();
    }

    /**
     * Configures the columns in the ticket table to display relevant ticket data.
     * This includes event name, date, seat, price, and status.
     */
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

    /**
     * Loads the user's profile information and populates the profile fields and ticket list.
     *
     * @param user The user whose profile information is to be loaded.
     */
    public void loadUserProfile(User user) {
        fillUserInfo(user);
        refreshTicketList();
    }

    /**
     * Fills the profile fields with the provided user's information.
     *
     * @param user The user whose information is to be filled in the fields.
     */
    public void fillUserInfo(User user) {
        txtName.setText(user.getName());
        txtCpf.setText(user.getCpf());
        txtEmail.setText(user.getEmail());
        txtLogin.setText(user.getLogin());
        txtPassword.setText(user.getPassword());
    }

    /**
     * Handles the action when the user cancels the profile editing.
     * Resets the fields to the original user data and makes the fields non-editable.
     */
    @FXML
    public void handleCancel() {
        fillUserInfo(user);
        setEditableTextField(false);
    }

    /**
     * Handles the action when the user saves the changes made to their profile.
     * Updates the user profile with the new data and displays a success or error message.
     */
    @FXML
    public void handleSave() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String login = txtLogin.getText();

        try {
            userController.updateUser(user, "name", name);
            userController.updateUser(user, "email", email);
            userController.updateUser(user, "login", login);
            stageController.showAlert(INFORMATION, "Sucesso", "Informações atualizadas com sucesso.");
        } catch (UserNotFoundException | UserUpdateException e) {
            stageController.showAlert(ERROR, "Erro", "Não foi possível atualizar as informações do usuário.");
        }

        setEditableTextField(false);
    }

    /**
     * Handles the action when the user clicks the "Edit" button.
     * Makes the profile fields editable for the user to modify.
     */
    @FXML
    public void handleEdit() {
        setEditableTextField(true);
    }

    /**
     * Sets whether the profile fields are editable or not.
     *
     * @param editable Boolean value indicating whether the fields should be editable.
     */
    public void setEditableTextField(boolean editable) {
        txtName.setEditable(editable);
        txtEmail.setEditable(editable);
        txtLogin.setEditable(editable);
    }

    /**
     * Handles the action when the user clicks the "Cancel Ticket" button.
     * Cancels the selected ticket and displays a success or error message.
     */
    public void handleCancelTicket() {
        Ticket selectedTicket = tableTickets.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(WARNING, "Aviso", "Nenhum ticket selecionado.");
            return;
        }

        try {
            ticketController.cancelTicket(user, selectedTicket);
            stageController.showAlert(INFORMATION, "Sucesso", "Ticket cancelado com sucesso.");
            refreshTicketList();
        } catch (TicketNotCancelableException | TicketAlreadyCanceledException | TicketNotFoundException e) {
            stageController.showAlert(ERROR, "Erro", e.getMessage());
        }
    }

    /**
     * Handles the action when the user clicks the "Reactive Ticket" button.
     * Reactivates the selected ticket and displays a success or error message.
     */
    public void handleReactiveTicket() {
        Ticket selectedTicket = tableTickets.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            stageController.showAlert(WARNING, "Aviso", "Nenhum ticket selecionado.");
            return;
        }

        try {
            ticketController.reactivateTicket(user, selectedTicket);
            stageController.showAlert(INFORMATION, "Sucesso", "Ticket reativado com sucesso.");
            refreshTicketList();
        } catch (TicketAlreadyReactivatedException | InvalidTicketException | TicketNotFoundException e) {
            stageController.showAlert(ERROR, "Erro", e.getMessage());
        }
    }

    /**
     * Refreshes the list of tickets by fetching the most up-to-date list of purchased tickets.
     * Displays an error message if the ticket list cannot be updated.
     */
    private void refreshTicketList() {
        try {
            List<Ticket> tickets = ticketController.listPurchasedTickets(LoginController.user);
            ticketList.setAll(tickets);
        } catch (Exception e) {
            stageController.showAlert(ERROR, "Erro", "Erro ao atualizar a lista de tickets.");
        }
    }

    /**
     * Sets up the button actions, including handling ticket cancellation and reactivation.
     */
    private void setupButtonActions() {
        btnCancelTicket.setOnAction(event -> {
            try {
                handleCancelTicket();
            } catch (Exception e) {
                stageController.showAlert(ERROR, "Erro", e.getMessage());
            }
        });

        btnReactiveTicket.setOnAction(event -> {
            try {
                handleReactiveTicket();
            } catch (Exception e) {
                stageController.showAlert(ERROR, "Erro", e.getMessage());
            }
        });
    }
}

