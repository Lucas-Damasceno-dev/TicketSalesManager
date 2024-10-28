package com.lucas.ticketsalesmanager.exception.purchase;

public class InsufficientFundsException extends PurchaseException {
    public InsufficientFundsException() {
        super("Fundos insuficientes para completar a compra.",
                "Tentativa de compra com saldo insuficiente.");
    }
}

