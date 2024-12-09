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

import com.google.gson.reflect.TypeToken;
import com.lucas.ticketsalesmanager.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The UserDAO class provides data access methods for managing user data.
 * It handles operations such as adding, retrieving, updating, and removing users
 * from a JSON file.
 */
public class UserDAO {
    private static final String FILE_PATH = "users.json";
    private static final Type userListType = new TypeToken<List<User>>(){}.getType();
    private final DataAccessObject<User> userDao;

    /**
     * Constructs a UserDAO instance, initializing the data access object
     * with the specified file path and type for user lists.
     */
    public UserDAO() {
        this.userDao = new DataAccessObject<>(FILE_PATH, userListType);
    }

    /**
     * Adds a new user to the user list.
     *
     * @param user The user to be added.
     */
    public void addUser(User user) {
        List<User> users = userDao.readData();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        userDao.writeData(users);
    }

    /**
     * Retrieves all users from the user list.
     *
     * @return A list of all users, or an empty list if no users are found.
     */
    public List<User> getAllUsers() {
        List<User> users = userDao.readData();
        return users != null ? users : new ArrayList<>();
    }

    /**
     * Finds a user by their login identifier.
     *
     * @param login The login of the user to find.
     * @return The User object if found, or null if not found.
     */
    public User findUserByLogin(String login) {
        List<User> users = userDao.readData();
        if (users != null) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing user's information.
     *
     * @param updatedUser The user whose information is to be updated.
     * @param infoToUpdate The type of information to update (e.g., login, password).
     * @param newInfo The new value for the specified information.
     * @throws IllegalArgumentException if the user is not found or the infoToUpdate is invalid.
     */
    public void updateUser(User updatedUser, String infoToUpdate, String newInfo) {
        List<User> users = userDao.readData();
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getLogin().equals(updatedUser.getLogin())) {
                    switch (infoToUpdate.toLowerCase()) {
                        case "login":
                            user.setLogin(newInfo);
                            break;
                        case "password":
                            user.setPassword(newInfo);
                            break;
                        case "name":
                            user.setName(newInfo);
                            break;
                        case "cpf":
                            user.setCpf(newInfo);
                            break;
                        case "email":
                            user.setEmail(newInfo);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid information type: " + infoToUpdate);
                    }
                    users.set(i, user);
                    userDao.writeData(users);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("User not found: " + updatedUser.getLogin());
    }

    /**
     * Removes a user from the user list by their login.
     *
     * @param login The login of the user to remove.
     */
    public void removeUser(String login) {
        List<User> users = userDao.readData();
        if (users != null) {
            users.removeIf(user -> user.getLogin().equals(login));
            userDao.writeData(users);
        }
    }
}
