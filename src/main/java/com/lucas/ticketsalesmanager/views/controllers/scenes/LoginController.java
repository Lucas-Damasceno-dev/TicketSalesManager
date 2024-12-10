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

import com.lucas.ticketsalesmanager.Main;
import com.lucas.ticketsalesmanager.controllers.LanguageController;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.controllers.UserController;
import com.lucas.ticketsalesmanager.models.Languages;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for handling user login functionality.
 * This controller manages the login screen, user authentication,
 * and navigation to the dashboard or sign-up screen.
 *
 * <ul>
 *     <li>Handles user input for username and password.</li>
 *     <li>Validates credentials by checking against stored user data.</li>
 *     <li>Manages the choice of application language using a dropdown menu.</li>
 *     <li>Transitions to the Dashboard or Sign-up screen based on user actions.</li>
 * </ul>
 */
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
    @FXML
    private ChoiceBox<Languages> choiceLang;
    @FXML
    private Label lblNoAccount;
    @FXML
    private Hyperlink hlRegisterHere;

    /**
     * Initializes the login controller by setting up necessary controllers
     * and configuring the language selection options.
     */
    public void initialize() {
        stageController = Main.stageController;
        screensController = Main.screensController;
        userController = new UserController();
        configureLangChoice();
    }

    /**
     * Handles the login process. Verifies that the username and password
     * fields are filled, authenticates the user, and transitions to the
     * dashboard screen if login is successful.
     *
     * Displays error messages if the fields are empty, the user is not found,
     * or if the password is incorrect.
     */
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

    /**
     * Navigates the user to the sign-up screen for registration.
     * If the screen cannot be loaded, an error message is shown.
     */
    @FXML
    public void onSignUp() {
        try {
            stageController.changeStageContent(screensController.loadScreen(SIGN_UP));
        } catch (IOException e) {
            stageController.showAlert(ERROR,
                    "Erro", "Não foi possível carregar a tela de cadastro.");
        }
    }

    /**
     * Handles the event when the user presses a key on the keyboard.
     * If the Enter key is pressed, it triggers the login process.
     *
     * @param event The key press event.
     */
    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.handleLogin();
        }
    }

    /**
     * Configures the language selection dropdown, initializing it with
     * available languages and setting the current language.
     * Also listens for changes to the selected language and updates
     * the interface accordingly.
     */
    private void configureLangChoice() {
        choiceLang.getItems().addAll(Languages.values());
        Languages current = Main.languageController.getCurretLanguage();
        current = current == null ? Languages.PT_BR : current;
        choiceLang.getSelectionModel().select(current);
        choiceLang.valueProperty().addListener((ov, old, newValue) -> {
            Main.languageController.updateLanguage(newValue);
            updateInterfaceLang();
        });
    }

    /**
     * Updates the interface language based on the selected language in the dropdown.
     * It adjusts the text of the login form fields, button, and labels accordingly.
     */
    private void updateInterfaceLang()
    {
        usernameField.setPromptText(Main.languageController.getLabel("enter-username"));
        passwordField.setPromptText(Main.languageController.getLabel("enter-password"));
        btnLogin.setText(Main.languageController.getLabel("login-button"));
        lblNoAccount.setText(Main.languageController.getLabel("no-account"));
        hlRegisterHere.setText(Main.languageController.getLabel("register-here"));
    }
}
