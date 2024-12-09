package com.lucas.ticketsalesmanager.views.controllers.scenes;


import com.lucas.ticketsalesmanager.Main;
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
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


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
    private Button btnSignUp;

    public void initialize() {
        screensController = Main.screensController;
        userController = new UserController();
    }

    @FXML
    public void onSignUp() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String name = nameField.getText();
        String cpf = CPFField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty() || name.isEmpty() || cpf.isEmpty()) {
            stageController.showAlert(ERROR,
                    "Erro", "Preencha todos os campos.");
            return;
        }
        if(!password.equals(confirmPassword))
        {
            stageController.showAlert(ERROR,
                    "Erro", "As senhas digitadas não são iguais");
            return;
        }
        
        boolean isAdmin = username.equals("admin") && password.equals("admin");
            
        try {
            userController.registerUser(username, password, name, cpf, email, isAdmin);
            stageController.changeStageContent(screensController.loadScreen(LOGIN));
        } catch (UserAlreadyExistsException | UserDAOException ex) {
           stageController.showAlert(ERROR, "Erro", ex.getMessage());
        } catch (IOException ex) {
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Não foi possível carregar a tela de login.");
        }
    }

    @FXML
    public void onBackToLogin() {
        try {
            stageController.changeStageContent(screensController.loadScreen(LOGIN));
        } catch (IOException e) {
            stageController.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro", "Não foi possível carregar a tela de login.");
        }
    }

    @FXML
    private void confirmPasswordOnKeyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            this.onSignUp();
        }
    }
}
