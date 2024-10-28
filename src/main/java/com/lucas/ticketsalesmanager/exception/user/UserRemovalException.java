package com.lucas.ticketsalesmanager.exception.user;

public class UserRemovalException extends Exception {
    public UserRemovalException(String login, String logMessage) {
        super(logMessage);
    }
}


