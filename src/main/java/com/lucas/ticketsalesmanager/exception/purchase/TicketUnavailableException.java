package com.lucas.ticketsalesmanager.exception.purchase;

public class TicketUnavailableException extends PurchaseException {
    public TicketUnavailableException(String seat) {
        super("O assento " + seat + " já está indisponível.",
                "Falha na compra: o assento " + seat + " já foi reservado.");
    }
}

