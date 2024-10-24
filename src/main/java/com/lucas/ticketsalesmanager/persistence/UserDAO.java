package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ticketsalesmanager.models.User;


public class UserDAO {
    public static void main(String[] args) {
        // Defines the file path where users will be saved
        String filePath = "users.json";

        // Defines the type of user list
        Type userListType = new TypeToken<List<User>>(){}.getType();

        // Creates an instance of the DataAccessObject for users
        DataAccessObject<User> userDao = new DataAccessObject<>(filePath, userListType);

        // Create some users to test
        List<User> users = new ArrayList<>();
        users.add(new User("johndoe", "password123", "John Doe", "12345678901", "john@example.com", false));
        users.add(new User("janedoe", "password456", "Jane Doe", "10987654321", "jane@example.com", true));

        // Save users to file
        userDao.writeData(users);
        System.out.println("Successfully saved users!");

        // Read users from file
        List<User> loadedUsers = userDao.readData();
        if (loadedUsers != null) {
            System.out.println("Uploaded users:");
            for (User user : loadedUsers) {
                System.out.println(user);
            }
        } else {
            System.out.println("No users found or error loading.");
        }
    }
}
