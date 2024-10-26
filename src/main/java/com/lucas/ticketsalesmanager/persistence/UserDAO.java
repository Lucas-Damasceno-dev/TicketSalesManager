package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;
import com.lucas.ticketsalesmanager.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String FILE_PATH = "users.json";
    private static final Type userListType = new TypeToken<List<User>>(){}.getType();
    private final DataAccessObject<User> userDao;

    public UserDAO() {
        this.userDao = new DataAccessObject<>(FILE_PATH, userListType);
    }

    // Add a new user
    public void addUser(User user) {
        List<User> users = userDao.readData();
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
        userDao.writeData(users);
    }

    // List all users
    public List<User> getAllUsers() {
        List<User> users = userDao.readData();
        return users != null ? users : new ArrayList<>();
    }

    // Find user by login
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
    // Update an existing user's information
    public void updateUser(User updatedUser, String infoToUpdate, String newInfo) {
        List<User> users = userDao.readData();
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getLogin().equals(updatedUser.getLogin())) {
                    // Check which information needs to be updated
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
                            throw new IllegalArgumentException("Tipo de informação inválido: " + infoToUpdate);
                    }
                    users.set(i, user);
                    userDao.writeData(users);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Usuário não encontrado: " + updatedUser.getLogin());
    }


    // Remove a user
    public void removeUser(String login) {
        List<User> users = userDao.readData();
        if (users != null) {
            users.removeIf(user -> user.getLogin().equals(login));
            userDao.writeData(users);
        }
    }
}
