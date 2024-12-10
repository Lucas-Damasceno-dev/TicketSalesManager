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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controller for handling the user registration process in the ticket sales system.
 * This controller manages user input for creating a new account, validates the input,
 * and interacts with the user controller to register a new user in the system.
 * It also manages language localization for interface labels.
 *
 * <ul>
 *     <li>Handles user input for name, CPF, username, email, and password.</li>
 *     <li>Validates input to ensure all fields are filled and passwords match.</li>
 *     <li>Registers a new user and redirects to the login screen if successful.</li>
 *     <li>Handles navigation to the login screen if the user already has an account.</li>
 *     <li>Updates interface elements based on the selected language.</li>
 * </ul>
 */
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
    @FXML
    private Label lblAccountRegistration;
    @FXML
    private Label lblName;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblPassConfirmation;
    @FXML
    private Label lblAlreadyHaveAccount;
    @FXML
    private Hyperlink lblLoginHere;

    /**
     * Initializes the SignUpController by setting up necessary controllers and
     * updating interface elements based on the selected language.
     */
    public void initialize() {
        screensController = Main.screensController;
        userController = new UserController();
        updateInterfaceLang();
    }

    /**
     * Handles the sign-up process by validating the user input and registering
     * a new user. If successful, navigates to the login screen.
     *
     * @throws IOException if there's an error loading the login screen.
     */
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
            stageController.showAlert(ERROR, "Error", "Please fill in all fields.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            stageController.showAlert(ERROR, "Error", "Passwords do not match.");
            return;
        }

        boolean isAdmin = username.equals("admin") && password.equals("admin");

        try {
            userController.registerUser(username, password, name, cpf, email, isAdmin);
            stageController.changeStageContent(screensController.loadScreen(LOGIN));
        } catch (UserAlreadyExistsException | UserDAOException ex) {
            stageController.showAlert(ERROR, "Error", ex.getMessage());
        } catch (IOException ex) {
            stageController.showAlert(ERROR, "Error", "Could not load the login screen.");
        }
    }

    /**
     * Navigates the user back to the login screen.
     *
     * @throws IOException if there's an error loading the login screen.
     */
    @FXML
    public void onBackToLogin() {
        try {
            stageController.changeStageContent(screensController.loadScreen(LOGIN));
        } catch (IOException e) {
            stageController.showAlert(ERROR, "Error", "Could not load the login screen.");
        }
    }

    /**
     * Handles key presses in the confirm password field. If the Enter key is pressed,
     * it triggers the sign-up process.
     *
     * @param event The key event.
     */
    @FXML
    private void confirmPasswordOnKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            this.onSignUp();
        }
    }

    /**
     * Updates the interface language based on the selected language in the system.
     * This method sets text for labels, prompt text for input fields, and button text.
     */
    private void updateInterfaceLang() {
        lblAccountRegistration.setText(Main.languageController.getLabel("account-registration"));
        usernameField.setPromptText(Main.languageController.getLabel("enter-username"));
        passwordField.setPromptText(Main.languageController.getLabel("enter-password"));
        btnSignUp.setText(Main.languageController.getLabel("register"));
        lblName.setText(Main.languageController.getLabel("name"));
        nameField.setPromptText(Main.languageController.getLabel("enter-name"));
        CPFField.setPromptText(Main.languageController.getLabel("enter-cpf"));
        emailField.setPromptText(Main.languageController.getLabel("enter-email"));
        lblPassword.setText(Main.languageController.getLabel("password"));
        lblPassConfirmation.setText(Main.languageController.getLabel("password-confirmation"));
        confirmPasswordField.setPromptText(Main.languageController.getLabel("password-confirmation"));
        lblAlreadyHaveAccount.setText(Main.languageController.getLabel("already-have-account"));
        lblLoginHere.setText(Main.languageController.getLabel("login-here"));
    }
}
