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

package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.persistence.UserDAO;
import com.lucas.ticketsalesmanager.exception.user.UserAlreadyExistsException;
import com.lucas.ticketsalesmanager.exception.user.UserDAOException;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.exception.user.UserRemovalException;
import com.lucas.ticketsalesmanager.exception.user.UserUpdateException;

import java.util.List;

/**
 * Controller responsible for managing users in the system.
 * This class provides methods to register, fetch, list, update
 * and remove users.
 */
public class UserController {
    private final UserDAO userDAO;

    /**
     * Constructor of the UserController class that initializes the UserDAO.
     */
    public UserController() {
        this.userDAO = new UserDAO();
    }

    /**
     * Registers a new user in the system.
     *
     * @param login The user's login.
     * @param password The user's password.
     * @param name The user name.
     * @param cpf The user's CPF.
     * @param email The user's email.
     * @param isAdmin Indicates whether the user is an administrator.
     * @return The registered user.
     * @throws UserAlreadyExistsException If a user with the same login already exists.
     * @throws UserDAOException If an error occurs when interacting with UserDAO.
     */
    public User registerUser(String login, String password, String name, String cpf, String email, boolean isAdmin)
            throws UserAlreadyExistsException, UserDAOException {
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

    /**
     * Finds a user by login.
     *
     * @param login The login of the user to be found.
     * @return The user corresponding to the given login.
     * @throws UserNotFoundException If the user is not found.
     */
    public User findUserByLogin(String login) throws UserNotFoundException {
        User user = userDAO.findUserByLogin(login);
        if (user == null) {
            throw new UserNotFoundException(login);
        }
        return user;
    }

    /**
     * Lists all users registered in the system.
     *
     * @return A list of all users.
     * @throws UserDAOException If an error occurs when listing users.
     */
    public List<User> listAllUsers() throws UserDAOException {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            throw new UserDAOException("Error listing all users.", e.getMessage());
        }
    }

    /**
     * Updates a user's information.
     *
     * @param updatedUser The user with the information to be updated.
     * @param infoToUpdate The information to be updated.
     * @param newInfo The new information value.
     * @return The updated user.
     * @throws UserUpdateException If an error occurs while updating the user.
     * @throws UserNotFoundException If the user is not found.
     */
    public User updateUser(User updatedUser, String infoToUpdate, String newInfo)
            throws UserUpdateException, UserNotFoundException {
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

    /**
     * Removes a user from the system.
     *
     * @param login The login of the user to be removed.
     * @throws UserNotFoundException If the user is not found.
     * @throws UserRemovalException If an error occurs while removing the user.
     */
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
