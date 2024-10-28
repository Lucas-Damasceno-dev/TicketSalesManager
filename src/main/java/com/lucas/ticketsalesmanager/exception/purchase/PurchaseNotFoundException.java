package com.lucas.ticketsalesmanager.exception.purchase;

public class PurchaseNotFoundException extends PurchaseException {
    public PurchaseNotFoundException(String purchaseId) {
        super("Compra não encontrada.",
                "Compra com o ID " + purchaseId + " não foi encontrada.");
    }
}

