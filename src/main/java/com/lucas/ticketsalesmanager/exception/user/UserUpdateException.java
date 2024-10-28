package com.lucas.ticketsalesmanager.exception.user;

public class UserUpdateException extends Exception {
    private final String infoToUpdate;

    public UserUpdateException(String login, String infoToUpdate, String logMessage) {
        super(logMessage);
        this.infoToUpdate = infoToUpdate;
    }

    public String getInfoToUpdate() {
        return infoToUpdate;
    }
}
