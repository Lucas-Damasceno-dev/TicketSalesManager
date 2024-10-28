package com.lucas.ticketsalesmanager.exception.user;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class UserNotFoundException extends TicketSalesManagerException {
    public UserNotFoundException(String userLogin) {
        super("User with login '" + userLogin + "' was not found.",
                "User lookup failed for login: " + userLogin);
    }
}

