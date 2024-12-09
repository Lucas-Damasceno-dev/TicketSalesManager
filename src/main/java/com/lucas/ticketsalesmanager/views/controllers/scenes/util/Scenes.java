package com.lucas.ticketsalesmanager.views.controllers.scenes.util;

public enum Scenes {
    LOGIN("login.fxml", false),
    SIGN_UP("signUp.fxml", true),
    DASHBOARD("dashboard.fxml", true),
    EVENT_LIST("eventList.fxml", true),
    EVENT_DETAILS("eventDetail.fxml", true),
    PROFILE("profile.fxml", true),
    PURCHASE("purchase.fxml", true),
    TICKETS("tickets.fxml", true);

    private final String fileName;
    private final boolean cache;

    Scenes(String fileName, boolean cache) {
        this.fileName = fileName;
        this.cache = cache;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isCache() {
        return cache;
    }
}
