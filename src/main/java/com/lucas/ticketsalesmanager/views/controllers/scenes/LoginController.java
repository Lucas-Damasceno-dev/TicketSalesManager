package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.controllers.UserController;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.views.controllers.StageController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.DASHBOARD;
import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.SIGN_UP;
import java.io.IOException;
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {

    private StageController stageController;
    private ScreensController screensController;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    private UserController userController;

    public static User user;
    @FXML
    private Button btnLogin;

    public void initialize() {
        stageController = Main.stageController;
        screensController = Main.screensController;
        userController = new UserController();
    }

    @FXML
    public void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            stageController.showAlert(ERROR, "Erro", "Preencha todos os campos.");
            return;
        }
        try {
            user = userController.findUserByLogin(usernameField.getText());
        } catch (UserNotFoundException e) {
            stageController.showAlert(ERROR, "Erro", "Usuário não encontrado.");
            return;
        }
        if (!user.login(username, password)) {
            stageController.showAlert(ERROR,
                    "Erro", "Senha inválida");
            return;
        }
        try {
            stageController.changeStageContent(screensController.loadScreen(DASHBOARD));
        } catch (IOException e) {
            stageController.showAlert(ERROR,
                    "Erro", "Não foi possível carregar o Dashboard.");
        }
    }

    @FXML
    public void onSignUp() {
        try {
            stageController.changeStageContent(screensController.loadScreen(SIGN_UP));
        } catch (IOException e) {
            stageController.showAlert(ERROR,
                    "Erro", "Não foi possível carregar a tela de cadastro.");
        }
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER))
        {
            this.handleLogin();
        }
    }
}
