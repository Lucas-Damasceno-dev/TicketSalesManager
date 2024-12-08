package com.lucas.ticketsalesmanager.views.controllers.scenes;


import com.lucas.ticketsalesmanager.exception.user.UserAlreadyExistsException;
import com.lucas.ticketsalesmanager.exception.user.UserDAOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static com.lucas.ticketsalesmanager.Main.stageController;
import static com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes.LOGIN;
import com.lucas.ticketsalesmanager.views.controllers.ScreensController;
import com.lucas.ticketsalesmanager.controllers.UserController;

import java.io.IOException;


public class SignUpController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField CPFField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    private ScreensController screensController;

    private UserController userController;

    @FXML
    private void initialize() {
        screensController = new ScreensController();
        userController = new UserController();
    }

    @FXML
    private void onSignUp() throws UserAlreadyExistsException, UserDAOException, IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String name = nameField.getText();
        String cpf = CPFField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty() || name.isEmpty() || cpf.isEmpty()) {
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Preencha todos os campos.");
            return;
        }

        userController.registerUser(username, password, name, cpf, email, false);
        stageController.changeStageContent(screensController.loadScreen(LOGIN));
    }

    @FXML
    private void onBackToLogin() {
        try {
            stageController.changeStageContent(screensController.loadScreen(LOGIN));
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Não foi possível carregar a tela de cadastro.");
        }
    }
}
