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
import static javafx.scene.control.Alert.AlertType.ERROR;


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
    private void initialize() {
        stageController = Main.stageController;
        screensController = new ScreensController();
        userController = new UserController();
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void setScreensController(ScreensController screensController) {
        this.screensController = screensController;
    }

    @FXML
    private void handleLogin() throws UserNotFoundException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty() ) {
            stageController.showAlert(ERROR,
                    "Erro", "Preencha todos os campos.");
            return;
        }
        try {
            user = userController.findUserByLogin(usernameField.getText());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            stageController.showAlert(ERROR, "Erro", "Usuário não encontrado.");
        }
        if (user.getPassword().equals(password)) {
            try {
                stageController.changeStageContent(screensController.loadScreen(DASHBOARD));
            } catch (Exception e) {
                e.printStackTrace();
                stageController.showAlert(ERROR,
                        "Erro", "Não foi possível carregar o Dashboard.");
            }
        }
    }

    public void onSignUp() {
        try {
            stageController.changeStageContent(screensController.loadScreen(SIGN_UP));
        } catch (Exception e) {
            e.printStackTrace();
            stageController.showAlert(ERROR,
                    "Erro", "Não foi possível carregar a tela de cadastro.");
        }
    }
}
