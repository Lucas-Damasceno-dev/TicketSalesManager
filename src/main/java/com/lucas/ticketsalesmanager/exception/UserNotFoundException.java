package com.lucas.ticketsalesmanager.exception;

public class UserNotFoundException extends TicketSalesManagerException {
    public UserNotFoundException(String userId) {
        super("User not found.", "User with ID: " + userId + " NOT FOUND IN THE SYSTEM.");
    }
}
