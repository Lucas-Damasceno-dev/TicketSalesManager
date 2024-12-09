/***********************************************************************************************
Author: LUCAS DA CONCEIÇÃO DAMASCENO
Curricular Component: EXA 863 - MI Programming - 2024.2 - TP01
Completed on: 10/24/2024
I declare that this code was prepared by me individually and does not contain any
code snippet from another colleague or another author, such as from books and
handouts, and web pages or electronic documents. Any piece of code
by someone other than mine is highlighted with a citation for the author and source
of the code, and I am aware that these excerpts will not be considered for evaluation purposes
************************************************************************************************/

package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The DataAccessObject class provides generic methods for reading and writing
 * data to and from a JSON file. It uses Gson for serialization and deserialization.
 *
 * @param <T> The type of objects this DAO will manage.
 */
public class DataAccessObject<T> {
    private final String filePath;
    private final Gson gson;
    private final Type type;

    /**
     * Constructs a DataAccessObject with the specified file path and type.
     *
     * @param filePath The path to the JSON file.
     * @param type The type of the objects managed by this DAO.
     */
    public DataAccessObject(String filePath, Type type) {
        this.filePath = filePath;
        this.gson = new Gson();
        this.type = type;
    }

    /**
     * Reads data from the JSON file and returns it as a list.
     *
     * @return A list of objects read from the file, or an empty list if the file is empty or an error occurs.
     */
    public List<T> readData() {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("[]");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileReader reader = new FileReader(filePath)) {
            List<T> data = gson.fromJson(reader, type);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Writes a list of objects to the JSON file.
     *
     * @param data The list of objects to be written to the file.
     * @return true if the data was written successfully, false otherwise.
     */
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
