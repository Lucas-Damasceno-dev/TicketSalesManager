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
package com.lucas.ticketsalesmanager.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucas.ticketsalesmanager.models.Languages;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code LanguageController} class manages the language translation system for the application.
 * It allows for the dynamic loading and retrieval of labels in different languages.
 * This class supports multiple languages, such as Portuguese (pt-BR) and English (en-US).
 *
 * The translations are stored as key-value pairs loaded from external JSON files.
 */
public class LanguageController {

    /**
     * Map of translation labels for Portuguese (pt-BR).
     */
    private final Map<String, String> ptBr;

    /**
     * Map of translation labels for English (en-US).
     */
    private final Map<String, String> enUs;

    /**
     * The current language used for retrieving labels.
     */
    private Languages currentLanguage;

    /**
     * Constructs a {@code LanguageController} and initializes the translation maps for supported languages.
     *
     * @param currentLanguage The initial language to be used by the application.
     */
    public LanguageController(Languages currentLanguage) {
        this.currentLanguage = currentLanguage;
        this.ptBr = new HashMap<>();
        this.enUs = new HashMap<>();
        loadTranslations(Languages.PT_BR, ptBr);
        loadTranslations(Languages.EN_US, enUs);
    }

    /**
     * Loads the translation file for a given language and populates the corresponding map of labels.
     *
     * @param language The language to load translations for (e.g., PT_BR, EN_US).
     * @param labels The map where the loaded key-value translations will be stored.
     */
    private void loadTranslations(Languages language, Map<String, String> labels) {
        URL filePath = getClass().getResource(language.getFileName());

        try (FileReader reader = new FileReader(filePath.getFile(), Charset.forName("UTF-8"))) {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> loadedLabels = gson.fromJson(reader, mapType);

            if (loadedLabels != null) {
                labels.putAll(loadedLabels);
            }

        } catch (IOException ex) {
            Logger.getLogger(LanguageController.class.getName()).log(Level.SEVERE,
                    "Error loading the translation file for language: " + language.name(), ex);
        }
    }

    /**
     * Retrieves a translation label for a given identifier.
     *
     * @param id The identifier for the label (key) to be retrieved.
     * @return The translated label corresponding to the given identifier.
     *         If the identifier is not found, it returns the identifier itself as a fallback.
     */
    public String getLabel(String id) {
        Map<String, String> labels = enUs;
        if (currentLanguage == Languages.PT_BR) {
            labels = ptBr;
        }
        return labels.getOrDefault(id, id);
    }

    /**
     * Updates the current language used for retrieving labels.
     *
     * @param lang The new language to be set as the current language.
     */
    public void updateLanguage(Languages lang) {
        this.currentLanguage = lang;
    }

    /**
     * Gets the current language being used for label retrieval.
     *
     * @return The current language.
     */
    public Languages getCurretLanguage() {
        return this.currentLanguage;
    }
}
