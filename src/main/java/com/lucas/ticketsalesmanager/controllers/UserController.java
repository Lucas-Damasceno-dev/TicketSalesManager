package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.persistence.UserDAO;

import java.util.List;

public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(String login, String password, String name, String cpf, String email, boolean isAdmin) {
        User user = new User(login, password, name, cpf, email, isAdmin);
        userDAO.addUser(user);
        return user;
    }

    public User findUserByLogin(String login) {
        return userDAO.findUserByLogin(login);
    }

    public List<User> listAllUsers() {
        return userDAO.getAllUsers();
    }

    public User updateUser(User updatedUser, String infoToUpdate, String newInfo) {
        userDAO.updateUser(updatedUser, infoToUpdate, newInfo);
        return updatedUser;
    }

    public void removeUser(String login) {
        userDAO.removeUser(login);
    }
}
