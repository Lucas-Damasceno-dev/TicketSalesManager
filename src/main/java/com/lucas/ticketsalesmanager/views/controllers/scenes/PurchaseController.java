package com.lucas.ticketsalesmanager.views.controllers.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PurchaseController {

    @FXML
    private Label lblTicketDetails;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private void initialize() {
        // Exemplo de inicialização
        lblTicketDetails.setText("Detalhes do Ingresso");
        lblTotalPrice.setText("Preço Total: R$ 100,00");
    }

    @FXML
    private void onConfirmPurchase() {
        // Lógica para confirmar compra
        System.out.println("Compra confirmada!");
    }

    @FXML
    private void onCancelPurchase() {
        // Lógica para cancelar compra
        System.out.println("Compra cancelada.");
    }
}
