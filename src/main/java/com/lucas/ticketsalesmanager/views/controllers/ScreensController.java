package com.lucas.ticketsalesmanager.views.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScreensController {

    private final Map<Scenes, Parent> scenessCache = new HashMap<>();
    private Scenes lastScenesLoaded;
    private static final String BASE_PATH =
            System.getProperty("views.base.path", "/com/lucas/ticketsalesmanager/views/");

    public ScreensController() {
        this.lastScenesLoaded = null;
    }

    public boolean isScenesLoaded(Scenes scenes) {
        return lastScenesLoaded == scenes;
    }

    public Parent loadScreen(Scenes scenes) throws IOException {
        return (scenes.isCache()) ? loadScenesInCache(scenes) : loadFXML(scenes);
    }

    private Parent loadScenesInCache(Scenes scenes) throws IOException {
        if (!isScenesLoaded(scenes) && !scenessCache.containsKey(scenes)) {
            Parent scenesContent = loadFXML(scenes);
            scenessCache.put(scenes, scenesContent);
        }
        lastScenesLoaded = scenes;
        return getScenes(scenes);
    }

    public Parent getScenes(Scenes scenes) {
        Parent content = scenessCache.get(scenes);
        if (content == null) {
            throw new IllegalArgumentException("Scenes not found in cache: " + scenes.getFileName());
        }
        return content;
    }

    private Parent loadFXML(Scenes scenes) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BASE_PATH + scenes.getFileName()));
        return loader.load();
    }

    public Object getSceneController(Scenes scenes) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BASE_PATH + scenes.getFileName()));
        loader.load();
        return loader.getController();
    }

    public void clearCache() {
        scenessCache.clear();
    }
}
