package com.lucas.ticketsalesmanager.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucas.ticketsalesmanager.models.Languages;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LanguageController {

    private final Map<String, String> ptBr;
    private final Map<String, String> enUs;

    private Languages currentLanguage;

    public LanguageController(Languages currentLanguage) {
        this.currentLanguage = currentLanguage;
        this.ptBr = new HashMap<>();
        this.enUs = new HashMap<>();
        loadTranslations(Languages.PT_BR, ptBr);
        loadTranslations(Languages.EN_US, enUs);
    }

    private void loadTranslations(Languages language, Map<String, String> labels) {
        URL filePath = getClass().getResource(language.getFileName());

        try (FileReader reader = new FileReader(filePath.getFile())) {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> loadedLabels = gson.fromJson(reader, mapType);

            if (loadedLabels != null) {
                labels.putAll(loadedLabels);
            }

        } catch (IOException ex) {
            Logger.getLogger(LanguageController.class.getName()).log(Level.SEVERE, "Erro ao carregar o arquivo de tradução", ex);
        }
    }

    public String getLabel(String id) {
        Map<String, String> labels = enUs;
        if (currentLanguage == Languages.PT_BR) {
            labels = ptBr;
        }
        return labels.getOrDefault(id, id);
    }

    public void updateLanguage(Languages lang) {
        this.currentLanguage = lang;
    }
}
