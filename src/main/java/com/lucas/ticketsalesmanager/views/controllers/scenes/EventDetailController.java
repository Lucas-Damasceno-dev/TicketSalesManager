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

    public void setEventAndUser(Event event) {
        this.currentUser = LoginController.user;

        this.currentEvent = event;
        this.eventController = new EventController();

        loadEventDetails();
        configureViewBasedOnUser();
    }

    /**
     * Configura a interface de acordo com o tipo de usuário logado (Admin ou
     * Usuário Comum)
     */
    private void configureViewBasedOnUser() {
        if (currentUser.isAdmin()) {
            configureAdminView();
        } else {
            configureUserView();
        }
    }

    /**
     * Configura a interface para o administrador (criação/edição de evento)
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
    
    public Event getReservedEvent(){
        return reservedEvent;
    }
    
    public String getReservedSeat(){
        return selectedSeat;
    }
    
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

    private List<String> buildSeats(Integer qty) {
        List<String> seats = new ArrayList(qty);
        for (int i = 0; i < qty; i++) {
            seats.add("A" + i);
        }
        return seats;
    }

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

    private void loadFeedbacks() {
        List<EventFeedback> feedbacks = currentEvent.getFeedbacks();
        feedbackList.clear();
        for (EventFeedback feedback : feedbacks) {
            String feedbackString = formatFeedback(feedback);
            feedbackList.add(feedbackString);
        }
        feedbackListView.setItems(feedbackList);
    }

    private void loadAvailableSeats() {
        List<String> seats = currentEvent.getAvailableSeats();
        availableSeatsList.setItems(FXCollections.observableArrayList(seats));
    }

    private String formatFeedback(EventFeedback feedback) {
        return String.format("Usuário: %s\nNota: %.1f\nComentário: %s",
                feedback.getUser().getName(), feedback.getRating(), feedback.getComment());
    }

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

    private void clearForm() {
        eventName.clear();
        eventDescription.clear();
        dataPicker.setValue(LocalDate.now());
        ratingSlider.setValue(0);
        commentTextArea.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

    @FXML
    private void saveEvent(ActionEvent event) {
        this.handleSaveEvent();
        this.clearForm();
    }
}
