/***********************************************************************************************
 Author: LUCAS DA CONCEIÇÃO DAMASCENO
 Curricular Component: EXA 863 - MI Programming - 2024.2 - TP01
 Completed on: 09/12/2024
 I declare that this code was prepared by me individually and does not contain any
 code snippet from another colleague or another author, such as from books and
 handouts, and web pages or electronic documents. Any piece of code
 by someone other than mine is highlighted with a citation for the author and source
 of the code, and I am aware that these excerpts will not be considered for evaluation purposes
 ************************************************************************************************/
package com.lucas.ticketsalesmanager.views.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.lucas.ticketsalesmanager.views.controllers.scenes.util.Scenes;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsible for managing and loading different scenes (FXML views) in the application.
 * It handles the caching of previously loaded scenes to optimize performance and ensures scenes are loaded
 * from the correct location when needed.
 * <p>
 * This class supports:
 * <ul>
 *     <li>Loading scenes either from cache or by loading the FXML file if it isn't cached.</li>
 *     <li>Providing the controller associated with a loaded scene.</li>
 *     <li>Clearing the cache of loaded scenes when necessary.</li>
 * </ul>
 * </p>
 */
public class ScreensController {

    private final Map<Scenes, Parent> scenessCache = new HashMap<>();
    private Scenes lastScenesLoaded;
    private static final String BASE_PATH
            = System.getProperty("views.base.path", "/com/lucas/ticketsalesmanager/views/");

    /**
     * Constructor that initializes the controller with no scenes loaded initially.
     */
    public ScreensController() {
        this.lastScenesLoaded = null;
    }

    /**
     * Checks if the specified scene has been loaded recently.
     *
     * @param scenes the scene to check
     * @return true if the scene is the last one loaded, false otherwise
     */
    public boolean isScenesLoaded(Scenes scenes) {
        return lastScenesLoaded == scenes;
    }

    /**
     * Loads a screen, either from the cache if caching is enabled or by loading the FXML file.
     *
     * @param scenes the scene to load
     * @return the loaded scene's root node
     * @throws IOException if there is an error loading the FXML file
     */
    public Parent loadScreen(Scenes scenes) throws IOException {
        return (scenes.isCache()) ? loadScenesInCache(scenes) : loadFXML(scenes);
    }

    /**
     * Loads a scene into the cache if not already cached, and returns it from the cache.
     *
     * @param scenes the scene to load and cache
     * @return the loaded scene's root node
     * @throws IOException if there is an error loading the FXML file
     */
    private Parent loadScenesInCache(Scenes scenes) throws IOException {
        if (!isScenesLoaded(scenes) && !scenessCache.containsKey(scenes)) {
            Parent scenesContent = loadFXML(scenes);
            scenessCache.put(scenes, scenesContent);
        }
        lastScenesLoaded = scenes;
        return getScenes(scenes);
    }

    /**
     * Retrieves a scene from the cache.
     *
     * @param scenes the scene to retrieve from the cache
     * @return the cached scene's root node
     * @throws IllegalArgumentException if the scene is not found in the cache
     */
    public Parent getScenes(Scenes scenes) {
        Parent content = scenessCache.get(scenes);
        if (content == null) {
            throw new IllegalArgumentException("Scenes not found in cache: " + scenes.getFileName());
        }
        return content;
    }

    /**
     * Loads the FXML file for a given scene.
     *
     * @param scenes the scene for which to load the FXML
     * @return the loaded scene's root node
     * @throws IOException if there is an error loading the FXML file
     */
    private Parent loadFXML(Scenes scenes) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BASE_PATH + scenes.getFileName()));
        return loader.load();
    }

    /**
     * Retrieves the controller associated with a given scene.
     *
     * @param scenes the scene for which to retrieve the controller
     * @return the controller of the loaded scene
     * @throws IOException if there is an error loading the FXML file
     */
    public Object getSceneController(Scenes scenes) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BASE_PATH + scenes.getFileName()));
        loader.load();
        return loader.getController();
    }

    /**
     * Retrieves the FXMLLoader used to load a given scene.
     *
     * @param scenes the scene for which to retrieve the FXMLLoader
     * @return the FXMLLoader for the given scene
     */
    public FXMLLoader getLoader(Scenes scenes) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(BASE_PATH + scenes.getFileName()));
        return loader;
    }

    /**
     * Clears the cache of all loaded scenes.
     */
    public void clearCache() {
        scenessCache.clear();
    }
}
