package com.lucas.ticketsalesmanager.exception.user;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class UserNotAuthorizedException extends TicketSalesManagerException {
    public UserNotAuthorizedException(String userLogin) {
        super("Only administrators can register events.",
                "The user " + userLogin + " is not an admin!");
    }
}
