package com.lucas.ticketsalesmanager.models;

import com.lucas.ticketsalesmanager.service.communication.EventFeedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The User class represents a user of the ticket sales system.
 * It stores information such as login, name, CPF, email, password, admin status,
 * and tickets purchased by the user.
 */
public class User {

    // Attributes
    /**
     * The user's login. This is a final field and cannot be changed.
     */
    private String login;

    /**
     * The user's full name.
     */
    private String name;

    /**
     * The user's CPF (Brazilian Individual Taxpayer Registry).
     */
    private String cpf;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The user's password.
     */
    private String password;

    /**
     * Indicates whether the user has admin privileges.
     */
    private final boolean isAdmin;

    /**
     * List of tickets purchased by the user.
     */
    private final ArrayList<Ticket> tickets;

    private final ArrayList<EventFeedback> eventFeedbacks;

    // Constructor
    /**
     * Constructs a new User object with the provided data.
     *
     * @param login The user's login.
     * @param password The user's password.
     * @param name The user's full name.
     * @param cpf The user's CPF.
     * @param email The user's email.
     * @param isAdmin Indicates whether the user is an admin.
     */
    public User(String login, String password, String name, String cpf, String email, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.isAdmin = isAdmin;
        this.eventFeedbacks = new ArrayList<>();
        this.tickets = new ArrayList<>();
    }

    // Getters and Setters
    /**
     * Gets the user's name.
     *
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name The new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's CPF.
     *
     * @return The user's CPF.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Sets the user's CPF.
     *
     * @param cpf The new CPF of the user.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Gets the user's email.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email The new email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's login.
     *
     * @return The user's login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the user's login.
     *
     * @param login The new login of the user.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password The new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user has admin privileges.
     *
     * @return {@code true} if the user is an admin, {@code false} otherwise.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Gets the list of tickets purchased by the user.
     *
     * @return The list of tickets.
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<EventFeedback> getFeedback() {
        return eventFeedbacks;
    }

    // Overridden Methods
    /**
     * Checks the equality between two User objects based on login, CPF, and email.
     *
     * @param obj The object to be compared.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return isAdmin == user.isAdmin &&
                Objects.equals(login, user.login) &&
                Objects.equals(cpf, user.cpf) &&
                Objects.equals(email, user.email);
    }

    /**
     * Returns the hash code based on the user's attributes.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(login, cpf, email, isAdmin);
    }

    /**
     * Returns a textual representation of the User object.
     *
     * @return A string containing the user's attributes.
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", tickets=" + tickets +
                '}';
    }

    // Class Methods
    /**
     * Verifies if the provided login and password credentials are valid.
     *
     * @param login The provided login.
     * @param password The provided password.
     * @return {@code true} if the credentials are correct, {@code false} otherwise.
     */
    public boolean login(String login, String password) {
        return User.this.login.equals(login) && User.this.password.equals(password);
    }
}
