package com.lucas.ticketsalesmanager.exception;

public class UserNotFoundException extends TicketSalesManagerException {
    public UserNotFoundException(String userId) {
        super("Usuário não encontrado.", "Usuário com ID: " + userId + " não encontrado no sistema.");
    }
}
