package com.lucas.ticketsalesmanager.exception.user;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class UserAlreadyExistsException extends TicketSalesManagerException {
    public UserAlreadyExistsException(String userLogin) {
        super("User with login '" + userLogin + "' already exists.",
                "Attempted to register a duplicate user with login: " + userLogin);
    }
}
