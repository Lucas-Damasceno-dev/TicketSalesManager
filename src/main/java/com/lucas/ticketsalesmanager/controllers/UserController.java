package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.persistence.UserDAO;
import com.lucas.ticketsalesmanager.exception.user.UserAlreadyExistsException;
import com.lucas.ticketsalesmanager.exception.user.UserDAOException;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.exception.user.UserRemovalException;
import com.lucas.ticketsalesmanager.exception.user.UserUpdateException;

import java.util.List;

public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public User registerUser(String login, String password, String name, String cpf, String email, boolean isAdmin) throws UserAlreadyExistsException, UserDAOException {
        if (userDAO.findUserByLogin(login) != null) {
            throw new UserAlreadyExistsException(login);
        }

        User user = new User(login, password, name, cpf, email, isAdmin);
        try {
            userDAO.addUser(user);
        } catch (Exception e) {
            throw new UserDAOException("Error registering user.", e.getMessage());
        }
        return user;
    }

    public User findUserByLogin(String login) throws UserNotFoundException {
        User user = userDAO.findUserByLogin(login);
        if (user == null) {
            throw new UserNotFoundException(login);
        }
        return user;
    }

    public List<User> listAllUsers() throws UserDAOException {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            throw new UserDAOException("Error listing all users.", e.getMessage());
        }
    }

    public User updateUser(User updatedUser, String infoToUpdate, String newInfo) throws UserUpdateException, UserNotFoundException {
        if (userDAO.findUserByLogin(updatedUser.getLogin()) == null) {
            throw new UserNotFoundException(updatedUser.getLogin());
        }

        try {
            userDAO.updateUser(updatedUser, infoToUpdate, newInfo);
        } catch (Exception e) {
            throw new UserUpdateException(updatedUser.getLogin(), infoToUpdate, e.getMessage());
        }
        return updatedUser;
    }

    public void removeUser(String login) throws UserNotFoundException, UserRemovalException {
        User user = userDAO.findUserByLogin(login);
        if (user == null) {
            throw new UserNotFoundException(login);
        }

        try {
            userDAO.removeUser(login);
        } catch (Exception e) {
            throw new UserRemovalException(login, e.getMessage());
        }
    }
}
