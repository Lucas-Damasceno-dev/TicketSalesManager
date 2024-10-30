package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DataAccessObject<T> {
    private final String filePath;
    private final Gson gson;
    private final Type type;

    public DataAccessObject(String filePath, Type type) {
        this.filePath = filePath;
        this.gson = new Gson();
        this.type = type;
    }

    public List<T> readData() {
        try (FileReader reader = new FileReader(filePath)) {
            List<T> data = gson.fromJson(reader, type);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public boolean writeData(List<T> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
