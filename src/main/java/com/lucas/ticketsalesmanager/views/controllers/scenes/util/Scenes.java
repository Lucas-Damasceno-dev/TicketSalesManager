package com.lucas.ticketsalesmanager.views.controllers.scenes.util;

public enum Scenes {
    LOGIN("login.fxml", false),
    SIGN_UP("signUp.fxml", false),
    DASHBOARD("dashboard.fxml", false),
    EVENT_LIST("eventList.fxml", false),
    EVENT_DETAILS("eventDetail.fxml", false),
    PROFILE("profile.fxml", false),
    PURCHASE("purchase.fxml", false),
    TICKETS("tickets.fxml", false);

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
