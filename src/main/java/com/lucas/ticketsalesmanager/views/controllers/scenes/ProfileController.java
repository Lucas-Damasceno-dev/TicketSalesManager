package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.exception.user.UserUpdateException;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import com.lucas.ticketsalesmanager.controllers.UserController;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfileController {


    public TextField txtName;
    public TextField txtCpf;
    public TextField txtEmail;
    public TextField txtLogin;
    public PasswordField txtPassword;
    public Button btnEdit;
    public Button btnSave;
    public Button btnCancel;
    public TableView tableTickets;

    private StageController stageController;
    private ScreensController screensController;

    private UserController userController;

    @FXML
    private void initialize() {
        stageController = Main.stageController;
        screensController = new ScreensController();
        fillUserInfo(LoginController.user);
        userController = new UserController();
        /*tableTickets = LoginController.user.getTickets();*/
    }

    public void  fillUserInfo(User user) {
        txtName.setText(user.getName());
        txtCpf.setText(user.getCpf());
        txtEmail.setText(user.getEmail());
        txtLogin.setText(user.getLogin());
        txtPassword.setText(user.getPassword());
    }

    public void handleCancel() {
        fillUserInfo(LoginController.user);
        setEditableTextField(false);
    }

    public void handleSave() throws UserNotFoundException, UserUpdateException {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String login = txtLogin.getText();

        try {
                userController.updateUser(LoginController.user, "name", name);
                userController.updateUser(LoginController.user, "email", email);
                userController.updateUser(LoginController.user, "login", login);
            } catch(Exception e){
                e.printStackTrace();
                    stageController.showAlert(ERROR, "ERRO",
                            "Não foi possivel atualizar as informações do usuario");
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

