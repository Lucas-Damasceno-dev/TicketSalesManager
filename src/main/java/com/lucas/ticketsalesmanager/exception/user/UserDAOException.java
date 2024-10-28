package com.lucas.ticketsalesmanager.exception.user;

public class UserDAOException extends Exception {
    private final String userMessage;

    public UserDAOException(String userMessage, String logMessage) {
        super(logMessage);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
