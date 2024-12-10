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
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.exception.event.EventAlreadyExistsException;
import com.lucas.ticketsalesmanager.exception.event.EventNotFoundException;
import com.lucas.ticketsalesmanager.exception.event.EventUpdateException;
import com.lucas.ticketsalesmanager.exception.event.InvalidEventDateException;
import com.lucas.ticketsalesmanager.exception.event.SeatUnavailableException;
import com.lucas.ticketsalesmanager.exception.user.UserNotAuthorizedException;
import com.lucas.ticketsalesmanager.service.communication.EventFeedback;
import com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes;
import java.io.IOException;
import java.text.ParseException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Controller responsible for managing the event detail screen.
 * This screen has different behavior for administrators and regular users.
 *
 * <ul>
 *     <li>Admins can create and edit events, set available seats, and update event information.</li>
 *     <li>Users can reserve seats, leave feedback, and view event details.</li>
 * </ul>
 *
 * The controller interacts with the EventController to manage event data
 * and updates the user interface accordingly.
 */
public class EventDetailController {

    @FXML
    public TextField eventName;

    @FXML
    public TextArea eventDescription;

    @FXML
    public ListView<String> availableSeatsList;

    @FXML
    private TextField seatCountField;

    @FXML
    private ListView<String> feedbackListView;

    @FXML
    private Slider ratingSlider;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Button btnReserveSeat;

    private Event currentEvent;
    private User currentUser;

    private final ObservableList<String> feedbackList = FXCollections.observableArrayList();

    private EventController eventController;
    @FXML
    private Label lblTitle;
    private VBox hboxCreateEvent;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private CheckBox checkEventStatus;
    @FXML
    private VBox vboxRoot;
    @FXML
    private VBox vboxSeats;
    @FXML
    private VBox vboxFeedbackArea;

    @FXML
    private Button btnSave;
    @FXML
    private Label lblEventValue;
    @FXML
    private TextField fieldEventValue;
    @FXML
    private Label lblQtySeats;
    @FXML
    private TextField fieldQtySeats;

    private Event reservedEvent;
    private String selectedSeat;

    /**
     * Initializes the controller by setting up the user and configuring the view
     * based on the user's role (Admin or Regular User).
     */
    public void initialize() {
        this.currentUser = LoginController.user;
        this.eventController = new EventController();
        if (currentUser.isAdmin()) {
            configureAdminView();
        }
        Main.eventDetailController = this;
        reservedEvent = null;
        selectedSeat = null;
    }

    /**
     * Sets the current event and user information.
     *
     * @param event The event whose details will be displayed.
     */
    public void setEventAndUser(Event event) {
        this.currentUser = LoginController.user;

        this.currentEvent = event;
        this.eventController = new EventController();

        loadEventDetails();
        configureViewBasedOnUser();
    }

    /**
     * Configures the user interface for administrators.
     * Admins have access to event creation and editing options.
     */
    private void configureViewBasedOnUser() {
        if (currentUser.isAdmin()) {
            configureAdminView();
        } else {
            configureUserView();
        }
    }

    /**
     * Configures the user interface for regular users.
     * Users can reserve seats and leave feedback.
     */
    private void configureAdminView() {
        eventName.setEditable(true);
        checkEventStatus.setDisable(false);
        eventDescription.setEditable(true);
        btnSave.setVisible(true);
        dataPicker.setDisable(false);
        fieldEventValue.setEditable(true);
        fieldQtySeats.setEditable(true);
        lblTitle.setText("Criar novo evento");
        vboxRoot.getChildren().remove(vboxFeedbackArea);
        vboxRoot.getChildren().remove(vboxSeats);

        btnReserveSeat.setText("Salvar Evento");
        btnReserveSeat.setOnAction(event -> handleSaveEvent());
    }

    /**
     * Configura a interface para o usuário comum (feedback, reserva de assento)
     */
    private void configureUserView() {
        vboxRoot.getChildren().remove(hboxCreateEvent);
        Date now = new Date();
        if (currentEvent.getDate().after(now)) {
            btnReserveSeat.setVisible(true);
            loadAvailableSeats();
            btnReserveSeat.setOnAction(event -> handleReserveSeat());
            btnReserveSeat.setText("Reservar Assento");
            vboxRoot.getChildren().remove(vboxFeedbackArea);
            return;
        }
        lblTitle.setText("Deixar feedback");
        vboxRoot.getChildren().remove(vboxSeats);
    }

    /**
     * Handles the reservation of a seat.
     * Users can select a seat from the list and confirm the reservation.
     * Admins can set the number of available seats for an event.
     */
    @FXML
    public void handleReserveSeat() {
        try {
            reservedEvent = null;
            selectedSeat = null;
            if (currentUser.isAdmin()) {
                String seatCountText = seatCountField.getText().trim();
                if (seatCountText.isEmpty()) {
                    throw new IllegalArgumentException("A quantidade de assentos não pode ser vazia.");
                }

                int seatCount = Integer.parseInt(seatCountText);
                if (seatCount <= 0) {
                    throw new IllegalArgumentException("A quantidade de assentos deve ser maior que zero.");
                }

                List<String> newAvailableSeats = new ArrayList<>();
                for (int i = 0; i < seatCount; i++) {
                    newAvailableSeats.add("Assento " + (i + 1));
                }
                currentEvent.setAvailableSeats(newAvailableSeats);

                showAlert("Sucesso", "Quantidade de assentos definida com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                selectedSeat = availableSeatsList.getSelectionModel().getSelectedItem();

                if (selectedSeat == null) {
                    throw new IllegalArgumentException("Nenhum assento foi selecionado.");
                }

                reservedEvent = eventController.removeEventSeat(currentEvent.getName(), selectedSeat);
                availableSeatsList.getItems().remove(selectedSeat);

                Parent purch = Main.screensController.loadScreen(Scenes.PURCHASE);
                Main.dashboardController.setParentAtStackPane(purch);
            }
        } catch (EventNotFoundException | EventUpdateException | SeatUnavailableException | IllegalArgumentException e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException ex) {
            Logger.getLogger(EventDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the reserved event after a seat reservation is completed.
     *
     * @return The reserved event.
     */
    public Event getReservedEvent(){
        return reservedEvent;
    }

    /**
     * Gets the reserved seat identifier after a seat reservation is completed.
     *
     * @return The reserved seat identifier.
     */
    public String getReservedSeat(){
        return selectedSeat;
    }

    /**
     * Loads and displays the details of the current event.
     */
    private void loadEventDetails() {
        if (currentEvent != null) {
            LocalDate localDate = currentEvent.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            eventName.setText(currentEvent.getName());
            dataPicker.setValue(localDate);
            checkEventStatus.selectedProperty().set(currentEvent.isActive());
            eventDescription.setText(currentEvent.getDescription());
            fieldQtySeats.setText(String.valueOf(currentEvent.getAvailableSeats().size()));
            fieldEventValue.setText(String.valueOf(currentEvent.getEventValue()));

            if (!currentUser.isAdmin()) {
                loadFeedbacks();
                loadAvailableSeats();
            } else {
                seatCountField.setVisible(true);
                seatCountField.setText(String.valueOf(currentEvent.getAvailableSeats().size()));
            }
        } else if (currentUser.isAdmin()) {
            eventName.clear();
            checkEventStatus.selectedProperty().set(false);
            eventDescription.clear();
            seatCountField.clear();
        }
    }

    /**
     * Builds a list of seat identifiers.
     *
     * @param qty The quantity of seats to create.
     * @return A list of seat identifiers.
     */
    private List<String> buildSeats(Integer qty) {
        List<String> seats = new ArrayList(qty);
        for (int i = 0; i < qty; i++) {
            seats.add("A" + i);
        }
        return seats;
    }

    /**
     * Handles the action of saving a new event or updating an existing one.
     * Admins can create a new event or update the details of an existing event.
     */
    public void handleSaveEvent() {
        try {
            String name = eventName.getText().trim();
            String dateString = dataPicker.getValue().toString();
            String description = eventDescription.getText().trim();
            Integer qtySeats = Integer.valueOf(fieldQtySeats.getText());
            Float eventValue = Float.valueOf(fieldEventValue.getText());

            if (name.isEmpty() || dateString.isEmpty() || description.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);

            if (currentEvent == null) {
                currentEvent = new Event(name, description, date);
                currentEvent.setEventValue(eventValue);
                currentEvent.setAvailableSeats(buildSeats(qtySeats));
                currentEvent.setActive(checkEventStatus.isSelected());
                eventController.registerEvent(currentUser, currentEvent);
            } else {
                currentEvent.setName(name);
                currentEvent.setDate(date);
                currentEvent.setEventValue(eventValue);
                currentEvent.setActive(checkEventStatus.isSelected());
                currentEvent.setDescription(description);
                eventController.updateEvent(currentEvent);
            }
            showAlert("Sucesso", "Evento salvo com sucesso!", Alert.AlertType.INFORMATION);
        } catch (EventAlreadyExistsException | EventNotFoundException | EventUpdateException | InvalidEventDateException | UserNotAuthorizedException | IllegalArgumentException | ParseException e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
        currentEvent = null;
    }

    /**
     * Loads and displays the feedback left by users for the current event.
     */
    private void loadFeedbacks() {
        List<EventFeedback> feedbacks = currentEvent.getFeedbacks();
        feedbackList.clear();
        for (EventFeedback feedback : feedbacks) {
            String feedbackString = formatFeedback(feedback);
            feedbackList.add(feedbackString);
        }
        feedbackListView.setItems(feedbackList);
    }

    /**
     * Loads and displays the available seats for the current event.
     */
    private void loadAvailableSeats() {
        List<String> seats = currentEvent.getAvailableSeats();
        availableSeatsList.setItems(FXCollections.observableArrayList(seats));
    }

    /**
     * Formats the feedback information for display in the list.
     *
     * @param feedback The feedback to format.
     * @return A formatted string representation of the feedback.
     */
    private String formatFeedback(EventFeedback feedback) {
        return String.format("Usuário: %s\nNota: %.1f\nComentário: %s",
                feedback.getUser().getName(), feedback.getRating(), feedback.getComment());
    }

    /**
     * Handles the action of saving user feedback for the current event.
     * Users can rate the event and leave a comment.
     */
    @FXML
    public void handleSaveFeedback() {
        try {
            float rating = (float) ratingSlider.getValue();
            String comment = commentTextArea.getText().trim();

            if (comment.isEmpty()) {
                throw new IllegalArgumentException("O comentário não pode ser vazio.");
            }

            EventFeedback feedback = EventFeedback.evaluateEvent(currentUser, currentEvent, rating, comment);

            currentEvent.getFeedbacks().add(feedback);
            feedbackList.add(formatFeedback(feedback));

            clearForm();
            showAlert("Sucesso", "Feedback salvo com sucesso!", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Clears the input fields of the feedback form.
     */
    private void clearForm() {
        eventName.clear();
        eventDescription.clear();
        dataPicker.setValue(LocalDate.now());
        ratingSlider.setValue(0);
        commentTextArea.clear();
    }

    /**
     * Displays an alert message on the screen.
     *
     * @param title The title of the alert window.
     * @param message The content of the alert message.
     * @param alertType The type of alert (e.g., information, error).
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the action of canceling the feedback form.
     * If the form has been partially filled, it asks for confirmation before clearing it.
     */
    @FXML
    public void handleCancelFeedback() {
        try {
            if (!commentTextArea.getText().trim().isEmpty() || ratingSlider.getValue() > 0) {
                clearForm();
                showAlert("Cancelamento", "O preenchimento do feedback foi cancelado.", Alert.AlertType.INFORMATION);
                return;
            }

            String selectedFeedback = feedbackListView.getSelectionModel().getSelectedItem();

            if (selectedFeedback == null) {
                throw new IllegalArgumentException("Nenhum feedback foi selecionado para exclusão.");
            }

            EventFeedback feedbackToDelete = currentEvent.getFeedbacks().stream()
                    .filter(feedback -> formatFeedback(feedback).equals(selectedFeedback))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Feedback não encontrado."));

            if (!currentUser.isAdmin() && !feedbackToDelete.getUser().equals(currentUser)) {
                throw new SecurityException("Você não tem permissão para excluir este feedback.");
            }

            currentEvent.getFeedbacks().remove(feedbackToDelete);
            feedbackList.remove(selectedFeedback);

            showAlert("Sucesso", "O feedback foi removido com sucesso.", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        } catch (SecurityException e) {
            showAlert("Permissão Negada", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the action of saving the current event and clearing the form fields.
     *
     * @param event The event triggered by the user action.
     */
    @FXML
    private void saveEvent(ActionEvent event) {
        this.handleSaveEvent();
        this.clearForm();
    }
}
