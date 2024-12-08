package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.exception.user.UserUpdateException;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import com.lucas.ticketsalesmanager.controllers.UserController;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.LoginController.user;
import static javafx.scene.control.Alert.AlertType.ERROR;

import com.lucas.ticketsalesmanager.views.controllers.scenes.util.UserTickets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

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
    private TableView<UserTickets> tableTickets;
    @FXML
    private TableColumn<UserTickets, String> columnEvent;
    @FXML
    private TableColumn<UserTickets, String> columnDate;
    @FXML
    private TableColumn<UserTickets, String> columnSeat;

    private ObservableList<UserTickets> ticketList;

    private StageController stageController;
    private ScreensController screensController;
    private UserController userController;

    @FXML
    private void initialize() {
        stageController = Main.stageController;
        screensController = new ScreensController();
        userController = new UserController();

        fillUserInfo(user);

        ticketList = FXCollections.observableArrayList(
                user.getTickets().stream()
                        .map(ticket -> new UserTickets(
                                ticket.getEvent().getName(),
                                ticket.getEvent().getDate(),
                                ticket.getSeat()))
                        .collect(Collectors.toList())
        );

        columnEvent.setCellValueFactory(cellData -> cellData.getValue().eventNameProperty());

        columnDate.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getEventDate();
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
            return new javafx.beans.property.SimpleStringProperty(formattedDate);
        });

        columnSeat.setCellValueFactory(cellData -> cellData.getValue().eventSeatProperty());

        tableTickets.setItems(ticketList);
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
        } catch (UserNotFoundException | UserUpdateException e) {
            e.printStackTrace();
            stageController.showAlert(ERROR, "ERRO", "Não foi possível atualizar as informações do usuário.");
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(ERROR, "ERRO", "Ocorreu um erro inesperado.");
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
}
