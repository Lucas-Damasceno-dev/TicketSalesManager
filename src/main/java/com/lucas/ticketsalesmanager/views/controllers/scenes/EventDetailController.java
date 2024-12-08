package com.lucas.ticketsalesmanager.views.controllers.scenes;

import com.lucas.ticketsalesmanager.exception.event.EventNotFoundException;
import com.lucas.ticketsalesmanager.exception.event.EventUpdateException;
import com.lucas.ticketsalesmanager.exception.event.SeatUnavailableException;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.service.communication.EventFeedback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lucas.ticketsalesmanager.views.controllers.scenes.LoginController.user;

public class EventDetailController {

    public TextField eventName;
    public TextField eventDate;
    public TextField eventStatus;
    public TextArea eventDescription;
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

    public void setEventAndUser(Event event) {
        this.currentEvent = event;
        this.currentUser = LoginController.user;
        this.eventController = new EventController();

        loadEventDetails();
        configureViewBasedOnUser();
    }

    /**
     * Configura a interface de acordo com o tipo de usuário logado (Admin ou Usuário Comum)
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
        eventDate.setEditable(true);
        eventStatus.setEditable(true);
        eventDescription.setEditable(true);

        feedbackListView.setVisible(false);
        ratingSlider.setVisible(false);
        commentTextArea.setVisible(false);
        btnReserveSeat.setText("Salvar Evento");
        btnReserveSeat.setOnAction(event -> handleSaveEvent());
    }

    /**
     * Configura a interface para o usuário comum (feedback, reserva de assento)
     */
    private void configureUserView() {
        eventName.setEditable(false);
        eventDate.setEditable(false);
        eventStatus.setEditable(false);
        eventDescription.setEditable(false);

        feedbackListView.setVisible(true);
        ratingSlider.setVisible(true);
        commentTextArea.setVisible(true);
        btnReserveSeat.setText("Reservar Assento");
        btnReserveSeat.setOnAction(event -> handleReserveSeat());
    }

    @FXML
    public void handleReserveSeat() {
        try {
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
                String selectedSeat = availableSeatsList.getSelectionModel().getSelectedItem();

                if (selectedSeat == null) {
                    throw new IllegalArgumentException("Nenhum assento foi selecionado.");
                }

                eventController.removeEventSeat(currentEvent.getName(), selectedSeat);
                availableSeatsList.getItems().remove(selectedSeat);

                showAlert("Sucesso", "Assento reservado com sucesso!", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadEventDetails() {
        if (currentEvent != null) {
            eventName.setText(currentEvent.getName());
            eventDate.setText("Data: " + currentEvent.getDate().toString());
            eventStatus.setText("Status: " + (currentEvent.isActive() ? "Ativo" : "Inativo"));
            eventDescription.setText(currentEvent.getDescription());

            if (!currentUser.isAdmin()) {
                loadFeedbacks();
                loadAvailableSeats();
            } else {
                seatCountField.setVisible(true);
                seatCountField.setText(String.valueOf(currentEvent.getAvailableSeats().size()));
            }
        } else if (currentUser.isAdmin()) {
            eventName.clear();
            eventDate.clear();
            eventStatus.clear();
            eventDescription.clear();
            seatCountField.clear();
        }
    }

    @FXML
    public void handleSaveEvent() {
        try {
            String name = eventName.getText().trim();
            String dateString = eventDate.getText().trim();
            String status = eventStatus.getText().trim();
            String description = eventDescription.getText().trim();

            if (name.isEmpty() || dateString.isEmpty() || status.isEmpty() || description.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(dateString);

            if (currentEvent == null) {
                currentEvent = new Event(name, description, date);
                eventController.registerEvent(currentUser, currentEvent);
            } else {
                currentEvent.setName(name);
                currentEvent.setDate(date);
                currentEvent.setActive(Boolean.parseBoolean(status));
                currentEvent.setDescription(description);
                eventController.updateEvent(currentEvent);
            }

            showAlert("Sucesso", "Evento salvo com sucesso!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
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
}
